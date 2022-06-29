import request from '@/utils/request'

// 查询图书实体信息列表
export function listBook(query) {
  return request({
    url: '/userApplication/book/list',
    method: 'get',
    params: query
  })
}

// 查询图书实体信息详细
export function keyWords(data) {
  return request({
    url: '/userApplication/book/getBook',
    method: 'post',
    data: data
  })
}
// 查询图书实体信息详细
export function getBook(id) {
  return request({
    url: '/userApplication/book/' + id,
    method: 'get'
  })
}
// 查询图书实体信息详细
export function getBookDetail(id) {
  return request({
    url: '/userApplication/book/detail/' + id,
    method: 'get'
  })
}
// 新增图书实体信息
export function addBook(data) {
  return request({
    url: '/userApplication/book',
    method: 'post',
    data: data
  })
}

// 修改图书实体信息
export function updateBook(data) {
  return request({
    url: '/userApplication/book',
    method: 'put',
    data: data
  })
}

// 删除图书实体信息
export function delBook(id) {
  return request({
    url: '/userApplication/book/' + id,
    method: 'delete'
  })
}
export function uploadFile(data) {
  return request({
    url: '/userApplication/book/upload',
    method: 'post',
    data:data
  })
}

// 改变图书状态
export function changeBookStatus(id, status) {
  const data = {
    id,
    status
  };
  return request({
    url: '/userApplication/book/changeStatus',
    method: 'put',
    data: data
  })
}
