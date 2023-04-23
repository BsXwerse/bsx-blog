package com.bsxjzb.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bsx_article_tag")
public class ArticleTag {
    private Long articleId;
    private Long tagId;
}
