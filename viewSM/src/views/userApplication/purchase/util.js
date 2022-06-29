
const node_type1 ={type:'primary',size:'large',icon:'el-icon-s-promotion',color: '#0bbd87'};
const node_type2 ={type:'primary',size:'large',icon:'el-icon-check',color: '#0bbd87'};
const node_type0 ={type:'primary',size:'large',icon:'el-icon-video-pause',color: '#a2a2a2'};
const node_type_1 ={type:'primary',size:'large',icon:'el-icon-remove',color: '#ff333d'};
const node_type_2 ={type:'primary',size:'large',icon:'el-icon-warning',color: '#ff7046'};
const node_type_default ={};

function setNode(obj,aim){
  aim.type=obj.type;
  aim.size = obj.size;
  aim.icon = obj.icon;
  aim.color = obj.color;
  return aim;
}

export function createType(data){
  for (let i=0;i<data.length;i++){
    data[i].handelPersonalEntity.avatar = process.env.VUE_APP_BASE_API + data[i].handelPersonalEntity.avatar;
    switch (data[i].handleResult) {
      case '1':data[i] = this.setNode(node_type1,data[i]);break;
      case '2':data[i] = this.setNode(node_type2,data[i]);break;
      case '0':data[i] = this.setNode(node_type0,data[i]);break;
      case '-1':data[i] = this.setNode(node_type_1,data[i]);break;
      case '-2':data[i] = this.setNode(node_type_2,data[i]);break;
      default:data[i] = this.setNode(node_type_default,data[i]);
    }
  }

  return data;
}
