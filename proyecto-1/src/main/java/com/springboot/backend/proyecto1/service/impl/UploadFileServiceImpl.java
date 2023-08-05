package com.springboot.backend.proyecto1.service.impl;

import com.springboot.backend.proyecto1.service.IUploadFileService;
import com.springboot.backend.proyecto1.exception.UploadFileException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

/**
 * Services for File
 */
@Service
public class UploadFileServiceImpl implements IUploadFileService {

    private final String pathDir;

    public UploadFileServiceImpl(@Value("${filesStoragePath:temp}") String filesStorage){
        pathDir = filesStorage;
    }

    /**
     * Implementation method to upload a file to the server
     *
     * @param file - Customer file
     * @return fileName
     */
    @Override
    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) throw new UploadFileException("El archivo está vacío");

        if (!isValidFile(Objects.requireNonNull(file.getContentType())))
            throw new UploadFileException("El archivo no es válido");

        String fileName = UUID.randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        Path path = Paths.get(pathDir);

        if(checkFile(path)) throw new UploadFileException("No existe el directorio en el servidor");

        path = path.resolve(fileName).toAbsolutePath();

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new UploadFileException("No se pudo cargar el archivo: " + e.getCause().getMessage());
        }
        return fileName;
    }

    /**
     * Implementation method to delete a file to the server
     *
     * @param fileName - Customer file name
     */
    @Override
    public void deleteFile(String fileName) {
        Path path = Paths.get(pathDir).resolve(fileName).toAbsolutePath();
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new UploadFileException("No se pudo eliminar el archivo");
        }
    }

    /**
     * Implementation method to check if a file exists
     *
     * @param path - Path to the file in the server
     * @return if exist file
     */
    @Override
    public boolean checkFile(Path path) {
        return !Files.exists(path);
    }

    /**
     * Implementation method to get resource the Customer
     *
     * @param path - Path to Resource
     * @return resource
     */
    @Override
    public Resource getResource(Path path) {
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new UploadFileException("No se pudo leer el archivo");
        }
        if (!(resource.exists() && resource.isReadable())) {
            throw new UploadFileException("El archivo no existe o no se puede leer");
        }
        return resource;
    }

    /**
     * Method to validate a file to the server
     *
     * @param contentType - Content type to the file
     * @return if is an extension valid
     */
    private boolean isValidFile(String contentType) {
        return contentType.equals("image/jpeg") || contentType.equals("image/jpg") || contentType.equals("image/png");
    }


}
