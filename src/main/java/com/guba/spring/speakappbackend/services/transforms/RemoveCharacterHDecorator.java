package com.guba.spring.speakappbackend.services.transforms;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveCharacterHDecorator extends TransformerTextDecorator {

    private static final Pattern PATTERN = Pattern.compile("[áéíóúÁÉÍÓÚaeiouAEIOU][Hh]");

    public RemoveCharacterHDecorator(TransformerText transformerText) {
        super(transformerText);
    }

    @Override
    public String transform(String text) {
        var textWithOutH = text
                .replaceAll("^[Hh]", "")
                .replaceAll(" [Hh]", " ");

        Matcher matcher = PATTERN.matcher(textWithOutH);

        //case buho deberia ser buo
        var textWithOutHFinal = matcher.replaceAll(matchResult -> matchResult.group().replaceAll("[Hh]", ""));
        return super.transform(textWithOutHFinal);
    }
}
