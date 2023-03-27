package com.bsxjzb.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleListVO {

    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    //所属分类名
    private String categoryName;
    //缩略图
    private String thumbnail;


    //访问量
    private Long viewCount;

    private Date createTime;

}