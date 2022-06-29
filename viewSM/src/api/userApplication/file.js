import request from '@/utils/request'

// 查询附件实体对应信息列表
export function listFile(query) {
  return request({
    url: '/userApplication/file/list',
    method: 'get',
    params: query
  })
}

// 查询附件实体对应信息详细
export function getFile(fileId) {
  return request({
    url: '/userApplication/file/' + fileId,
    method: 'get'
  })
}
// 新增附件实体对应信息
export function addF(data) {
  return request({
    url: '/userApplication/file',
    method: 'post',
    data: data
  })
}
// 新增附件实体对应信息
export function addFile(data) {
  return request({
    url: '/userApplication/file/insertList',
    method: 'post',
    data: {"fileList":data}
  })
}
// 新增附件实体对应信息
export function addFileList(data) {
  return request({
    url: '/sysFile/upload',
    method: 'post',
    data: data
  })
}// 新增附件实体对应信息
export function addTypeFile(data,fileType) {
  return request({
    url: '/sysFile/uploadType?fileType='+fileType,
    method: 'post',
    data: data
  })
}

// 修改附件实体对应信息
export function updateFile(data) {
  return request({
    url: '/userApplication/file',
    method: 'put',
    data: data
  })
}

// 删除附件实体对应信息
export function delFile(fileId) {
  return request({
    url: '/userApplication/file/' + fileId,
    method: 'delete'
  })
}
// 删除附件实体对应信息
export function delOneFile(fileId) {
  return request({
    url: '/userApplication/file/del/' + fileId,
    method: 'post'
  })
}

//获取图片
export function getFiles(modelId,dbId) {
  const data = {
    modelId,
    dbId,
  };
  return request({
    url: '/userApplication/file/getFile',
    method: 'get',
    data: data
  })
}

//上传图片
export function setPicList(modelId,picList) {
  let status = 1;
  const data = {
    modelId,
    picList,
    status
  }
  return request({
    url: '/userApplication/auth/setPicList',
    method: 'post',
    data: data
  })
}

//分析字符串获取关键词
export function analysisStr(str) {
  return request({
    url: '/userApplication/file/analysisStr',
    method: 'post',
    data: {"str":str}
  })
}

export function appendSrcPiffect(src){
  return process.env.VUE_APP_BASE_API + src;
}

export function getFilePath(){
  return request({
    url: '/userApplication/file/getPath',
    method: 'get',
  })
}

export function downloadFile(url){
  return request({
    url: '/userApplication/file/downloadFile/'+url,
    method: 'get',
  })
}


export function appendSrcListPiffect(list) {
  if (undefined == list){
    return list;
  }
  for (let index = 0;index<list.length;index++){
    list[index].url = this.appendSrcPiffect(list[index].url);
  }
  return list;
}
