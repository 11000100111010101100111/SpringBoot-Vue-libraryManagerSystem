import request from '@/utils/request'

// 查询书架层信息列表
export function listStorey(query) {
  return request({
    url: '/userApplication/storey/list',
    method: 'get',
    params: query
  })
}

// 查询书架层信息详细
export function getStorey(id) {
  return request({
    url: '/userApplication/storey/' + id,
    method: 'get'
  })
}

// 新增书架层信息
export function addStorey(data) {
  return request({
    url: '/userApplication/storey',
    method: 'post',
    data: data
  })
}

// 修改书架层信息
export function updateStorey(data) {
  return request({
    url: '/userApplication/storey',
    method: 'put',
    data: data
  })
}

// 删除书架层信息
export function delStorey(id) {
  return request({
    url: '/userApplication/storey/' + id,
    method: 'delete'
  })
}

//修改图书状态
export function changeStoreyStatus(id,status){
  const data = {
    id:id,
    status:status
  };
  return request({
    url: '/userApplication/storey',
    method: 'put',
    data: data
  })
}
