package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.common.core.domain.model.LoginUser;
import com.nhXJH.common.core.page.TableDataInfo;
import com.nhXJH.web.domain.BaseFile;
import com.nhXJH.web.domain.LibraryAuth;
import com.nhXJH.web.domain.LibraryBook;
import com.nhXJH.web.domain.dto.LibraryBookDto;
import com.nhXJH.web.domain.dto.SearchBookDto;
import com.nhXJH.web.domain.param.BookFileParam;
import com.nhXJH.web.domain.vo.BookVO;
import com.nhXJH.web.domain.vo.LibraryBookDetailVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图书实体信息Service接口
 * 
 * @author xjh
 * @date 2022-02-07
 */
public interface ILibraryBookService extends IService<LibraryBook>{
    /**
     * 查询图书实体信息
     * 
     * @param id 图书实体信息主键
     * @return 图书实体信息
     */
    public LibraryBook selectLibraryBookById(Long id);
    public Integer getCount(LibraryBook libraryBook);
    /**
     * 查询图书实体信息
     *
     * @param id 图书实体信息主键
     * @return 图书实体信息
     */
    public LibraryBookDetailVO selectLibraryBookDetailById(Long id);
    /**
     * 查询图书实体信息列表
     * 
     * @param libraryBook 图书实体信息
     * @return 图书实体信息集合
     */
    public List<LibraryBook> selectLibraryBookList(LibraryBook libraryBook);

    public List<LibraryBookDto> selectLibraryBookDtoList(LibraryBook libraryBook);

    public Integer getBookCount(SearchBookDto dto);
    public TableDataInfo getBook(SearchBookDto dto);
    /**
     * 新增图书实体信息
     * 
     * @param libraryBook 图书实体信息
     * @return 结果
     */
    public int insertLibraryBook(LibraryBook libraryBook);

    /**
     * 修改图书实体信息
     * 
     * @param libraryBook 图书实体信息
     * @return 结果
     */
    public int updateLibraryBook(LibraryBook libraryBook);

    /**
     * 批量删除图书实体信息
     * 
     * @param ids 需要删除的图书实体信息主键集合
     * @return 结果
     */
    public int deleteLibraryBookByIds(Long[] ids,Long operator);

    /**
     * 删除图书实体信息信息
     * 
     * @param id 图书实体信息主键
     * @return 结果
     */
    public int deleteLibraryBookById(Long id,Long operator);

    /**
     * 修改图书状态
     *
     * @param book 图书信息
     * @return 结果
     */
    public int updateUserStatus(LibraryBook book);

    /**附件上传**/
    public BaseFile uploadFile(BookFileParam fileParam);
}
