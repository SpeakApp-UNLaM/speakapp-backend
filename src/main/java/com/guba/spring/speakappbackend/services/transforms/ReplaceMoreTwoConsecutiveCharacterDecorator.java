package com.guba.spring.speakappbackend.services.transforms;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReplaceMoreTwoConsecutiveCharacterDecorator extends TransformerTextDecorator {

    private static final String CHARACTERS = "a,b,c,d,e,f,g,h,i,j,k,l,m,ñ,n,o,p,q,r,s,t,u,v,w,x,y,z,á,é,í,ó,ú";

    private static final Map<String, String> REGEXES = Stream
            .concat(
                    Arrays.stream(CHARACTERS.toLowerCase().split(",")),
                    Arrays.stream(CHARACTERS.toUpperCase().split(",")))
            .collect(Collectors.toMap(
                    character -> character + character + "+",
                    character -> character + character
            ));

    public ReplaceMoreTwoConsecutiveCharacterDecorator(TransformerText transformerText) {
        super(transformerText);
    }

    @Override
    public String transform(String text) {
        String textWithOutAccent = text;
        for (var entry : REGEXES.entrySet()) {
            final String regex = entry.getKey();
            final String replacement = entry.getValue();
            textWithOutAccent = textWithOutAccent.replaceAll(regex, replacement);
        }
        return super.transform(textWithOutAccent);
    }
}