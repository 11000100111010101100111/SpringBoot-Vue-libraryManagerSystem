package com.nhXJH.web.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhXJH.web.domain.BasePurchaseRequisition;
import com.nhXJH.web.domain.BookStockLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 图书库存出入信息Mapper接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Mapper
public interface BookStockLogMapper  extends BaseMapper<BookStockLog> {
    /**
     * 查询图书库存出入信息
     * 
     * @param id 图书库存出入信息主键
     * @return 图书库存出入信息
     */
    public BookStockLog selectBookStockLogById(Long id);
    public Integer getCount(BookStockLog bookStockLog);
    /**
     * 查询图书库存出入信息
     *
     * @param id 图书库存出入信息图书库存id
     * @return 图书库存出入信息
     */
    public BookStockLog selectByStockId(Long id);

    /**
     * 查询图书库存出入信息列表
     * 
     * @param bookStockLog 图书库存出入信息
     * @return 图书库存出入信息集合
     */
    public List<BookStockLog> selectBookStockLogList(BookStockLog bookStockLog);
/**
     * 查询图书库存出入信息列表
     *
     * @param stockId 库位ID
     * @return 图书库存出入信息集合
     */
    public List<BookStockLog> selectLogsByStockId(Long stockId);

    /**
     * 新增图书库存出入信息
     * 
     * @param bookStockLog 图书库存出入信息
     * @return 结果
     */
    public int insertBookStockLog(BookStockLog bookStockLog);
    /**
     * 新增图书库存出入信息
     *
     * @param list 图书库存出入信息
     * @return 结果
     */
    public int insertLog(List<BookStockLog> list);

    /**
     * 修改图书库存出入信息
     * 
     * @param bookStockLog 图书库存出入信息
     * @return 结果
     */
    public int updateBookStockLog(BookStockLog bookStockLog);

    /**
     * 删除图书库存出入信息
     * 
     * @param id 图书库存出入信息主键
     * @return 结果
     */
    public int deleteBookStockLogById(Long id);

    /**
     * 批量删除图书库存出入信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBookStockLogByIds(Long[] ids);
}
