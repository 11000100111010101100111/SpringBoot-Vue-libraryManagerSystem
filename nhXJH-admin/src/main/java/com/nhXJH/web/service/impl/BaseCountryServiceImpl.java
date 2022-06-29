package com.nhXJH.web.service.impl;

import java.util.List;
import com.nhXJH.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhXJH.web.mapper.BaseCountryMapper;
import com.nhXJH.web.domain.BaseCountry;
import com.nhXJH.web.service.IBaseCountryService;

/**
 * 国家信息Service业务层处理
 * 
 * @author xjh
 * @date 2022-03-01
 */
@Service
public class BaseCountryServiceImpl extends ServiceImpl<BaseCountryMapper, BaseCountry>
        implements IBaseCountryService  {
    @Autowired
    private BaseCountryMapper baseCountryMapper;

    /**
     * 查询国家信息
     * 
     * @param id 国家信息主键
     * @return 国家信息
     */
    @Override
    public BaseCountry selectBaseCountryById(Long id) {
        return baseCountryMapper.selectBaseCountryById(id);
    }

    @Override
    public Integer getCount(BaseCountry baseCountry) {
        return baseCountryMapper.getCount(baseCountry);
    }

    /**
     * 查询国家信息列表
     * 
     * @param baseCountry 国家信息
     * @return 国家信息
     */
    @Override
    public List<BaseCountry> selectBaseCountryList(BaseCountry baseCountry) {
        return baseCountryMapper.selectBaseCountryList(baseCountry);
    }

    /**
     * 新增国家信息
     * 
     * @param baseCountry 国家信息
     * @return 结果
     */
    @Override
    public int insertBaseCountry(BaseCountry baseCountry) {
        baseCountry.setCreateTime(DateUtils.getNowDate());
        return baseCountryMapper.insertBaseCountry(baseCountry);
    }

    /**
     * 修改国家信息
     * 
     * @param baseCountry 国家信息
     * @return 结果
     */
    @Override
    public int updateBaseCountry(BaseCountry baseCountry) {
        baseCountry.setUpdateTime(DateUtils.getNowDate());
        return baseCountryMapper.updateBaseCountry(baseCountry);
    }

    /**
     * 批量删除国家信息
     * 
     * @param ids 需要删除的国家信息主键
     * @return 结果
     */
    @Override
    public int deleteBaseCountryByIds(Long[] ids) {
        return baseCountryMapper.deleteBaseCountryByIds(ids);
    }

    /**
     * 删除国家信息信息
     * 
     * @param id 国家信息主键
     * @return 结果
     */
    @Override
    public int deleteBaseCountryById(Long id) {
        return baseCountryMapper.deleteBaseCountryById(id);
    }
}
