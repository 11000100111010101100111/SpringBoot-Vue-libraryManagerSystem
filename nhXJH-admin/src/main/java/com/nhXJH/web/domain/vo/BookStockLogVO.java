package com.nhXJH.web.domain.vo;

import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.web.domain.BookStockLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/26
 * Time: 15:41
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookStockLogVO extends BookStockLog {
    SysUser createUser;
    SysUser updateUser;
    SysUser otherUser;
    BookStockLog stockLog;
}
