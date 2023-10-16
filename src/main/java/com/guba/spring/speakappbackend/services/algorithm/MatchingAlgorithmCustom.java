package com.guba.spring.speakappbackend.services.algorithm;

import com.guba.spring.speakappbackend.services.transforms.TransformerText;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

@Component
public class MatchingAlgorithmCustom implements MatchingAlgorithm {

    private final TransformerText transformerText;

    public MatchingAlgorithmCustom(@Qualifier("removeTransformerDecorator") TransformerText transformerText) {
        this.transformerText = transformerText;
    }

    /**
     *
     *
     * */
    @Override
    public ResultMatchDTO getMatchPercentage(String wordCalculate, String wordExpected) {

        double percentageMatch;
        String wordTransformer = this.transformerText.transform(wordCalculate);
        String wordExpectedTransformer = this.transformerText.transform(wordExpected);

        Optional<ResultMatchDTO> resultCaseBasic = caseBasic(wordTransformer, wordExpectedTransformer);
        if (resultCaseBasic.isPresent())
            return resultCaseBasic.get();

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
        return new ResultMatchDTO(wordCalculate, wordExpected, percentageMatch, result);
    }

    private Optional<ResultMatchDTO> caseBasic(String wordCalculate, String wordExpected) {
        double percentageMatch;
        int size = Optional.ofNullable(wordExpected).map(String::length).orElse(0);
        int [] result = IntStream.generate(() -> 1).limit(size).toArray();
        if (wordCalculate == null && wordExpected == null) {
            percentageMatch = 1.0;
        } else if (wordCalculate == null || wordExpected == null) {
            percentageMatch = 0.0;
            result = IntStream.generate(() -> 0).limit(size).toArray();
        } else if (wordExpected.equalsIgnoreCase(wordCalculate)) {
            percentageMatch = 1.0;
        } else {
            return Optional.empty();
        }

        return Optional.of(new ResultMatchDTO(wordCalculate, wordExpected, percentageMatch, result));
    }
}
