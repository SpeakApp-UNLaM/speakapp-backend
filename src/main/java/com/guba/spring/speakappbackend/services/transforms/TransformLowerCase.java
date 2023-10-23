package com.guba.spring.speakappbackend.services.transforms;

public class TransformLowerCase implements TransformerText {
    @Override
    public String transform(String text) {
        text = text.trim();
        return text.toLowerCase();
    }
}