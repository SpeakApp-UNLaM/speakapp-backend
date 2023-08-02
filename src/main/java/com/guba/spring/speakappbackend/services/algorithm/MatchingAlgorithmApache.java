package com.guba.spring.speakappbackend.services.algorithm;

import org.apache.commons.lang3.StringUtils;

public class MatchingAlgorithmApache implements MatchingAlgorithm {

    @Override
    public ResultMatchDTO getMatchPercentage(String wordCalculate, String word) {
        double percentageMatch;

        if (wordCalculate == null && word == null) {
            percentageMatch = 1.0;
        } else if (wordCalculate == null || word == null) {
            percentageMatch = 0.0;
        } else {
            percentageMatch = StringUtils.getJaroWinklerDistance(wordCalculate, word);
        }

        return ResultMatchDTO
                .builder()
                .percentageMatch(percentageMatch)
                .build();
    }

}
