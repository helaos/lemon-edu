<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <!-- 标题 -->
      <el-form-item>
        <el-input v-model="searchObj.title" placeholder="轮播图标题" />
      </el-form-item>

      <el-form-item>
        <el-input v-model="searchObj.linkUrl" placeholder="路由路径" />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
      <div style="margin:25px 0px;">
        <el-button type="primary" @click="$router.push({ path: '/cms/add'})">添加新轮播图</el-button>
      </div>
    </el-form>

    <!-- 列表 -->
    <el-table v-loading="listLoading" :data="bannerList" element-loading-text="数据加载中" border fit highlight-current-row
      row-class-name="myClassList">
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">{{ (page - 1) * limit + scope.$index + 1 }}</template>
      </el-table-column>

      <el-table-column label="轮播图信息" width="200" align="center">
        <template slot-scope="scope">
          <div class="pic">
            <img :src="scope.row.imageUrl" alt="scope.row.title" width="150px" height="70px" />
          </div>
        </template>
      </el-table-column>

      <el-table-column label="标题" width="200" align="center">
        <template slot-scope="scope">{{scope.row.title}}</template>
      </el-table-column>

      <el-table-column label="路由路径" width="150" align="center">
        <template slot-scope="scope">{{scope.row.linkUrl}}</template>
      </el-table-column>

      <el-table-column label="创建时间" align="center">
        <template slot-scope="scope">{{ scope.row.createTime.substr(0, 10) }}</template>
      </el-table-column>
      <el-table-column label="发布时间" align="center">
        <template slot-scope="scope">{{ scope.row.updateTime.substr(0, 10) }}</template>
      </el-table-column>

      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <router-link :to="'/cms/update/'+scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit">修改轮播图信息</el-button>
          </router-link>
          <el-button type="text" size="mini" icon="el-icon-delete" @click="removeBanner(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination :current-page="page" :page-size="limit" :total="total" style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper" @current-change="fetchData" />
  </div>
</template>

<script>
  import banner from "@/api/cms/banner";
  export default {
    data() {
      return {
        listLoading: false,
        page: 1,
        limit: 4,
        total: 0,
        searchObj: {},
        bannerList: [],
      };
    },
    created() {
      this.fetchData();
    },
    methods: {

      removeBanner(id) {
        this.$confirm("此操作将删除轮播图，是否继续?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            banner.deleteBannerInfo(id)
              .then(response => {
                this.$message({
                  type: "success",
                  message: "删除轮播图成功",
                });
                this.fetchData(this.page);
              })
          })
          .catch(error => {
            if (error === 'cancel') {
              this.$message({
                type: 'info',
                message: '已取消删除'
              });
            }
          });
      },

      resetData() {
        this.searchObj = {};
        this.fetchData();
      },

      fetchData(page = 1) {
        this.page = page;
        this.listLoading = true;
        banner.getBannerList(this.page, this.limit, this.searchObj)
          .then((response) => {
            this.listLoading = false;
            this.total = response.data.total;
            this.bannerList = response.data.rows;
          })
          .catch((error) => {
            this.listLoading = false;
            console.log(error);
          });
      },
    },
  }

</script>

<style scoped>
  .myClassList .info {
    width: 450px;
    overflow: hidden;
  }

  .myClassList .info .pic {
    width: 150px;
    height: 90px;
    overflow: hidden;
    float: left;
  }

  .myClassList .info .pic a {
    display: block;
    width: 100%;
    height: 100%;
    margin: 0;
    padding: 0;
  }

  .myClassList .info .pic img {
    display: block;
    width: 100%;
  }

  .myClassList td .info .title {
    width: 280px;
    float: right;
    height: 90px;
  }

  .myClassList td .info .title a {
    display: block;
    height: 48px;
    line-height: 24px;
    overflow: hidden;
    color: #00baf2;
    margin-bottom: 12px;
  }

  .myClassList td .info .title p {
    line-height: 20px;
    margin-top: 5px;
    color: #818181;
  }

</style>
