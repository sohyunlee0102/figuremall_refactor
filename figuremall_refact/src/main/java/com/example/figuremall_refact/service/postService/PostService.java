package com.example.figuremall_refact.service.postService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.PostHandler;
import com.example.figuremall_refact.domain.post.Post;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.dto.postDto.PostRequestDTO;
import com.example.figuremall_refact.dto.postDto.PostResponseDTO;
import com.example.figuremall_refact.repository.postRepository.PostRepository;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        postFileService.addPostFile(files, request.getFiles(), post);

        return new PostResponseDTO.AddPostResponseDto(post.getId());
    }

    @Transactional
    public PostResponseDTO.AddPostResponseDto editPost(PostRequestDTO.EditPostDto request, MultipartFile[] files) {
        Post post = findById(request.getPostId());

        if (request.getTitle() != null) {
            post.setTitle(request.getTitle());
        }

        if (request.getCategory() != null) {
            post.setCategory(request.getCategory());
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

}