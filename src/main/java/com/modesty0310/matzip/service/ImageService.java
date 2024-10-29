package com.modesty0310.matzip.service;

import com.modesty0310.matzip._enum.ErrorCode;
import com.modesty0310.matzip.exception.CustomException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";
    private static final int MAX_FILE_COUNT = 5; // 최대 파일 개수 제한

    public List<String> saveFiles(MultipartFile[] files) {
        if (files.length > MAX_FILE_COUNT) {
            throw new CustomException(ErrorCode.OVER_MAX_FILE_COUNT);
        }

        // 업로드 디렉토리 확인 및 생성
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        List<String> uris = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                String ext = FilenameUtils.getExtension(file.getOriginalFilename());
                String filename = FilenameUtils.getBaseName(file.getOriginalFilename()) + System.currentTimeMillis() + "." + ext;
                File dest = new File(UPLOAD_DIR + filename);
                file.transferTo(dest);
                uris.add(filename);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new CustomException(ErrorCode.FAILD_IMAGE_UPLOAD);
            }
        }
        return uris;
    }
}
