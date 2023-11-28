package com.guba.spring.speakappbackend.storages.filesystems;

import com.guba.spring.speakappbackend.exceptions.FileStoreException;
import com.guba.spring.speakappbackend.services.ConverterServiceBase64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
@RequiredArgsConstructor
public class AudioStorageRepository {

    private static final Path ROOT_FOLDER = Paths.get(System.getProperty("user.home"), "audios");
    private final ConverterServiceBase64 converterServiceBase64;

    @PostConstruct
    void init() throws IOException {
        if (!Files.exists(ROOT_FOLDER))
            Files.createDirectories(ROOT_FOLDER);

        log.info("root path audios: {}", ROOT_FOLDER);
    }

    public void save(byte[] file, String folder, String filename) {
        try {
            createDirectoriesIfNotExist(folder);
            var dir = Path.of(ROOT_FOLDER.toAbsolutePath().toString(), folder, filename);
            Files.write(dir, file);
        } catch (FileAlreadyExistsException e) {
            throw new FileStoreException("A file of that name already exists.");
        } catch (Exception e) {
            String msj = String.format("Error in save file in folder=%s and filename=%s", folder, filename);
            throw new FileStoreException(msj, e);
        }
    }

    public void save(String audio, String folder, String filename) {
        try {
            createDirectoriesIfNotExist(folder);
            var dir = Path.of(ROOT_FOLDER.toAbsolutePath().toString(), folder, filename);
            byte [] file = this.converterServiceBase64.getDecoded(audio);
            Files.write(dir, file);
        } catch (FileAlreadyExistsException e) {
            throw new FileStoreException("A file of that name already exists.");
        } catch (Exception e) {
            String msj = String.format("Error in save file in folder=%s and filename=%s", folder, filename);
            throw new FileStoreException(msj, e);
        }
    }

    public Resource getAudioByFilename(String filename) {
        try {
            Path file = ROOT_FOLDER.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
        } catch (MalformedURLException e) {
            throw new FileStoreException("Error malformed url for filename " + filename, e);
        }
        throw new FileStoreException("Could not read the file!");
    }

    public String getAudioBase64ByFilename(String filename) {
        String audioBase64 = "";
        try {
            Resource resource = this.getAudioByFilename(filename);
            audioBase64 = this.converterServiceBase64.getEncoded(resource.getInputStream().readAllBytes());
        } catch (Exception e) {
            log.error("error get audio {}", filename, e);
        }
        return audioBase64;
    }

    private void createDirectoriesIfNotExist(String directories) throws IOException {
        var rootWithDirectories = Path.of(ROOT_FOLDER.toAbsolutePath().toString(), directories);
        if (!Files.exists(rootWithDirectories))
            Files.createDirectories(rootWithDirectories);
    }

}
