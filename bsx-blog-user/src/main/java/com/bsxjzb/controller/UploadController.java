package com.bsxjzb.controller;

import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public BlogResponse uploadImg(@RequestBody MultipartFile img) {
        String url = uploadService.uploadImg(img);
        return BlogResponse.ok(url);
    }
}
