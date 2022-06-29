<template>
    <div style="min-height: 70vh;max-height: 70vh">
      <el-row :gutter="20" type="flex">
        <el-col style="width: 20%" >
          <select-tree autoStyle="max-height: 65vh;overflow-y: auto;" v-on:selectNode="selectBookType" tree-type="bookType"/>
        </el-col>
        <el-col style="width: 80%;">
          <el-table
            style="max-height: 65vh;min-height: 65vh;overflow-y: auto;"
            v-loading="bookType.loading"
            :data="bookType.bookList">
            <el-table-column type="selection" width="55" align="center" v-if="false"/>
            <el-table-column label="图书名称" align="center" prop="name" fixed="left" width="250px"/>
            <el-table-column label="图书编码" align="center" prop="code" />
            <el-table-column label="图书类型" align="center" prop="type"  />
            <el-table-column label="保存公开级别" align="center" prop="leavel"  width="100px">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.library_secrecy_class" :value="scope.row.leavel"/>
              </template>
            </el-table-column>
            <el-table-column label="图书类别" align="center" prop="claz" width="200px"/>
            <el-table-column label="作者" align="center" prop="author" width="100px"/>
            <el-table-column label="出版社" align="center" prop="mechanism" width="200px"/>
            <el-table-column label="出版日期" align="center" prop="publicationDate" width="180">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.publicationDate, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="图书版本号" align="center" prop="version"/>
            <el-table-column label="发行量" align="center" prop="circulation"/>
            <el-table-column label="ISBN" align="center" prop="isbn" width="250px"/>
            <el-table-column label="系列丛书" align="center" prop="series" width="250px"/>
            <el-table-column label="关键字" align="center" prop="keyWords" v-if="false"/>
            <el-table-column label="单价" align="center" prop="price"/>
            <el-table-column label="说明" align="center" prop="mark" v-if="false"/>
            <el-table-column label="操作" align="center" fixed="right" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  icon="el-icon-read"
                  type="primary" round
                  @click="handleBookSelectionChange(scope.row)"
                  v-hasPermi="['userApplication:book:read']"
                  style="padding: 5px"
                >立即选择</el-button>
              </template>
            </el-table-column>
          </el-table>
          <pagination
            v-show="bookType.total>0"
            :total="bookType.total"
            :page.sync="bookType.queryParams.pageNum"
            :limit.sync="bookType.queryParams.pageSize"
            @pagination="getBookList"
          />
        </el-col>
      </el-row>
    </div>
</template>

<script>
  import { listBook } from "@/api/userApplication/book";
  import SelectTree from "../base/libraryElement";
  import { bookclasstreeselect } from "@/api/main/bookClass";

    export default {
      components: {SelectTree},
      dicts: ['base_status','library_secrecy_class'],
        name: "ChioseBook",
      comments:[SelectTree],
      data(){
          return{
            //图书选择
            bookType:{
              drawer:false,
              operatorIndex:undefined,
              title:'请选择待采购的图书（单项选择）',
              queryBookTypeId:undefined,
              //图书类别名称
              bookClassName:'',
              // defaultProps: {
              //   children: "children",
              //   label: "label"
              // },
              total:0,
              queryParams:{
                claz:null,
                pageNum:1,
                pageSize:10,
              },
              // 图书类别树选项
              // bookClassOptions: undefined,
            },
          }
      },
      methods:{
        // 图书类别节点单击事件
        selectBookType(selectData) {
          this.bookType.bookClassName = selectData.curData.label;
          this.bookType.queryBookTypeId = selectData.curData.id;
          // this.form.claz = data.id;
          this.bookType.queryParams.claz=this.bookType.queryBookTypeId;
          this.getBookList();
        },

        /** 查询图书实体信息列表 */
        getBookList() {
          this.bookType.loading = true;
          listBook(this.bookType.queryParams).then(response => {
            this.bookType.bookList = response.rows;
            this.bookType.total = response.total;
            this.bookType.loading = false;
          });
        },
        // 选中图书数据
        handleBookSelectionChange(selection) {
          return this.$emit("selectedBook",selection);
        },
      }
    }
</script>

<style scoped>

</style>
