package com.nhXJH.web.domain.vo;

import com.nhXJH.web.domain.BaseFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/15
 * Time: 20:58
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileVo extends BaseFile {
    String fileName;
    String newName;
    Long groupId;
    String createPersonalName;
    String updatePersonalName;
}
