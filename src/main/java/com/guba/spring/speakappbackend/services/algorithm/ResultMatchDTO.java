package com.guba.spring.speakappbackend.services.algorithm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ResultMatchDTO {
    private String wordCalculate;
    private String wordExpected;
    private double percentageMatch;
    private int[] charMatch;
}
