import request from '@/utils/request'

// 查询作信息者列表
export function listAuth(query) {
  return request({
    url: '/userApplication/auth/list',
    method: 'get',
    params: query
  })
}
// 查询作信息者列表
export function allAuth(query) {
  return request({
    url: '/userApplication/auth/all',
    method: 'get',
    params: query
  })
}

// 查询作信息者详细
export function getAuth(id) {
  return request({
    url: '/userApplication/auth/' + id,
    method: 'get'
  })
}

// 新增作信息者
export function addAuth(data) {
  return request({
    url: '/userApplication/auth',
    method: 'post',
    data: data
  })
}

// 修改作信息者
export function updateAuth(data) {
  return request({
    url: '/userApplication/auth',
    method: 'put',
    data:data
  })
}

// 删除作信息者
export function delAuth(id) {
  return request({
    url: '/userApplication/auth/' + id,
    method: 'delete'
  })
}

//修改状态
export function changeAuthStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/userApplication/auth/changeStatus',
    method: 'put',
    data: data
  })
}

//获取图像
export function getFile(query) {
  return request({
    url: '/userApplication/auth/getFile/'+query,
    method: 'get'
  })
}
