package com.springboot.backend.proyecto1.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

/**
 * Interface the UploadFile
 */
public interface IUploadFileService {

    /**
     * Upload file to server
     */
    String uploadFile(MultipartFile file);

    /**
     * Delete file to server
     */
    void deleteFile(String fileName);

    /**
     * Valid file to server
     */
    boolean checkFile(Path path);

    /**
     * Get file to server
     */
    Resource getResource(Path path);
}
