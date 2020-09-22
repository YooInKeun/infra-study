package com3.demo.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com3.demo.config.S3Config;
import com3.demo.domain.File;
import com3.demo.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3UploadService {

    private final S3Config s3Config;

    private final AmazonS3 amazonS3;

    private final FileRepository fileRepository;

    /**
     * s3 업로드 후, 객체 url 리턴
     * @param file
     * @return
     */
    public String upload(MultipartFile file) throws IOException {
        // 원래는 file 이름 띄어쓰기 및 특수문자 처리 꼭 해야함

        // key 생성
        String uuid = UUID.randomUUID().toString();
        Path basePath = s3Config.getBasePath();
        String key = basePath.resolve(uuid).resolve(file.getOriginalFilename()).toString();

        // 요청 생성
        PutObjectRequest putObjectRequest = new PutObjectRequest(
                s3Config.getBucketName(),
                key,
                file.getInputStream(),
                makeMetaData(file))
                .withCannedAcl(CannedAccessControlList.PublicRead); // 이 설정을 해야 업로드한 객체 외부에서 사용 가능

        // 업로드
        amazonS3.putObject(putObjectRequest);
        fileRepository.save(new File(key, file.getSize()));
        return amazonS3.getUrl(s3Config.getBucketName(), key).toString();
    }

    /**
     * 파일 메타 데이터 설정
     * @param multipartFile
     * @return
     */
    private ObjectMetadata makeMetaData(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        return objectMetadata;
    }
}
