package com.bsxjzb.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MenuTreeVO {
    /** 节点ID */
    private Long id;
    /** 节点名称 */
    private String label;

    private Long parentId;
    /** 子节点 */
    private List<MenuTreeVO> children;
}