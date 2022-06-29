import request from '@/utils/request'

// 查询表信息映射列表
export function listMap(query) {
  return request({
    url: '/userApplication/map/list',
    method: 'get',
    params: query
  })
}

// 查询表信息映射详细
export function getMap(id) {
  return request({
    url: '/userApplication/map/' + id,
    method: 'get'
  })
}

// 新增表信息映射
export function addMap(data) {
  return request({
    url: '/userApplication/map',
    method: 'post',
    data: data
  })
}

// 修改表信息映射
export function updateMap(data) {
  return request({
    url: '/userApplication/map',
    method: 'put',
    data: data
  })
}

// 删除表信息映射
export function delMap(id) {
  return request({
    url: '/userApplication/map/' + id,
    method: 'delete'
  })
}
