import request from '@/utils/request'

// 查询国家信息列表
export function listCountry(query) {
  return request({
    url: '/userApplication/country/list',
    method: 'get',
    params: query
  })
}

// 查询国家信息详细
export function getCountry(id) {
  return request({
    url: '/userApplication/country/' + id,
    method: 'get'
  })
}

// 新增国家信息
export function addCountry(data) {
  return request({
    url: '/userApplication/country',
    method: 'post',
    data: data
  })
}

// 修改国家信息
export function updateCountry(data) {
  return request({
    url: '/userApplication/country',
    method: 'put',
    data: data
  })
}

// 删除国家信息
export function delCountry(id) {
  return request({
    url: '/userApplication/country/' + id,
    method: 'delete'
  })
}
