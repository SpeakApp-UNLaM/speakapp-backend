package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.storages.database.models.Articulation;
import com.guba.spring.speakappbackend.storages.database.models.Image;
import com.guba.spring.speakappbackend.storages.database.repositories.ArticulationRepository;
import com.guba.spring.speakappbackend.storages.database.repositories.ImageRepository;
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

    private static final String DIR_FOLDER_SPEAK_APP = "/Users/work/guido/repo/speakapp-backend/";
    private static final String DIR_IMAGES_CSV = "/Users/work/guido/repo/speakapp-backend/csv/tm_image.csv";

    private static final String DIR_ARTICULATION_CSV = "/Users/work/guido/repo/speakapp-backend/csv/tm_articuleme.csv";

    private final ConverterServiceBase64 converterServiceBase64;
    private final ArticulationRepository articulationRepository;

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

    public void loadArticulationToDatabase() throws FileNotFoundException {

        List<ArticulationCSV> articulationsCSV = new CsvToBeanBuilder<ArticulationCSV>(new FileReader(DIR_ARTICULATION_CSV))
                .withSeparator(',')
                .withType(ArticulationCSV.class)
                .build()
                .parse();

        log.info("read records {} from {}", articulationsCSV.size(), DIR_IMAGES_CSV);

        IntStream
                .iterate(0, i -> i < articulationsCSV.size(), i -> i + 100)
                .mapToObj(i -> articulationsCSV.subList(i, Math.min(i + 100, articulationsCSV.size())))
                .collect(toList())
                .forEach(articulationCSV-> {
                    var articulations = articulationCSV
                            .stream()
                            .map(this::convertArticulationCSVToArticulation)
                            .filter(Objects::nonNull)
                            .collect(toList());
                    this.articulationRepository.saveAll(articulations);
                    log.info("saved images {} into database", articulations.size());
                });

    }

    public Image convertImageCSVToImage(ImageCSV imageCSV) {
        try {
            //get file image from file system
            var pathRelative = imageCSV.getUrl().substring(1);
            Path dirImage = Path.of(DIR_FOLDER_SPEAK_APP, pathRelative);
            byte[] bytesFileSystem = Files.readAllBytes(dirImage);

            //converter to image database
            var image = new Image();
            image.setIdImage(Long.valueOf(imageCSV.getIdImage()));
            image.setName(imageCSV.getName());
            image.setDividedName(imageCSV.getDividedName());
            //encode
            image.setImageData(converterServiceBase64.getEncoded(bytesFileSystem));
            return image;
        } catch (IOException e) {
            throw new IllegalArgumentException("error in read images from filesystem",e);
        }
    }

    public Articulation convertArticulationCSVToArticulation(ArticulationCSV articulationCSV) {
        try {
            //get file image from file system
            var pathRelative = articulationCSV.getUrl().substring(1);
            Path dirImage = Path.of(DIR_FOLDER_SPEAK_APP, pathRelative);
            byte[] bytesFileSystem = Files.readAllBytes(dirImage);

            //converter to image database
            var articulation = new Articulation();
            articulation.setIdArticulation(articulationCSV.getIdArticulation());
            articulation.setIdPhoneme(articulationCSV.getIdPhoneme());
            articulation.setImageData(converterServiceBase64.getEncoded(bytesFileSystem)); //encode

            return articulation;
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

    @Getter
    @Setter
    public static class ArticulationCSV {

        @CsvBindByName(column = "id_articulation")
        private Long idArticulation;

        @CsvBindByName(column = "id_phoneme")
        private Long idPhoneme;

        @CsvBindByName(column = "url")
        private String url;

    }
}
