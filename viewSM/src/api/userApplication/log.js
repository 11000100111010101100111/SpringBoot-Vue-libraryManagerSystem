import request from '@/utils/request'

// 查询图书库存出入信息列表
export function listLog(query) {
  return request({
    url: '/userApplication/log/list',
    method: 'get',
    params: query
  })
}
// 查询图书库存出入记录
export function getLogs(stockId) {
  return request({
    url: '/userApplication/log/getLog/'+stockId,
    method: 'get'
  })
}
// 查询图书库存出入信息详细
export function getLog(id) {
  return request({
    url: '/userApplication/log/' + id,
    method: 'get'
  })
}

// 新增图书库存出入信息
export function addLog(data) {
  return request({
    url: '/userApplication/log',
    method: 'post',
    data: data
  })
}

// 修改图书库存出入信息
export function updateLog(data) {
  return request({
    url: '/userApplication/log',
    method: 'put',
    data: data
  })
}

// 删除图书库存出入信息
export function delLog(id) {
  return request({
    url: '/userApplication/log/' + id,
    method: 'delete'
  })
}
