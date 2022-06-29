import request from '@/utils/request'
import da from "element-ui/src/locale/lang/da";

// 查询采购申请信息列表
export function listPurchase(query) {
  return request({
    url: '/userApplication/purchase/list',
    method: 'get',
    params: query
  })
}
// 查询采购申请信息列表
export function mine(query) {
  return request({
    url: '/userApplication/purchase/mine',
    method: 'get',
    params: query
  })
}
// 查询采购申请信息列表
export function vo(query) {
  return request({
    url: '/userApplication/purchase/vo',
    method: 'get',
    params: query
  })
}
// 查询审批流程结点信息列表
export function approvedPurchaseNode(query) {
  return request({
    url: '/userApplication/purchase/approved/list',
    method: 'get',
    params: query
  })
}
// 查询采购申请信息详细
export function getPurchase(id) {
  return request({
    url: '/userApplication/purchase/' + id,
    method: 'get'
  })
}

// 新增采购申请信息
export function addPurchase(data) {
  const type=0;
  data.submitType = type;
  return request({
    url: '/userApplication/purchase',
    method: 'post',
    data: JSON.stringify(data)
  })
}

// 修改采购申请信息
export function updatePurchase(data) {
  const type=0;
  data.submitType = type;
  return request({
    url: '/userApplication/purchase',
    method: 'put',
    data: JSON.stringify(data)
  })
}
// 修改采购申请信息
export function approvePurchase(data) {
  const type=1;
  data.submitType = type;
  return request({
    url: '/userApplication/purchase',
    method: 'put',
    data: JSON.stringify(data)
  })
}

// 删除采购申请信息
export function delPurchase(id) {
  return request({
    url: '/userApplication/purchase/' + id,
    method: 'delete'
  })
}

export function test(html) {
  return request({
    url:'/userApplication/purchase/test',
    method:'post',
    data:{'html':html}
  });

}
