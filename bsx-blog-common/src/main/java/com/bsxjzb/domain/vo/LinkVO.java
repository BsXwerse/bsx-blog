package com.bsxjzb.domain.vo;

import lombok.Data;

@Data
public class LinkVO {
    private Long id;


    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;
}
