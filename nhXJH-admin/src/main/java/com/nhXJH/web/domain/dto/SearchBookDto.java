package com.nhXJH.web.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author xjh
 * @since 2022/5/5
 * Time: 9:52
 * 图书搜索参数
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchBookDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("搜索关键字")
    private String keyWords;

    private Integer pageNum;

    private Integer pageSize;
}
