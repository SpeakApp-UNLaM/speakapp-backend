package com.guba.spring.speakappbackend.services.algorithm;

import java.util.Arrays;

public class MatchingAlgorithmCustom implements MatchingAlgorithm {


    /**
     *
     *
     * */
    @Override
    public ResultMatchDTO getMatchPercentage(String wordCalculate, String wordExpected) {

        double percentageMatch = 2.0;
        if (wordCalculate == null && wordExpected == null) {
            percentageMatch = 1.0;
        } else if (wordCalculate == null || wordExpected == null) {
            percentageMatch = 0.0;
        } else if (wordExpected.equalsIgnoreCase(wordCalculate)) {
            percentageMatch = 1.0;
        }

        if (percentageMatch != 2.0) {
            return ResultMatchDTO
                    .builder()
                    .percentageMatch(percentageMatch)
                    .wordCalculate(wordCalculate)
                    .wordExpected(wordExpected)
                    .build();
        }

        int [] result = new int[wordExpected.length()];
        int indexStartCalculate = 0;

        //CCCAAARLLAAAA
        //CARLA
        for (int indexExpected = 0; indexExpected < wordExpected.length(); indexExpected++) {

            boolean isActualCharEqualsNextChar = false;
            if ( (indexExpected + 1) < wordExpected.length()) {
                isActualCharEqualsNextChar = wordExpected.charAt(indexExpected) == wordExpected.charAt(indexExpected + 1);
            }

            for (int indexCalculate = indexStartCalculate; indexCalculate < wordCalculate.length(); indexCalculate++) {

                boolean isEqualsChar = wordExpected.charAt(indexExpected) == wordCalculate.charAt(indexCalculate);
                if ( isEqualsChar && result[indexExpected] == 0 ) {
                    result[indexExpected] = 1;
                } else if (!isEqualsChar) {
                    indexStartCalculate = indexCalculate;
                    break;
                }
            }
            if (
                    isActualCharEqualsNextChar
                    && (indexStartCalculate + 1) < wordCalculate.length()
                    && wordCalculate.charAt(indexStartCalculate - 1) == wordCalculate.charAt(indexStartCalculate - 2)) {
                indexStartCalculate--;
            }
            if ( indexExpected == indexStartCalculate)
                indexStartCalculate++;

        }

        percentageMatch = Arrays.stream(result).asDoubleStream().sum() / wordExpected.length();
        return ResultMatchDTO
                .builder()
                .percentageMatch(percentageMatch)
                .charMatch(result)
                .wordCalculate(wordCalculate)
                .wordExpected(wordExpected)
                .build();
    }
}
