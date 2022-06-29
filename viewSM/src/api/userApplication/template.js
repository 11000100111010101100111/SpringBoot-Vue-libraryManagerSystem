import request from '@/utils/request'

// 查询审批流程模板列表
export function listTemplate(query) {
  return request({
    url: '/userApplication/template/list',
    method: 'get',
    params: query
  })
}

// 查询审批流程模板详细
export function getTemplate(id) {
  return request({
    url: '/userApplication/template/' + id,
    method: 'get'
  })
}

// 新增审批流程模板
export function addTemplate(data) {
  return request({
    url: '/userApplication/template',
    method: 'post',
    data: data
  })
}

// 修改审批流程模板
export function updateTemplate(data) {
  return request({
    url: '/userApplication/template',
    method: 'put',
    data: data
  })
}

// 删除审批流程模板
export function delTemplate(id) {
  return request({
    url: '/userApplication/template/' + id,
    method: 'delete'
  })
}

export function importJSON(data) {
  return request({
    url:'/userApplication/template/import',
    method:"post",
    data:data,
  });

}
