package com.bsxjzb.domain.vo;

import com.bsxjzb.domain.po.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVO {
    private List<Menu> menus;
}
