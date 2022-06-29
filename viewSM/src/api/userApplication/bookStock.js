import request from '@/utils/request'

// 查询图书库存信息列表
export function listStock(query) {
  return request({
    url: '/userApplication/bookStock/list',
    method: 'get',
    params: query
  })
}// 查询图书库存信息列表
export function getCode(query) {
  return request({
    url: '/userApplication/bookStock/getCode',
    method: 'get',
    params: query
  })
}
// 查询图书库存信息列表
export function browBook(code) {
  return request({
    url: '/userApplication/bookStock/browBook/'+code,
    method: 'get'
  })
}
// 查询图书库存信息列表
export function brow(code) {
  return request({
    url: '/userApplication/bookStock/brow/'+code,
    method: 'get'
  })
}

// 查询图书库存信息详细
export function getStock(id) {
  return request({
    url: '/userApplication/bookStock/' + id,
    method: 'get'
  })
}

// 新增图书库存信息
export function addStock(data) {
  return request({
    url: '/userApplication/bookStock',
    method: 'post',
    data: data
  })
}

// 修改图书库存信息
export function updateStock(data) {
  return request({
    url: '/userApplication/bookStock',
    method: 'put',
    data: data
  })
}

// 删除图书库存信息
export function delStock(id) {
  return request({
    url: '/userApplication/bookStock/' + id,
    method: 'delete'
  })
}
