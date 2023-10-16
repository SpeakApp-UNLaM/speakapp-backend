package com.guba.spring.speakappbackend.services.transforms;

public class FilterAlfaNumericDecorator extends TransformerTextDecorator {

    public FilterAlfaNumericDecorator(TransformerText transformerText) {
        super(transformerText);
    }

    @Override
    public String transform(String text) {

        //String textWithOut = text.replaceAll("[^A-Za-z0-9áéíóúÁÉÍÓÚ ]", "");
        String textWithOut = text.replaceAll("[^A-Za-z0-9áéíóúÁÉÍÓÚ ]", "#");
        return super.transform(textWithOut);
    }
}