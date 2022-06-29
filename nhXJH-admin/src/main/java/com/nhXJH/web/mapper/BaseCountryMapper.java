package com.nhXJH.web.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhXJH.web.domain.ApprovalQueue;
import com.nhXJH.web.domain.BaseCountry;
import org.apache.ibatis.annotations.Mapper;

/**
 * 国家信息Mapper接口
 * 
 * @author xjh
 * @date 2022-03-01
 */
@Mapper
public interface BaseCountryMapper  extends BaseMapper<BaseCountry> {
    /**
     * 查询国家信息
     * 
     * @param id 国家信息主键
     * @return 国家信息
     */
    public BaseCountry selectBaseCountryById(Long id);
    public Integer getCount(BaseCountry baseCountry);
    /**
     * 查询国家信息列表
     * 
     * @param baseCountry 国家信息
     * @return 国家信息集合
     */
    public List<BaseCountry> selectBaseCountryList(BaseCountry baseCountry);

    /**
     * 新增国家信息
     * 
     * @param baseCountry 国家信息
     * @return 结果
     */
    public int insertBaseCountry(BaseCountry baseCountry);

    /**
     * 修改国家信息
     * 
     * @param baseCountry 国家信息
     * @return 结果
     */
    public int updateBaseCountry(BaseCountry baseCountry);

    /**
     * 删除国家信息
     * 
     * @param id 国家信息主键
     * @return 结果
     */
    public int deleteBaseCountryById(Long id);

    /**
     * 批量删除国家信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaseCountryByIds(Long[] ids);
}
