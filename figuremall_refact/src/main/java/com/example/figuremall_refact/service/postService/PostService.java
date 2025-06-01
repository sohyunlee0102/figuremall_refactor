package com.example.figuremall_refact.service.postService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.PostHandler;
import com.example.figuremall_refact.domain.enums.PostCategory;
import com.example.figuremall_refact.domain.post.Post;
import com.example.figuremall_refact.domain.post.PostFile;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.dto.postDto.PostRequestDTO;
import com.example.figuremall_refact.dto.postDto.PostResponseDTO;
import com.example.figuremall_refact.repository.postRepository.PostRepository;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final PostFileService postFileService;

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new PostHandler(ErrorStatus.POST_NOT_FOUND));
    }

    @Transactional
    public PostResponseDTO.AddPostResponseDto addPost(PostRequestDTO.AddPostDto request, MultipartFile[] files, String email) {
        User user = userService.findByEmail(email);

        Post post = Post.builder()
                .title(request.getTitle())
                .user(user)
                .content(request.getContent())
                .views(0)
                .category(request.getCategory())
                .build();

        postRepository.save(post);

        if (files != null && files.length > 0) {
            postFileService.addPostFile(files, request.getFiles(), post);
        }

        return new PostResponseDTO.AddPostResponseDto(post.getId());
    }

    @Transactional
    public PostResponseDTO.AddPostResponseDto editPost(PostRequestDTO.EditPostDto request, MultipartFile[] files) {
        Post post = findById(request.getPostId());

        if (request.getTitle() != null) {
            post.setTitle(request.getTitle());
        }

        if (request.getContent() != null) {
            post.setContent(request.getContent());
        }

        if (request.getFiles() != null) {
            postFileService.editPostFile(post, request.getDeleteFileIds(), request.getFiles(), files);
        }

        return new PostResponseDTO.AddPostResponseDto(post.getId());
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Transactional
    public Page<PostResponseDTO.PostList> getPosts(String category, Integer page, Integer size, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        if ("likes".equals(sortBy)) {
            sort = Sort.by(Sort.Direction.DESC, "likes");
        } else if ("views".equals(sortBy)) {
            sort = Sort.by(Sort.Direction.DESC, "views");
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        return postRepository.findByCategory(PostCategory.valueOf(category), pageable)
                .map(post -> PostResponseDTO.PostList.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .views(post.getViews())
                        .likes(post.getLikes().size())
                        .writer(post.getUser().getUsername())
                        .createdAt(post.getCreatedAt())
                        .build()
                );
    }

    @Transactional
    public PostResponseDTO.PostResponse getPost(Long postId) {
        Post post = findById(postId);
        post.setViews(post.getViews() + 1);
        List<PostFile> files = post.getFiles();
        List<PostResponseDTO.Files> dtos = new ArrayList<>();

        for (PostFile file : files) {
            dtos.add(new PostResponseDTO.Files(file.getFileName(), file.getFileUrl(), file.getFileType(), file.getFileSize()));
        }

        return PostResponseDTO.PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .views(post.getViews())
                .likes(post.getLikes().size())
                .writer(post.getUser().getUsername())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .files(dtos)
                .build();
    }

}