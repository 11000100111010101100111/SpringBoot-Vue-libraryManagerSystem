package com.nhXJH.web.domain.param;

import com.nhXJH.web.domain.BaseFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/9
 * Time: 11:35
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookFileParam extends BaseFile {
    Long bookId;
    String fileType;
    String bookTitle;
}
