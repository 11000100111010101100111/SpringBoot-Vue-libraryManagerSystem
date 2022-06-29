import request from '@/utils/request'

// 查询计量单位换算信息列表
export function listConversion(query) {
  return request({
    url: '/userApplication/conversion/list',
    method: 'get',
    params: query
  })
}

// 查询计量单位换算信息详细
export function getConversion(id) {
  return request({
    url: '/userApplication/conversion/' + id,
    method: 'get'
  })
}

// 新增计量单位换算信息
export function addConversion(data) {
  return request({
    url: '/userApplication/conversion',
    method: 'post',
    data: data
  })
}

// 修改计量单位换算信息
export function updateConversion(data) {
  return request({
    url: '/userApplication/conversion',
    method: 'put',
    data: data
  })
}

// 删除计量单位换算信息
export function delConversion(id) {
  return request({
    url: '/userApplication/conversion/' + id,
    method: 'delete'
  })
}
