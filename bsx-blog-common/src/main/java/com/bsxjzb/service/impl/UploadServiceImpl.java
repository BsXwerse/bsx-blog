package com.bsxjzb.service.impl;
import com.bsxjzb.constants.enums.AppHttpCodeEnum;
import com.bsxjzb.exception.SystemException;
import com.bsxjzb.service.UploadService;
import com.bsxjzb.util.PathUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Data
@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String outLink;

    @Override
    public String uploadImg(MultipartFile img) {
        if (img.isEmpty() || img.getSize() == 0) {
            throw new SystemException(AppHttpCodeEnum.EMPTY_FILE_ERROR);
        }
        String name = img.getOriginalFilename();
        if (!name.endsWith(".png") && !name.endsWith(".jpg")) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        String filePath = PathUtil.generatePath(name);
        String url = uploadOss(filePath, img);
        return url;
    }

    private String uploadOss(String filePath, MultipartFile img) {
        Configuration cfg = new Configuration(Region.autoRegion());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            uploadManager.put(img.getInputStream(), filePath, upToken, null, null);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outLink + filePath;
    }
}
