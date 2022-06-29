package com.nhXJH.web.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhXJH.web.domain.ApprovalQueue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 申请队列信息Mapper接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Mapper
public interface ApprovalQueueMapper extends BaseMapper<ApprovalQueue> {
    /**
     * 查询申请队列信息
     * 
     * @param id 申请队列信息主键
     * @return 申请队列信息
     */
    public ApprovalQueue selectApprovalQueueById(Long id);

    /**
     * 查询申请队列信息列表
     * 
     * @param approvalQueue 申请队列信息
     * @return 申请队列信息集合
     */
    public List<ApprovalQueue> selectApprovalQueueList(ApprovalQueue approvalQueue);
    public Integer getCount(ApprovalQueue approvalQueue);

    /**
     * 新增申请队列信息
     * 
     * @param approvalQueue 申请队列信息
     * @return 结果
     */
    public int insertApprovalQueue(ApprovalQueue approvalQueue);
    /**
     * 新增申请队列信息
     *
     * @param list 申请队列信息列表
     * @return 结果
     */
    public int insertList(List<ApprovalQueue> list);

    /**
     * 修改申请队列信息
     * 
     * @param approvalQueue 申请队列信息
     * @return 结果
     */
    public int updateApprovalQueue(ApprovalQueue approvalQueue);

    /**
     * 删除申请队列信息
     * 
     * @param id 申请队列信息主键
     * @return 结果
     */
    public int deleteApprovalQueueById(@Param("id") Long id,@Param("operator") Long operator);

    /**
     * 批量删除申请队列信息
     * 
     * @param array 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteApprovalQueueByIds(@Param("array") Long[] array,@Param("operator") Long operator);

    /**
     * 已读
     *
     * @param array 需要删除的申请队列信息主键集合
     * @return 结果
     */
    public int readAll(@Param("array") Long[] array,@Param("operator") Long operator);

    /**
     * 批量删除同一批次申请队列信息
     *
     * @param array 需要删除的数据主键集合
     * @return 结果
     */
    public int delGroup(@Param("array") Long[] array,@Param("operator") Long operator);
}
