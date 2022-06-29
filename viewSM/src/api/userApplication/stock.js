import request from '@/utils/request'

// 查询图书库存信息列表
export function listStock(query) {
  return request({
    url: '/userApplication/stock/list',
    method: 'get',
    params: query
  })
}
// 查询库位详细信息
export function detail(query) {
  return request({
    url: '/userApplication/stock/detail/'+query,
    method: 'get'
  })
}

// 查询图书库存信息详细
export function getStock(id) {
  return request({
    url: '/userApplication/stock/' + id,
    method: 'get'
  })
}

// 新增图书库存信息
export function addStock(data) {
  return request({
    url: '/userApplication/stock',
    method: 'post',
    data: data
  })
}
// 新增图书库存信息
export function addMoreStock(data) {
  return request({
    url: '/userApplication/stock/more',
    method: 'post',
    data: data
  })
}

// 修改图书库存信息
export function updateStock(data) {
  return request({
    url: '/userApplication/stock',
    method: 'put',
    data: data
  })
}

// 删除图书库存信息
export function delStock(id) {
  return request({
    url: '/userApplication/stock/' + id,
    method: 'delete'
  })
}
