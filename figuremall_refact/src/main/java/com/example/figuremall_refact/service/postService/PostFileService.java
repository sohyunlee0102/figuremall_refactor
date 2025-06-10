package com.example.figuremall_refact.service.postService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.PostHandler;
import com.example.figuremall_refact.domain.post.Post;
import com.example.figuremall_refact.domain.post.PostFile;
import com.example.figuremall_refact.dto.postDto.PostRequestDTO;
import com.example.figuremall_refact.dto.postDto.PostResponseDTO;
import com.example.figuremall_refact.repository.postRepository.PostFileRepository;
import com.example.figuremall_refact.service.s3Service.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostFileService {

    private final PostFileRepository postFileRepository;
    private final S3Service s3Service;

    public PostFile findById(Long id) {
        return postFileRepository.findById(id).orElseThrow(() -> new PostHandler(ErrorStatus.POST_FILE_NOT_FOUND));
    }

    @Transactional
    public void addPostFile(MultipartFile[] files, List<PostRequestDTO.AddPostFileDto> requestList, Post post) {
        List<PostFile> postFiles = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            PostRequestDTO.AddPostFileDto fileDto = requestList.get(i);

            if (file.isEmpty()) continue;

            try {
                String fileUrl = s3Service.uploadFile(file);

                PostFile postFile = PostFile.builder()
                        .post(post)
                        .fileUrl(fileUrl)
                        .fileName(fileDto.getFileName())
                        .fileType(fileDto.getFileType())
                        .fileSize(fileDto.getFileSize())
                        .build();

                postFiles.add(postFile);
            } catch (IOException e) {
                System.err.println("Error uploading file: " + e.getMessage());
            }
        }

        postFileRepository.saveAll(postFiles);
    }

    @Transactional
    public void editPostFile(Post post, List<Long> deleteFileIds, List<PostRequestDTO.AddPostFileDto> fileDTOs, MultipartFile[] newFiles) {
        if (deleteFileIds != null) {
            deleteFiles(deleteFileIds);
        }

        if (newFiles != null && fileDTOs != null) {
            addNewFiles(post, newFiles, fileDTOs);
        }
    }

    private void deleteFiles(List<Long> deleteFileIds) {
        List<PostFile> postFiles = postFileRepository.findAllById(deleteFileIds);
        for (PostFile postFile : postFiles) {
            s3Service.deleteFile(postFile.getFileUrl());
            postFileRepository.delete(postFile);
        }
    }

    private void addNewFiles(Post post, MultipartFile[] files, List<PostRequestDTO.AddPostFileDto> requestList) {
        if (files.length != requestList.size()) {
            throw new PostHandler(ErrorStatus.DATA_NUMBER_NOT_SAME);
        }

        List<PostFile> postFiles = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            PostRequestDTO.AddPostFileDto fileDto = requestList.get(i);

            if (file.isEmpty()) continue;

            try {
                String fileUrl = s3Service.uploadFile(file);

                PostFile postFile = PostFile.builder()
                        .post(post)
                        .fileName(fileDto.getFileName())
                        .fileType(fileDto.getFileType())
                        .fileSize(fileDto.getFileSize())
                        .fileUrl(fileUrl)
                        .build();

                postFiles.add(postFile);
            } catch (IOException e) {
                System.err.println("Error uploading file: " + e.getMessage());
            }
        }

        postFileRepository.saveAll(postFiles);
    }

    public void deleteFile(PostFile postFile) {
        postFileRepository.delete(postFile);
    }

}
