package com.wcd.userservice.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDirPath;
    @Value("${file.url}")
    private String url;

    // 확장자 추출
    private String extractExt(String originalFileName) {
        int idx = originalFileName.lastIndexOf(".");
        String ext = originalFileName.substring(idx);
        return ext;
    }

    private String createStoreFileName(String originalFileName) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFileName);
        String storeFileName = uuid + ext;

        return storeFileName;
    }

    public String storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFileName);
        multipartFile.transferTo(new File(fileDirPath + storeFileName));

        return url + storeFileName;
    }
}
