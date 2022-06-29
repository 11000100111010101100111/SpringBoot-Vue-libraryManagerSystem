package com.nhXJH.web.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhXJH.web.domain.LibraryAuth;
import com.nhXJH.web.domain.LibraryBook;
import com.nhXJH.web.domain.dto.LibraryBookDto;
import com.nhXJH.web.domain.dto.SearchBookDto;
import com.nhXJH.web.domain.vo.BookVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 图书实体信息Mapper接口
 * 
 * @author xjh
 * @date 2022-02-07
 */
@Mapper
public interface LibraryBookMapper  extends BaseMapper<LibraryBook> {
    /**
     * 查询图书实体信息
     * 
     * @param id 图书实体信息主键
     * @return 图书实体信息
     */
    public LibraryBook selectLibraryBookById(Long id);
    public Integer getCount(LibraryBook libraryBook);
    /**
     * 查询图书实体信息列表
     * 
     * @param libraryBook 图书实体信息
     * @return 图书实体信息集合
     */
    public List<LibraryBook> selectLibraryBookList(LibraryBook libraryBook);

    public List<LibraryBookDto> selectLibraryBookDtoList(LibraryBook libraryBook);

    public List<BookVO> getBookVo(@Param("param") SearchBookDto dto);
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
     * 删除图书实体信息
     * 
     * @param id 图书实体信息主键
     * @param operator 操作人id
     * @return 结果
     */
    public int deleteLibraryBookById(Long id,Long operator);

    /**
     * 批量删除图书实体信息
     * 
     * @param ids 需要删除的数据主键集合
     * @param operator 操作人id
     * @return 结果
     */
    public int deleteLibraryBookByIds(Long[] ids,Long operator);
}
