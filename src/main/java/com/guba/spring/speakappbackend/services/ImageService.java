package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.repositories.database.models.Image;
import com.guba.spring.speakappbackend.repositories.database.repositories.ImageRepository;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private static final String DIR_FOLDER_IMAGES = "D:\\guido\\repos\\speakapp-backend/";
    private static final String DIR_IMAGES_CSV = "D:\\guido\\repos\\speakapp-backend/csv/tm_image.csv";


    private final ConverterImage converterImage;

    private final ImageRepository imageRepository;

    public void loadImagesToDatabase() throws FileNotFoundException {

        List<ImageCSV> imagesCSV = new CsvToBeanBuilder<ImageCSV>(new FileReader(DIR_IMAGES_CSV))
                .withSeparator(',')
                .withType(ImageCSV.class)
                .build()
                .parse();

        log.info("read records {} from {}", imagesCSV.size(), DIR_IMAGES_CSV);

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
                    this.imageRepository.saveAll(imagesDatabase);
                    log.info("saved images {} into database", imagesDatabase.size());
                });

    }

    public Image convertImageCSVToImage(ImageCSV imageCSV) {
        try {
            //get file image from file system
            var pathRelative = imageCSV.getUrl().substring(1);
            Path dirImage = Path.of(DIR_FOLDER_IMAGES, pathRelative);
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
        private String idImage;

        @CsvBindByName(column = "name")
        private String name;

        @CsvBindByName(column = "url")
        private String url;

        @CsvBindByName(column = "divided_name")
        private String dividedName;
    }
}
