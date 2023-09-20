package com.guba.spring.speakappbackend.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

import com.guba.spring.speakappbackend.web.schemas.FileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class FileStorageService {

    private final Path rootFolder = Paths.get(System.getProperty("user.home"), "audios");

    @PostConstruct
    void init() throws IOException {
        if (!Files.exists(rootFolder))
            Files.createDirectories(rootFolder);

        log.info("root path audios: {}", this.rootFolder);
    }

    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.rootFolder.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        } catch (FileAlreadyExistsException e) {
            throw new RuntimeException("A file of that name already exists.");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void save(byte[] file, String folder, String filename) {
        try {
            createDirectoriesIfNotExist(folder);
            var dir = Path.of(rootFolder.toAbsolutePath().toString(), folder, filename);
            Files.write(dir, file);
        } catch (FileAlreadyExistsException e) {
            throw new RuntimeException("A file of that name already exists.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Resource load(String filename) {
        try {
            Path file = this.rootFolder.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
        throw new RuntimeException("Could not read the file!");
    }

    public void save(List<MultipartFile> files) {
        for (MultipartFile file : files) {
            this.save(file);
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootFolder.toFile());
    }

    public List<FileDTO> loadAll() {
        try {
            return Files
                    .walk(this.rootFolder, 1)
                    .filter(path -> !path.equals(this.rootFolder))
                    .map(this.rootFolder::relativize)
                    .map(path -> {
                        String filename = path.getFileName().toString();
                        return new FileDTO(filename, path.toUri().getPath());
                    }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    private void createDirectoriesIfNotExist(String directories) throws IOException {
        var rootWithDirectories = Path.of(rootFolder.toAbsolutePath().toString(), directories);
        if (!Files.exists(rootWithDirectories))
            Files.createDirectories(rootWithDirectories);
    }

}
