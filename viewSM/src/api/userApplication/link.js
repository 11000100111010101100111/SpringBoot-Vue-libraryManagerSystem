import request from '@/utils/request'

// 查询采购申请结点处理列表
export function listLink(query) {
  return request({
    url: '/userApplication/link/list',
    method: 'get',
    params: query
  })
}

// 查询采购申请结点处理列表
export function linkNodes(query) {
  return request({
    url: '/userApplication/link/link',
    method: 'get',
    params: query
  })
}

// 查询采购申请结点处理详细
export function getLink(id) {
  return request({
    url: '/userApplication/link/' + id,
    method: 'get'
  })
}

// 新增采购申请结点处理
export function addLink(data) {
  return request({
    url: '/userApplication/link',
    method: 'post',
    data: data
  })
}

// 修改采购申请结点处理
export function updateLink(data) {
  return request({
    url: '/userApplication/link',
    method: 'put',
    data: data
  })
}

// 删除采购申请结点处理
export function delLink(id) {
  return request({
    url: '/userApplication/link/' + id,
    method: 'delete'
  })
}
