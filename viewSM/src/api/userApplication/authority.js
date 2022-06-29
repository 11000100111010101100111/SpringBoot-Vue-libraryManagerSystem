import request from '@/utils/request'

// 查询用户可访问部门信息列表
export function listAuthority(query) {
  return request({
    url: '/userApplication/authority/list',
    method: 'get',
    params: query
  })
}

// 查询用户可访问部门信息详细
export function getAuthority(id) {
  return request({
    url: '/userApplication/authority/' + id,
    method: 'get'
  })
}

// 新增用户可访问部门信息
export function addAuthority(data) {
  return request({
    url: '/userApplication/authority',
    method: 'post',
    data: data
  })
}

export function getAuthorityDept(userId) {
  return request({
    url: '/userApplication/authority/dept/'+userId,
    method: 'get'
  })
}

// 新增用户可访问部门信息
export function addAuthoritys(data) {
  return request({
    url: '/userApplication/authority/adds/'+data,
    method: 'delete',
  })
}

// 新增用户可访问部门信息
export function addDeptAuthority(data) {
  return request({
    url: '/userApplication/authority/addAuthority',
    method: 'post',
    data:data
  })
}

// 修改用户可访问部门信息
export function updateAuthority(data) {
  return request({
    url: '/userApplication/authority',
    method: 'put',
    data: data
  })
}

// 删除用户可访问部门信息
export function delAuthority(id) {
  return request({
    url: '/userApplication/authority/del/' + id,
    method: 'delete'
  })
}
