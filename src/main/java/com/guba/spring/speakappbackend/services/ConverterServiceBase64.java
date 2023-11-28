package com.guba.spring.speakappbackend.services;

import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class ConverterServiceBase64 {

    public byte[] getDecoded(String imageBase64) {
        return Base64
                .getDecoder()
                .decode(imageBase64);
    }

    public String getEncoded(byte[] imageByte) {
        return Base64
                .getEncoder()
                .encodeToString(imageByte);
    }

}
