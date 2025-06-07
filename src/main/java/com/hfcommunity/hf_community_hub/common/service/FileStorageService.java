package com.hfcommunity.hf_community_hub.common.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    /**
     * Store a file and return its URL
     * @param file the file to store
     * @return the URL of the stored file
     * @throws IOException if an I/O error occurs
     */
    String storeFile(MultipartFile file) throws IOException;
}