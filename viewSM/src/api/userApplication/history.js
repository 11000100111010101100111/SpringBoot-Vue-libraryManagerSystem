import request from '@/utils/request'

// 查询借书信息列表
export function listHistory(query) {
  return request({
    url: '/userApplication/history/list',
    method: 'get',
    params: query
  })
}
// 查询借书信息列表
export function getMyHistory(query) {
  return request({
    url: '/userApplication/history/mine/history',
    method: 'get',
    params: query
  })
}
// 查询借书信息列表
export function getBackHistory(query) {
  return request({
    url: '/userApplication/history/mine/back/history',
    method: 'get',
    params: query
  })
}
// 查询借书信息列表
export function id(query) {
  return request({
    url: '/userApplication/history/mine/back/id',
    method: 'get',
    params: query
  })
}
// 查询借书信息列表
export function back(query) {
  return request({
    url: '/userApplication/history/mine/back',
    method: 'get',
    params: query
  })
}

// 查询借书信息详细
export function getHistory(id) {
  return request({
    url: '/userApplication/history/' + id,
    method: 'get'
  })
}

// 新增借书信息
export function addHistory(data) {
  return request({
    url: '/userApplication/history',
    method: 'post',
    data: data
  })
}

// 修改借书信息
export function updateHistory(data) {
  return request({
    url: '/userApplication/history',
    method: 'put',
    data: data
  })
}

// 删除借书信息
export function delHistory(id) {
  return request({
    url: '/userApplication/history/' + id,
    method: 'delete'
  })
}
