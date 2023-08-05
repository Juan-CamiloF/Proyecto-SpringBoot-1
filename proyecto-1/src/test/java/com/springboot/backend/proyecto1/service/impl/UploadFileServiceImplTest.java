package com.springboot.backend.proyecto1.service.impl;

import com.springboot.backend.proyecto1.exception.UploadFileException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UploadFileServiceImplTest {

    UploadFileServiceImpl uploadFileService;

    static final Path TEST = Path.of("test");

    @BeforeAll
    static void initAll() throws IOException {
        Files.createDirectories(TEST);
    }

    @BeforeEach
    void init() {
        uploadFileService = new UploadFileServiceImpl("test");
    }

    private byte[] createImageBytes() {
        String imageData = "data:image/png;base64,iVBORw0KG...";
        return imageData.getBytes(StandardCharsets.UTF_8);
    }

    @Test
    void testUploadFilePng() throws IOException {
        byte[] imageBytes = createImageBytes();
        MockMultipartFile file = new MockMultipartFile("image", "image.png", "image/png", imageBytes);
        String filename = uploadFileService.uploadFile(file);
        assertNotNull(filename);
        Path path = TEST.resolve(filename);
        assertTrue(Files.exists(path));
        Files.deleteIfExists(path);
    }

    @Test
    void testUploadFileJpg() throws IOException {
        byte[] imageBytes = createImageBytes();
        MockMultipartFile file = new MockMultipartFile("image", "image.jpg", "image/jpg", imageBytes);
        String filename = uploadFileService.uploadFile(file);
        assertNotNull(filename);
        Path path = TEST.resolve(filename);
        assertTrue(Files.exists(path));
        Files.deleteIfExists(path);
    }

    @Test
    void testUploadFileJpeg() throws IOException {
        byte[] imageBytes = createImageBytes();
        MockMultipartFile file = new MockMultipartFile("image", "image.jpeg", "image/jpeg", imageBytes);
        String filename = uploadFileService.uploadFile(file);
        assertNotNull(filename);
        Path path = TEST.resolve(filename);
        assertTrue(Files.exists(path));
        Files.deleteIfExists(path);
    }

    @Test
    void testUploadEmptyFile() {
        byte[] emptyBytes = new byte[0];
        MockMultipartFile file = new MockMultipartFile("image", "image.png", "image/png", emptyBytes);
        assertThrows(UploadFileException.class, () -> uploadFileService.uploadFile(file), "El archivo está vacío");
    }

    @Test
    void testUploadInvalidFile() {
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "Test content".getBytes());
        assertThrows(UploadFileException.class, () -> uploadFileService.uploadFile(file), "El archivo no es válido");
    }

    @Test
    void testUploadFileWithNonExistentDirectory() throws IOException {
        byte[] imageBytes = createImageBytes();
        MockMultipartFile file = new MockMultipartFile("image", "image.png", "image/png", imageBytes);
        Files.deleteIfExists(TEST);
        assertThrows(UploadFileException.class, () -> uploadFileService.uploadFile(file), "No existe el directorio en el servidor");
        Files.createDirectories(TEST);
    }

    /*@Test
    void testUploadFileWithErrorInCopyFile() throws IOException {
        MockMultipartFile multipartFile = mock(MockMultipartFile.class);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getContentType()).thenReturn("image/png");
        when(multipartFile.getOriginalFilename()).thenReturn("image.png");
        InputStream inputStream = mock(InputStream.class);
        when(multipartFile.getInputStream()).thenReturn(inputStream);
        doThrow(IOException.class).when(inputStream).read();
        assertThrows(UploadFileException.class, () -> uploadFileService.uploadFile(multipartFile));
    }*/

    @Test
    void testDeleteFile() {
        byte[] imageBytes = createImageBytes();
        MockMultipartFile file = new MockMultipartFile("image", "image.png", "image/png", imageBytes);
        String filename = uploadFileService.uploadFile(file);
        assertNotNull(filename);
        assertTrue(Files.exists(TEST.resolve(filename)));
        uploadFileService.deleteFile(filename);
        assertFalse(Files.exists(TEST.resolve(filename)));
    }

//    @Test
//    void testExceptionDeleteFile() throws IOException {
//        mockStatic(Files.class);
//        when(Files.deleteIfExists(any(Path.class))).thenThrow(IOException.class);
//        assertThrows(UploadFileException.class, () -> uploadFileService.deleteFile("ajsdlf"), "No se pudo eliminar el archivo");
//    }

    @Test
    void testGetResource() throws IOException {
        byte[] imageBytes = createImageBytes();
        MockMultipartFile file = new MockMultipartFile("image", "image.png", "image/png", imageBytes);
        String filename = uploadFileService.uploadFile(file);
        assertNotNull(filename);
        Resource resource = uploadFileService.getResource(TEST.resolve(filename));
        assertNotNull(resource);
        assertTrue(resource.isReadable());
        assertTrue(resource.exists());
        Files.deleteIfExists(TEST.resolve(filename));
    }

    @Test
    void testGetNonExistentResource() throws IOException {
        byte[] imageBytes = createImageBytes();
        MockMultipartFile file = new MockMultipartFile("image", "image.png", "image/png", imageBytes);
        String filename = uploadFileService.uploadFile(file);
        assertNotNull(filename);
        Files.deleteIfExists(TEST.resolve(filename));
        assertFalse(Files.exists(TEST.resolve(filename)));
        assertThrows(UploadFileException.class, () -> uploadFileService.getResource(TEST.resolve(filename)), "El archivo no existe o no se puede leer");

    }

    @Test
    void testGetNotReadableResource() throws IOException {
        byte[] imageBytes = createImageBytes();
        MockMultipartFile file = new MockMultipartFile("image", "image.png", "image/png", imageBytes);
        String filename = uploadFileService.uploadFile(file);
        assertNotNull(filename);
        File f = new File(TEST.resolve(filename).toUri());
        f.setReadable(false);
        assertThrows(UploadFileException.class, () -> uploadFileService.getResource(TEST.resolve(filename)), "El archivo no existe o no se puede leer");
        f.setReadable(true);
        Files.deleteIfExists(TEST.resolve(filename));
    }

//    @Test
//    void testExceptionGetResource() {
//        Path invalidPath = mock(Path.class);
//        assertThrows(MalformedURLException.class, () -> uploadFileService.getResource(invalidPath), "No se pudo leer el archivo");
//    }


    @AfterAll
    static void deleteDirectories() throws IOException {
        Files.deleteIfExists(TEST);
    }
}
