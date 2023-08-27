package com.wcd.userservice.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDirPath;
    @Value("${file.url}")
    private String url;

    public String storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFileName);
        multipartFile.transferTo(new File(fileDirPath + storeFileName));

        return url + storeFileName;
    }

    public boolean deleteFile(String fileUrl) throws MalformedURLException {
        URL url = new URL(fileUrl);

        File fileToDelete = new File(url.getPath());

        if(fileToDelete.exists()) {
            return fileToDelete.delete();
        }

        return false; // 파일을 찾지 못하거나 삭제할 수 없음음
    }


    private String createStoreFileName(String originalFileName) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFileName);

        return uuid + "." + ext;
    }

    private String extractExt(String originalFileName) {
        int idx = originalFileName.lastIndexOf(".");
        return originalFileName.substring(idx);
    }


}
