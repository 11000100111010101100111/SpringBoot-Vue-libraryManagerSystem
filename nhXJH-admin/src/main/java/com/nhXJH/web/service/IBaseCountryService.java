package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.web.domain.ApprovalQueue;
import com.nhXJH.web.domain.BaseCountry;

/**
 * 国家信息Service接口
 * 
 * @author xjh
 * @date 2022-03-01
 */
public interface IBaseCountryService extends IService<BaseCountry>{
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
     * 批量删除国家信息
     * 
     * @param ids 需要删除的国家信息主键集合
     * @return 结果
     */
    public int deleteBaseCountryByIds(Long[] ids);

    /**
     * 删除国家信息信息
     * 
     * @param id 国家信息主键
     * @return 结果
     */
    public int deleteBaseCountryById(Long id);
}
