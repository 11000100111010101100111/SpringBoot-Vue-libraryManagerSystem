import request from '@/utils/request'

// 查询出版社信息列表
export function listPress(query) {
  return request({
    url: '/userApplication/press/list',
    method: 'get',
    params: query
  })
}

// 查询出版社信息列表
export function allPress(query) {
  return request({
    url: '/userApplication/press/all',
    method: 'get',
    params: query
  })
}

// 查询出版社信息详细
export function getPress(id) {
  return request({
    url: '/userApplication/press/' + id,
    method: 'get'
  })
}

// 新增出版社信息
export function addPress(data) {
  return request({
    url: '/userApplication/press',
    method: 'post',
    data: data
  })
}

// 修改出版社信息
export function updatePress(data) {
  return request({
    url: '/userApplication/press',
    method: 'put',
    data: data
  })
}

// 删除出版社信息
export function delPress(id) {
  return request({
    url: '/userApplication/press/' + id,
    method: 'delete'
  })
}

//修改状态
export function changePressStatus(id, status) {
  const data = {
    id,
    status
  };
  return request({
    url: '/userApplication/press/changeStatus',
    method: 'put',
    data: data
  })
}
