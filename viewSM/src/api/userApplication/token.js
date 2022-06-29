import request from '@/utils/request'

// 查询附件访问秘钥信息列表
export function listToken(query) {
  return request({
    url: '/userApplication/token/list',
    method: 'get',
    params: query
  })
}

// 查询附件访问秘钥信息详细
export function getToken(id) {
  return request({
    url: '/userApplication/token/' + id,
    method: 'get'
  })
}

// 新增附件访问秘钥信息
export function addToken(data) {
  return request({
    url: '/userApplication/token',
    method: 'post',
    data: data
  })
}

// 修改附件访问秘钥信息
export function updateToken(data) {
  return request({
    url: '/userApplication/token',
    method: 'put',
    data: data
  })
}

// 删除附件访问秘钥信息
export function delToken(id) {
  return request({
    url: '/userApplication/token/' + id,
    method: 'delete'
  })
}
