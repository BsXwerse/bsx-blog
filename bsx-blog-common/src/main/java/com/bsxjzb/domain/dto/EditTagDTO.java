package com.bsxjzb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditTagDTO {

    private Long id;
    //备注
    private String remark;
    //标签名
    private String name;
}