package com.guba.spring.speakappbackend.web.controllers;

import com.guba.spring.speakappbackend.storages.database.models.Image;
import com.guba.spring.speakappbackend.storages.database.models.ImageTemp;
import com.guba.spring.speakappbackend.storages.database.repositories.ExerciseRepository;
import com.guba.spring.speakappbackend.storages.database.repositories.ImageRepository;
import com.guba.spring.speakappbackend.storages.database.repositories.ImageTempRepository;
import com.guba.spring.speakappbackend.storages.database.repositories.PhonemeRepository;
import com.guba.spring.speakappbackend.services.ConverterImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "images")
@Slf4j
public class ImageController {

    private static final String PATH = "/Users/work/guido/repo/speakapp-backend/src/main/resources";
    private long idImage = 0;
    private long idImageTemp = 0;


    private final PhonemeRepository phonemeRepository;
    private final ImageRepository imageRepository;
    private final ExerciseRepository exerciseRepository;
    private final ImageTempRepository imageTempRepository;

    private final ConverterImage converterImage;


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String message;
        try {
            String nameFile = file.getOriginalFilename();
            //SAVE IMAGEN WITH BASE64
            var image = new Image();
            image.setIdImage(idImage++);
            image.setName(nameFile);
            image.setImageData(converterImage.getEncoded(file.getBytes()));
            this.imageRepository.save(image);

            byte[] bytesFileSystem = Files.readAllBytes(Path.of(PATH,nameFile));
            var imageFileSystem = new Image();
            imageFileSystem.setIdImage(idImage++);
            imageFileSystem.setName(nameFile);
            imageFileSystem.setImageData(converterImage.getEncoded(bytesFileSystem));
            this.imageRepository.save(imageFileSystem);

            //SAVE IMAGEN WITH byte
            var imageByte = new ImageTemp();
            imageByte.setIdImage(idImageTemp++);
            imageByte.setName(nameFile);
            imageByte.setImageData(file.getBytes());
            this.imageTempRepository.save(imageByte);

            var imageByteFileSystem = new ImageTemp();
            imageByteFileSystem.setIdImage(idImageTemp++);
            imageByteFileSystem.setName(nameFile);
            imageByteFileSystem.setImageData(bytesFileSystem);
            this.imageTempRepository.save(imageByteFileSystem);

            //SAVE  FILE-SYSTEM
            Files.write(Path.of(PATH,"api-rest_" + nameFile), file.getBytes());
            Files.write(Path.of(PATH,"copy-file-system_" + nameFile), bytesFileSystem);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping(value = "/download/{id}")
    @ResponseBody
    public ResponseEntity<byte []> getFile(@PathVariable Long id) {
        Image image = this.imageRepository.getById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf(MediaType.IMAGE_PNG_VALUE))
                .body(converterImage.getDecoded(image.getImageData()));
    }

}
