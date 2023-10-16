package com.guba.spring.speakappbackend.services.transforms;

import java.util.Map;

public class ReplaceAccentDecorator extends TransformerTextDecorator {

    private static final Map<String, String> ACCENTS = Map.of(
            "á", "a",
            "é", "e",
            "í", "i",
            "ó", "o",
            "ú", "u",
            "Á", "A",
            "É", "E",
            "Í", "I",
            "Ó", "O",
            "Ú", "U"
    );

    public ReplaceAccentDecorator(TransformerText transformerText) {
        super(transformerText);
    }

    @Override
    public String transform(String text) {
        String textWithOutAccent = text;
        for (var entry : ACCENTS.entrySet()) {
            final String accent = entry.getKey();
            final String noAccent = entry.getValue();
            textWithOutAccent = textWithOutAccent.replace(accent, noAccent);
        }
        return super.transform(textWithOutAccent);
    }
}