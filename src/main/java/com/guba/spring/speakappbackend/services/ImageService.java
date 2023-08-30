package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.database.models.Image;
import com.guba.spring.speakappbackend.database.repositories.ImageRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private static final String DIR_FOLDER_IMAGES = "/Users/work/guido/repo/speakapp-backend/";
    private static final String DIR_IMAGES_CSV = "/Users/work/guido/repo/speakapp-backend/csv/tm_image.csv";


    private final ConverterImage converterImage;

    private final ImageRepository imageRepository;

    public void loadImagesToDatabase() throws FileNotFoundException {

        /*
        CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build(); // custom separator
        try(CSVReader reader = new CSVReaderBuilder(
                new FileReader(DIR_IMAGES_CSV))
                .withCSVParser(csvParser)   // custom CSV parser
                .withSkipLines(1)           // skip the first line, header info
                .build()){
            List<String[]> r = reader.readAll();
        }*/
        List<ImageCSV> imagesCSV = new CsvToBeanBuilder<ImageCSV>(new FileReader(DIR_IMAGES_CSV))
                .withSeparator(',')
                .withType(ImageCSV.class)
                .build()
                .parse();

        IntStream
                .iterate(0, i -> i < imagesCSV.size(), i -> i + 100)
                .mapToObj(i -> imagesCSV.subList(i, Math.min(i + 100, imagesCSV.size())))
                .collect(toList())
                .forEach(images100CSV-> {
                    var imagesDatabase = images100CSV
                            .stream()
                            .map(this::convertImageCSVToImage)
                            .filter(Objects::nonNull)
                            .collect(toList());
                    //this.imageRepository.saveAll(imagesDatabase);
                });

    }

    public Image convertImageCSVToImage(ImageCSV imageCSV) {
        try {
            //get file image from file system
            var url = imageCSV.getUrl().substring(1);
            //new String(url.getBytes(StandardCharsets.UTF_8), 0, n, "utf8");
            Path dirImage = Path.of(DIR_FOLDER_IMAGES, url);
            if ("/Users/work/guido/repo/speakapp-backend/imagenes/ara�a.PNG".equalsIgnoreCase(dirImage.toString())) {
                URLDecoder.decode(dirImage.toString(), StandardCharsets.UTF_8);
            }
            byte[] bytesFileSystem = Files.readAllBytes(dirImage);

            //converter to image database
            var image = new Image();
            image.setIdImage(Long.valueOf(imageCSV.getIdImage()));
            image.setName(imageCSV.getName());
            image.setDividedName(imageCSV.getDividedName());
            //encode
            image.setImageData(converterImage.getEncoded(bytesFileSystem));
            return image;
        } catch (IOException e) {
            throw new IllegalArgumentException("error in read images from filesystem",e);
        }
    }

    @Getter
    @Setter
    public static class ImageCSV {

        @CsvBindByName(column = "id_image")
        //@CsvBindByPosition(position = 0)
        private String idImage;

        @CsvBindByName(column = "name")
        //@CsvBindByPosition(position = 1)
        private String name;

        @CsvBindByName(column = "url")
        //@CsvBindByPosition(position = 2)
        private String url;

        @CsvBindByName(column = "divided_name")
        //@CsvBindByPosition(position = 3)
        private String dividedName;
    }
}
