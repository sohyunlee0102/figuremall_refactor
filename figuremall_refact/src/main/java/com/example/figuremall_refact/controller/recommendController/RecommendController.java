package com.example.figuremall_refact.controller.recommendController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.recommendDto.RecommendRequest;
import com.example.figuremall_refact.service.recommendService.RecommendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/recommends")
@RestController
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    @PostMapping
    public ApiResponse<String> recommend(@Valid @RequestBody RecommendRequest request) {
        return ApiResponse.onSuccess(recommendService.getRecommendations(request));
    }

}
