package com.example.figuremall_refact.dto.recommendDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendRequest {

    String purpose;
    String skillLevel;
    String goalSkills;
    Integer budget;
    String ageGroup;
    String footSize;
    Integer weight;
    String gender;
    String fitPreference;

}
