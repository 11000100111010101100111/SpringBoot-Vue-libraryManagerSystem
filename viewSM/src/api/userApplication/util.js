import request from '@/utils/request'

// 查询物品单位信息列表
export function listUtil(query) {
  return request({
    url: '/userApplication/util/list',
    method: 'get',
    params: query
  })
}
// 查询物品单位信息列表
export function allUtil(query) {
  return request({
    url: '/userApplication/util/all',
    method: 'post',
    data: query
  })
}
// 查询物品单位信息详细
export function getUtil(id) {
  return request({
    url: '/userApplication/util/' + id,
    method: 'get'
  })
}

// 新增物品单位信息
export function addUtil(data) {
  return request({
    url: '/userApplication/util',
    method: 'post',
    data: data
  })
}

// 修改物品单位信息
export function updateUtil(data) {
  return request({
    url: '/userApplication/util',
    method: 'put',
    data: data
  })
}

// 删除物品单位信息
export function delUtil(id) {
  return request({
    url: '/userApplication/util/' + id,
    method: 'delete'
  })
}
