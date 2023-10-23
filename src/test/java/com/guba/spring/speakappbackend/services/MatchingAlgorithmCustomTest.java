package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.services.algorithm.MatchingAlgorithm;
import com.guba.spring.speakappbackend.services.algorithm.MatchingAlgorithmCustom;
import com.guba.spring.speakappbackend.services.transforms.FilterAlfaNumericDecorator;
import com.guba.spring.speakappbackend.services.transforms.ReplaceAccentDecorator;
import com.guba.spring.speakappbackend.services.transforms.ReplaceMoreTwoConsecutiveCharacterDecorator;
import com.guba.spring.speakappbackend.services.transforms.TransformLowerCase;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({MockitoExtension.class})
class MatchingAlgorithmCustomTest {

    private final MatchingAlgorithm matchingAlgorithm = new MatchingAlgorithmCustom(
           new ReplaceMoreTwoConsecutiveCharacterDecorator(
                   new ReplaceAccentDecorator(
                           new FilterAlfaNumericDecorator(
                                   new TransformLowerCase()
                           )
                   )
           )
    );

    @ParameterizedTest
    @CsvSource({
            "arbol, arbol",
            "isla, isla",
            "arco, arco",
            "caramelo, caramelo",
            "flan, flan",
            "globo, globo",
            "robot, robot",
            "barra, barra",
            "ARBOL, arBOL",
            "ISLA, iSLa",
            "ARCO, aRCo",
            "CARAMELO, caRaMelO",
            "FLAN, FLan",
            "GLOBO, glOBo",
            "ROBOT, roBOt",
            "BARRA, bARra"
    })
    void getMatchPercentage100Test(String wordCalculate, String word) {
        final double percentageExcepted = 1;

        final var result = matchingAlgorithm.getMatchPercentage(wordCalculate, word);

        assertEquals(percentageExcepted, result.getPercentageMatch());
    }

    @ParameterizedTest
    @CsvSource({
            "caaaarrrrlllaaa, carla",
            "aaaarrrrrboollll, arbol",
            "iiiiisssslaaaa, isla",
            "aaarrrrccooo, arco",
            "caaraaammelooo, caramelo",
            "flaaannn, flan",
            "glooboo, globo",
            "rrrobootttt, robot",
            "baarrrraaa, barra",
            "baarrrr, bar",
            "llllaaavee, llave",
            "  Arr , ar"
    })
    void getMatchPercentage100WithTest(String wordCalculate, String word) {
        final double percentageExcepted = 1;

        final var result = matchingAlgorithm.getMatchPercentage(wordCalculate, word);

        assertEquals(percentageExcepted, result.getPercentageMatch());
    }

    @ParameterizedTest
    @CsvSource({
            "albol, arbol",
            "ila, isla",
            "alco, arco",
            "calamelo, caramelo",
            "fan, flan",
            "lobo, globo",
            "robo, robot",
            "bara, barra"
    })
    void getMatchPercentageBetiw80Test(String wordCalculate, String word) {
        final double percentageExcepted = 1;

        final var result = matchingAlgorithm.getMatchPercentage(wordCalculate, word);

        assertTrue( result.getPercentageMatch() < 90);
    }

    @ParameterizedTest
    @CsvSource({
            "albol, arbol, '1,0,1,1,1'",
            "ila, isla, '1,0,1,1'",
            "alco, arco, '1,0,1,1'",
            "calamelo, caramelo, '1,1,0,1,1,1,1,1'",
            "fan, flan, '1,0,1,1'",
            "lobo, globo, '0,1,1,1,1'",
            "robo, robot, '1,1,1,1,0'",
            "bara, barra, '1,1,1,0,1'",
            "varra, vara, '1,1,0,1'"
    })
    void getMatchPercentageTestWithArray(String wordCalculate, String word, String arrayInt) {
        List<Integer> expected = Arrays.stream(arrayInt.split(",")).map(Integer::valueOf).collect(Collectors.toList());

        final var result = matchingAlgorithm.getMatchPercentage(wordCalculate, word);
        final var resultMatch = Arrays.stream(result.getCharMatch()).boxed().collect(Collectors.toList());
        assertTrue( result.getPercentageMatch() < 90);
        assertEquals( expected, resultMatch);

    }

}