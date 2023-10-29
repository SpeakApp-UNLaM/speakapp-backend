package com.guba.spring.speakappbackend.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith({MockitoExtension.class})
class OrderCollectionTest {

    @Test
    void orderDate() {
        //given
        var now = LocalDateTime.now();
        var resultExpected = List.of(now.plusHours(1).plusMinutes(1), now.plusHours(1), now);

        //when
        var result = Stream
                .of(now, now.plusHours(1), now.plusHours(1).plusMinutes(1))
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        //then
        Assertions.assertEquals(resultExpected, result);
    }
}
