package com.guba.spring.speakappbackend.storages.filesystems;

import com.guba.spring.speakappbackend.exceptions.FileStoreException;
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
public class AudioStorageRepository {

    private final Path rootFolder = Paths.get(System.getProperty("user.home"), "audios");

    @PostConstruct
    void init() throws IOException {
        if (!Files.exists(rootFolder))
            Files.createDirectories(rootFolder);

        log.info("root path audios: {}", this.rootFolder);
    }

    public void save(byte[] file, String folder, String filename) {
        try {
            createDirectoriesIfNotExist(folder);
            var dir = Path.of(rootFolder.toAbsolutePath().toString(), folder, filename);
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
            Path file = this.rootFolder.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
        } catch (MalformedURLException e) {
            throw new FileStoreException("Error malformed url for filename " + filename, e);
        }
        throw new FileStoreException("Could not read the file!");
    }

    private void createDirectoriesIfNotExist(String directories) throws IOException {
        var rootWithDirectories = Path.of(rootFolder.toAbsolutePath().toString(), directories);
        if (!Files.exists(rootWithDirectories))
            Files.createDirectories(rootWithDirectories);
    }

}
