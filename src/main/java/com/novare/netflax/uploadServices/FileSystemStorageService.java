package com.novare.netflax.uploadServices;

import com.novare.netflax.exceptions.StorageException;
import com.novare.netflax.exceptions.StorageFileNotFoundException;
import com.novare.netflax.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements IStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileSystemStorageService.class);

    private final Path rootLocation;

    public FileSystemStorageService(@Value("${upload.root-location}") String path) {
        this.rootLocation = Paths.get(path);
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        try {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            String extension = StringUtils.getFilenameExtension(filename);
            String storedFilename = System.currentTimeMillis() + "." + extension;

            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                throw new StorageException("Cannot store file with relative path outside current directory "
                        + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(storedFilename),
                        StandardCopyOption.REPLACE_EXISTING);
                return storedFilename;
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file");
        }
    }

    @Override
    public String storeBase64(byte[] file) {
        try {
            InputStream inputStream = new ByteArrayInputStream(file);
            String storedFilename = FileUtil.getStorageFileName(inputStream);
            Files.copy(inputStream, this.rootLocation.resolve(storedFilename),
                    StandardCopyOption.REPLACE_EXISTING);
            return storedFilename;
        } catch (IOException e) {
            String errorMessage = String.format("%s: %s", "Error when storeBase64: ", e.getMessage());
            logger.error(errorMessage);
            return null;
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void delete(String filename) {
        String justFilename = StringUtils.getFilename(filename);
        try {
            Path file = load(justFilename);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new StorageException("Error deleting a file", e);
        }
    }
}

