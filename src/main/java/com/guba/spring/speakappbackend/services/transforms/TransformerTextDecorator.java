package com.guba.spring.speakappbackend.services.transforms;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransformerTextDecorator implements TransformerText {

    private final TransformerText transformerText;

    @Override
    public String transform(String text) {
        return transformerText.transform(text);
    }
}