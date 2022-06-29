import request from '@/utils/request'

// 查询采购申请信息列表
export function listRequisition(query) {
  return request({
    url: '/userApplication/requisition/list',
    method: 'get',
    params: query
  })
}
// 查询我的采购申请信息列表
export function mine(query) {
  return request({
    url: '/userApplication/requisition/mine',
    method: 'get',
    params: query
  })
}

// 查询采购申请信息详细
export function getRequisition(id) {
  return request({
    url: '/userApplication/requisition/' + id,
    method: 'get'
  })
}

// 新增采购申请信息
export function addRequisition(data) {
  return request({
    url: '/userApplication/requisition',
    method: 'post',
    data: data
  })
}

// 修改采购申请信息
export function updateRequisition(data) {
  return request({
    url: '/userApplication/requisition',
    method: 'put',
    data: data
  })
}

// 删除采购申请信息
export function delRequisition(id) {
  return request({
    url: '/userApplication/requisition/' + id,
    method: 'delete'
  })
}
