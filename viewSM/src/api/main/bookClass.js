import request from '@/utils/request'

// 查询中国标准图书分类规则列表
export function listBookClass(query) {
  return request({
    url: '/main/bookClass/list',
    method: 'get',
    params: query
  })
}

// 查询中国标准图书分类规则详细
export function getBookClass(id) {
  return request({
    url: '/main/bookClass/' + id,
    method: 'get'
  })
}

// 新增中国标准图书分类规则
export function addBookClass(data) {
  return request({
    url: '/main/bookClass',
    method: 'post',
    data: data
  })
}

// 修改中国标准图书分类规则
export function updateBookClass(data) {
  return request({
    url: '/main/bookClass',
    method: 'put',
    data: data
  })
}

// 删除中国标准图书分类规则
export function delBookClass(id) {
  return request({
    url: '/main/bookClass/' + id,
    method: 'delete'
  })
}

// 状态修改
export function changeStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/main/bookClass/changeStatus',
    method: 'put',
    data: data
  })
}
// 查询图书类别下拉树结构
export function bookclasstreeselect() {
  return request({
    url: '/main/bookClass/tree',
    method: 'get'
  })
}
// 查询图书类别下拉树结构
export function bookclasstreeselectBook() {
  return request({
    url: '/main/bookClass/book/tree',
    method: 'get'
  })
}
// 查询图书类别下拉树结构
export function bookclasstreeselectResource() {
  return request({
    url: '/main/bookClass/resource/tree',
    method: 'get'
  })
}
