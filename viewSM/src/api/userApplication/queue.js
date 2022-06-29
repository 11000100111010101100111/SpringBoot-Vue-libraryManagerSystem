import request from '@/utils/request'

// 查询申请队列信息列表
export function listQueue(query) {
  return request({
    url: '/userApplication/queue/list',
    method: 'get',
    params: query
  })
}

// 查询申请队列信息详细
export function getQueue(id) {
  return request({
    url: '/userApplication/queue/' + id,
    method: 'get'
  })
}

// 新增申请队列信息
export function addQueue(data) {
  return request({
    url: '/userApplication/queue',
    method: 'post',
    data: data
  })
}

// 修改申请队列信息
export function updateQueue(data) {
  return request({
    url: '/userApplication/queue',
    method: 'put',
    data: data
  })
}
// 处理申请
export function approve(data) {
  return request({
    url: '/userApplication/queue/approve',
    method: 'post',
    data: data
  })
}

// 删除申请队列信息
export function delQueue(id) {
  return request({
    url: '/userApplication/queue/' + id,
    method: 'delete'
  })
}

// 删除申请队列信息
export function send() {
  return request({
    url: '/mq',
    method: 'get'
  })
}

//readAll
export function readAll(ids) {
  return request({
    url: '/userApplication/queue/readAll/'+ids,
    method: 'get'
  })
}
