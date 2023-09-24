package com.guba.spring.speakappbackend.repositories.filesystems;

import com.guba.spring.speakappbackend.exceptions.FileStoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class VideoArticulationStorageRepository {

    private final Path rootFolder = Paths.get(System.getProperty("user.home"), "videos");

    @PostConstruct
    void init() throws IOException {
        if (!Files.exists(rootFolder))
            Files.createDirectories(rootFolder);

        log.info("root path videos: {}", this.rootFolder);
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

}
