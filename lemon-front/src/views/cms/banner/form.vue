<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="轮播图名称">
        <el-input v-model="banner.title" />
      </el-form-item>
      <el-form-item label="跳转路由">
        <el-input v-model="banner.linkUrl" />
      </el-form-item>
      <el-form-item label="排序优先级">
        <el-input-number v-model="banner.sort" controls-position="right" :min="0" :max="1" />
      </el-form-item>
      <el-form-item label="课程封面">
        <!-- 上传轮播图 -->
        <el-upload label="轮播图" class="upload" :on-success="handleUploadSuccess" :before-upload="beforeImageUpload"
          :action="BASE_API+'/eduoss/file/upload?host=cover'" :show-file-list="false">
          <img :src="banner.imageUrl" width="300px" height="150px" />
        </el-upload>
      </el-form-item>
    </el-form>

    <div style="margin:25px 20px;">
      <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate()" style="margin:25px 25px">保存
      </el-button>
    </div>
  </div>
</template>

<script>
  import banner from "@/api/cms/banner";

  const defaultBanner = {
    title: '',
    linkUrl: '',
    sort: 0,
    imageUrl: process.env.OSS_PATH + '/banner/default.jpg'
  };

  export default {
    data() {
      return {
        banner: defaultBanner,
        imagecropperShow: false,
        imagecropperKey: 0, //上传组件key值
        BASE_API: process.env.BASE_API,
        saveBtnDisabled: false, //保证按钮不能被重复点击
      };
    },
    // 第一次跳转才会执行
    created() {
      this.init();
    },
    // 监听
    watch: {
      // 路由发生变化，方法就会执行
      $route(to, from) {
        this.init();
      },
    },
    methods: {
      // 判断路由参数方法抽取为初始化方法
      init() {
        if (this.$route.params && this.$route.params.id) {
          const id = this.$route.params.id;
          this.getInfo(id);
        } else {
          this.banner = {
            sort: 0,
            imageUrl: process.env.OSS_PATH + '/banner/default.jpg'
          };
        }
      },
      //保存或更新教师信息
      saveOrUpdate() {
        this.saveBtnDisabled = true;
        //判断是修改还是添加
        if (!this.banner.id) {
          //添加
          this.saveBanner();
        } else {
          //修改
          this.updateBanner();
        }
      },
      //修改轮播图
      updateBanner() {
        banner.updateBannerInfo(this.banner).then((response) => {
          this.saveBtnDisabled = false;
          //提示信息
          this.$message({
            type: "success",
            message: "修改成功!",
          });
          //回到列表页面
          this.$router.push("/cms/list");
        });
        this.banner = defaultBanner;
      },
      // 保存轮播图信息
      saveBanner() {
        banner.addBannerInfo(this.banner)
          .then(response => {
            //提示信息
            return this.$message({
              type: "success",
              message: "添加成功!",
            });
          })
          .then(response => {
            //回到列表页面
            this.$router.push("/cms/list");
          });
      },
      // 根据id查询轮播图信息
      getInfo(id) {
        banner.getBannerInfo(id).then((response) => {
          this.banner = response.data.item;
        });
      },

      //上传成功
      handleUploadSuccess(res, file) {
        this.$message({
          type: "success",
          message: res.message,
        });
        this.banner.imageUrl = res.data.url;
      },

      beforeImageUpload(file) {
        const isLt2M = file.size / 1024 / 1024 < 5;
        if (!isLt2M) {
          this.$message.error("上传图片大小不能超过 5MB!");
        }
        return isLt2M;
      },
    },
  };

</script>
