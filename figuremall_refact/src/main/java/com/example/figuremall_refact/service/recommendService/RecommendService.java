package com.example.figuremall_refact.service.recommendService;

import com.example.figuremall_refact.dto.recommendDto.RecommendRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final RestTemplate restTemplate;

    public String getRecommendations(RecommendRequest request) {
        String fastApiUrl = "http://localhost:8000/recommend";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RecommendRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(fastApiUrl, entity, String.class);
        System.out.println(response.getBody());

        return response.getBody();
    }
}
