import request from '@/utils/request'

// 查询图书管理机构信息列表
export function listMechanism(query) {
  return request({
    url: '/userApplication/mechanism/list',
    method: 'get',
    params: query
  })
}

// 查询图书管理机构信息详细
export function getMechanism(id) {
  return request({
    url: '/userApplication/mechanism/' + id,
    method: 'get'
  })
}

// 新增图书管理机构信息
export function addMechanism(data) {
  return request({
    url: '/userApplication/mechanism',
    method: 'post',
    data: data
  })
}

// 修改图书管理机构信息
export function updateMechanism(data) {
  return request({
    url: '/userApplication/mechanism',
    method: 'put',
    data: data
  })
}

// 删除图书管理机构信息
export function delMechanism(id) {
  return request({
    url: '/userApplication/mechanism/' + id,
    method: 'delete'
  })
}

//修改状态
export function changeMechanismStatus(id, status) {
  const data = {
    id,
    status
  };
  return request({
    url: '/userApplication/mechanism/changeStatus',
    method: 'put',
    data: data
  })
}

//获取图像
export function getFile(query) {
  return request({
    url: '/userApplication/mechanism/getFile/'+query,
    method: 'get'
  })
}
//获取图像具体信息
export function getFileList(query) {
  return request({
    url: '/userApplication/mechanism/getFileList/',
    method: 'post',
    data:query
  })
}

//分析字符串获取关键词
export function analysisStr(id) {
  const data = {
    id
  };
  return request({
    url: '/userApplication/mechanism/analysisStr',
    method: 'post',
    data: data
  })
}

//分析字符串获取关键词
export function reflushFile(data,id) {
  return request({
    url: '/userApplication/mechanism/reflushFile?id='+id,
    method: 'post',
    data: data
  })
}
