import request from '@/utils/request'

// 查询审批流程结点信息列表
export function listPurchaseNode(query) {
  return request({
    url: '/userApplication/purchaseNode/list',
    method: 'get',
    params: query
  })
}

// 查询审批流程结点信息详细
export function getPurchaseNode(id) {
  return request({
    url: '/userApplication/purchaseNode/' + id,
    method: 'get'
  })
}

// 新增审批流程结点信息
export function addPurchaseNode(data) {
  return request({
    url: '/userApplication/purchaseNode',
    method: 'post',
    data: data
  })
}

// 修改审批流程结点信息
export function updatePurchaseNode(data) {
  return request({
    url: '/userApplication/purchaseNode',
    method: 'put',
    data: data
  })
}

// 删除审批流程结点信息
export function delPurchaseNode(id) {
  return request({
    url: '/userApplication/purchaseNode/' + id,
    method: 'delete'
  })
}
