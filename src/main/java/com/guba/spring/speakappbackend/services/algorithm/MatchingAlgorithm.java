package com.guba.spring.speakappbackend.services.algorithm;

public interface MatchingAlgorithm {

    ResultMatchDTO getMatchPercentage(String wordCalculate, String word);
}
