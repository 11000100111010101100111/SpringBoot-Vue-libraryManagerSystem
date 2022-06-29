import Vue from 'vue'

import Cookies from 'js-cookie'
//流程
// import FlowDesignTCD from 'flow-design-tcd'
// import "flow-design-tcd/lib/flow-design-tcd.css"
// Vue.use(FlowDesignTCD)
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/components/ef/index.css'
Vue.use(ElementUI, {size: 'small'})


import '@/theme/al-font/iconfont.css' //引用导入的阿里图标库
import Element from 'element-ui' //element-ui
import './assets/styles/element-variables.scss'

import '@/assets/styles/index.scss' // global css
import '@/assets/styles/view.scss' // view css
import App from './App'
import store from './store'
import router from './router'
import directive from './directive' // directive
import plugins from './plugins' // plugins
import { download } from '@/utils/request'

import './assets/icons' // icon
import './permission' // permission control
import { getDicts } from "@/api/system/dict/data";
import { getConfigKey } from "@/api/system/config";
import { parseTime, resetForm, addDateRange, selectDictLabel, selectDictLabels, handleTree } from "@/utils/view";
// 分页组件
import Pagination from "@/components/Pagination";
// 自定义表格工具组件
import RightToolbar from "@/components/RightToolbar"
// 富文本组件
import Editor from "@/components/Editor"
// 文件上传组件
import FileUpload from "@/components/FileUpload"
// 图片上传组件
import ImageUpload from "@/components/ImageUpload"
// 图片预览组件
import ImagePreview from "@/components/ImagePreview"
// 字典标签组件
import DictTag from '@/components/DictTag'
// 头部标签组件
import VueMeta from 'vue-meta'
// 字典数据组件
import DictData from '@/components/DictData'
// 地址选择组件
import VDistpicker from 'v-distpicker'
//图片懒加载组件库
import vView from 'vue-view-lazy'

//粘贴功能
import vueclipboard from 'vue-clipboard2'


// import netty from 'https://code.jquery.com/jquery-3.2.1.min.js'
// import nettyIo from'./js/socket.io/socket.io.js'





// 全局方法挂载
Vue.prototype.getDicts = getDicts
Vue.prototype.getConfigKey = getConfigKey
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.selectDictLabels = selectDictLabels
Vue.prototype.download = download
Vue.prototype.handleTree = handleTree

// 全局组件挂载
Vue.component('DictTag', DictTag)
Vue.component('Pagination', Pagination)
Vue.component('RightToolbar', RightToolbar)
Vue.component('Editor', Editor)
Vue.component('FileUpload', FileUpload)
Vue.component('ImageUpload', ImageUpload)
Vue.component('ImagePreview', ImagePreview)
Vue.component('v-distpicker',VDistpicker)

Vue.use(directive)
Vue.use(plugins)
Vue.use(VueMeta)
Vue.use(vueclipboard)
DictData.install()

Vue.use(vView,{
  error:'../../dev-api/profile/avatar/2022/02/16/5f3e109f-356e-402a-8afe-29b904065e1a.jpeg',
  loading:'../../dev-api/profile/avatar/2022/02/16/5f3e109f-356e-402a-8afe-29b904065e1a.jpeg',
});
/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get('size') || 'medium' // set element-ui default size
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
