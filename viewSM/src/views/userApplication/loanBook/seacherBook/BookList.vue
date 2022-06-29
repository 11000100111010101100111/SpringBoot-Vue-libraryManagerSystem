<template>
    <div>
<!--      <el-table v-loading="loading" :data="list" :show-header="false">-->
<!--        <el-table-column align="left" width="150">-->
<!--          <template slot-scope="scope">-->
<!--            <Picture :src-list="scope.row.srcList" :my-stype="picStyle"/>-->
<!--          </template>-->
<!--        </el-table-column>-->
<!--        <el-table-column align="left">-->
<!--        </el-table-column>-->
<!--      </el-table>-->
      <el-card
        v-for="(row,index) in list"
        :body-style="{ padding: '0px',display:'inline-block' }"
      >
        <div>
          <div style="display: inline-block;">
            <Picture :src-list="row.srcList" :my-stype="picStyle"/>
            <div style="padding: 14px;">
              <span>好吃的汉堡</span>
              <div class="bottom clearfix">
                <time class="time">222222222</time>
                <el-button type="text" class="button">操作按钮</el-button>
              </div>
            </div>
          </div>
          <div style="display: inline-block;">

          </div>
        </div>
      </el-card>

      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
</template>

<script>
    import BookDetial from "./bookDetial";
    import Template from "../../template/index";
    import Picture from "../../base/Picture";
    import {keyWords} from '@/api/userApplication/book'
    export default {
      components: {Picture, Template, BookDetial},
      props:{
        key:String,
        default:''
      },
        name: "BookList",
      data(){
        return{
          queryParams:{
            pageNum:0,
            pageSize:1,
          },
          picStyle:'width: 150px; height: 150px;box-shadow: 0 0 5px #666',
          list:[
            {
            srcList:[
              '/dev-api/profile/avatar/2022/system/default_avatar_21.png',
              '/dev-api/profile/avatar/2022/system/default_avatar_98.png',
              '/dev-api/profile/avatar/2022/system/default_avatar_8.png'
            ],
            book:{
              id: 1,
              name: '888',
              dept:'fsdfsdfsdfsdfsdfsdf',
              code: '111',
              authName:"肖家海",
              type: "0",
              leavel: 1,
              claz: 1,
              author: 1,
              mechanism: 1,
              pressName:'',
              publicationDate: 1,
              version: 1,
              circulation: 1,
              isbn: 11111,
              series: 11111,
              keyWords: 1111111,
              price: 1111,
              mark: 'dndofdnofdsfnosadifsaofnsadsfsdfsdfsdf',
              other: 111111,
              status: 0,
              createPersonal: 1,
              createTime: '2024-10-12 12:12:12',
              updateTime: '2024-10-12 12:12:12',
              updatePersonal: 1
            }
          },
            {
              srcList:[
                '/dev-api/profile/avatar/2022/system/default_avatar_21.png',
                '/dev-api/profile/avatar/2022/system/default_avatar_98.png',
                '/dev-api/profile/avatar/2022/system/default_avatar_8.png'
              ],
              book:{
                id: 1,
                name: '888',
                code: '111',
                type: "0",
                leavel: 1,
                claz: 1,
                authName:"肖家海",
                author: 1,
                mechanism: 1,
                pressName:'',
                publicationDate: 1,
                version: 1,
                circulation: 1,
                isbn: 11111,
                series: 11111,
                keyWords: 1111111,
                price: 1111,
                mark: 1111,
                other: 111111,
                status: 0,
                createPersonal: 1,
                createTime: '2024-10-12 12:12:12',
                updateTime: '2024-10-12 12:12:12',
                updatePersonal: 1
              }
            },
            {
              srcList:[
                '/dev-api/profile/avatar/2022/system/default_avatar_21.png',
                '/dev-api/profile/avatar/2022/system/default_avatar_98.png',
                '/dev-api/profile/avatar/2022/system/default_avatar_8.png'
              ],
              book:{
                id: 1,
                name: '888',
                code: '111',
                type: "0",
                leavel: 1,
                authName:"肖家海",
                claz: 1,
                author: 1,
                mechanism: 1,
                pressName:'',
                publicationDate: 1,
                version: 1,
                circulation: 1,
                isbn: 11111,
                series: 11111,
                keyWords: 1111111,
                price: 1111,
                mark: 1111,
                other: 111111,
                status: 0,
                createPersonal: 1,
                createTime: '2024-10-12 12:12:12',
                updateTime: '2024-10-12 12:12:12',
                updatePersonal: 1
              }
            }
            ],
          loading:false,
          total:3,
        }
      },
      methods:{
        getList(){
          keyWords(this.key).then(res=>{
            this.list = res.rows;
          });
        },
      }

    }
</script>

<style scoped>

</style>
