package tech.diggle.apps.elearning.uploads;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final Path rootLocation = Paths.get("upload-dir");

    public String store(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName));
            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Failure storing file", e);
        }
    }

    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!", e);
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init() {
        if (!Files.exists(rootLocation))
            try {
                Files.createDirectory(rootLocation);
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize storage!", e);
            }
    }
}