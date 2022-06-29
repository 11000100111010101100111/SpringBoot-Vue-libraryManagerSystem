import request from '@/utils/request'

// 查询采购申请子列表
export function listPurchaseSub(query) {
  return request({
    url: '/userApplication/purchaseSub/list',
    method: 'get',
    params: query
  })
}
// 查询采购申请子列表
export function purchaseSubList(query) {
  return request({
    url: '/userApplication/purchaseSub/subs',
    method: 'get',
    params: query
  })
}// 查询采购申请子列表
export function inStock(query) {
  return request({
    url: '/userApplication/purchaseSub/inStock',
    method: 'get',
    params: query
  })
}

// 查询采购申请子详细
export function getPurchaseSub(id) {
  return request({
    url: '/userApplication/purchaseSub/' + id,
    method: 'get'
  })
}

// 新增采购申请子
export function addPurchaseSub(data) {
  return request({
    url: '/userApplication/purchaseSub',
    method: 'post',
    data: data
  })
}

// 修改采购申请子
export function updatePurchaseSub(data) {
  return request({
    url: '/userApplication/purchaseSub',
    method: 'put',
    data: data
  })
}

// 删除采购申请子
export function delPurchaseSub(id) {
  return request({
    url: '/userApplication/purchaseSub/' + id,
    method: 'delete'
  })
}
