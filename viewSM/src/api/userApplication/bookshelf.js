import request from '@/utils/request'

// 查询书架信息列表
export function listBookshelf(query) {
  return request({
    url: '/userApplication/bookshelf/list',
    method: 'get',
    params: query
  })
}

// 查询书架信息详细
export function getBookshelf(id) {
  return request({
    url: '/userApplication/bookshelf/' + id,
    method: 'get'
  })
}

// 新增书架信息
export function addBookshelf(data) {
  return request({
    url: '/userApplication/bookshelf',
    method: 'post',
    data: data
  })
}

// 修改书架信息
export function updateBookshelf(data) {
  return request({
    url: '/userApplication/bookshelf',
    method: 'put',
    data: data
  })
}

// 删除书架信息
export function delBookshelf(id) {
  return request({
    url: '/userApplication/bookshelf/' + id,
    method: 'delete'
  })
}

// 改变书架状态
export function changeBookShelfStatus(id, status) {
  const data = {
    id,
    status
  };
  return request({
    url: '/userApplication/bookshelf/changeStatus',
    method: 'put',
    data: data
  })
}
