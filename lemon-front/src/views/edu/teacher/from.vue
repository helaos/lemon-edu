<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name" />
      </el-form-item>

      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right" :min="0" />
      </el-form-item>

      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>

      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career" />
      </el-form-item>

      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea" />
      </el-form-item>

      <!-- 讲师头像：TODO -->
      <el-form-item label="讲师头像">
        <!-- 头衔缩略图 -->
        <pan-thumb :image="String(teacher.avatar)" />

        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
        </el-button>

        <!--
          v-show：是否显示上传组件
          :key：类似于id，如果一个页面多个图片上传控件，可以做区分
          :url：后台上传的url地址
          @close：关闭上传组件
          @crop-upload-success：上传成功后的回调 -->
        <image-cropper v-show="imagecropperShow" :width="300" :height="300" :key="imagecropperKey"
          :url="BASE_API+'/eduoss/file/upload'" field="file" @close="close" @crop-upload-success="cropSuccess" />
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import teach from '@/api/edu/teacher.js';
  import ImageCropper from '@/components/ImageCropper';
  import PanThumb from '@/components/PanThumb';

  const defaultForm = {
    name: '',
    sort: 0,
    level: 1,
    career: '',
    intro: '',
    avatar: process.env.OSS_PATH + '/avatar/default.png'
  }

  export default {

    components: {
      ImageCropper,
      PanThumb
    },

    data() {
      return {
        teacher: defaultForm,
        imagecropperShow: false, // 上传弹框组件是否显示
        imagecropperKey: 0, // 唯一标识
        BASE_API: process.env.BASE_API, // 获取dev.env.js里面的地址
        saveBtnDisabled: false // 保存按钮是否禁用
      }
    },
    created() {
      console.log('created');
      this.init();

    },
    watch: { // 监听
      $route(to, from) { // 路由的变化方式
        this.init();
      }
    },
    methods: {
      // 初始化
      init() {
        if (this.$route.params && this.$route.params.id) {
          const id = this.$route.params.id;
          this.getInfo(id);
        } else {
          // 使用对象拓展运算符，拷贝对象，而不是引用，
          // 否则新增一条记录后，defaultForm就变成了之前新增的teacher的值
          this.teacher = {
            avatar: process.env.OSS_PATH + '/avatar/default.png'
          }
        }
      },

      // 讲师修改的方法
      saveOrUpdate() {
        // 打开按钮的禁用
        this.saveBtnDisabled = true;

        if (!this.teacher.id) {
          // 保存修改
          this.saveTeacher();
        } else {
          this.updateTeacher()
        }
      },

      // 讲师添加的方法
      saveTeacher() {
        teach.addTeacher(this.teacher)
          .then(response => { // 添加成功
            // 提示信息
            return this.$message({
              type: 'success',
              message: '保存成功!'
            });
          }).then(response => {
            // 回到列表页面
            this.$router.push({
              path: '/teacher'
            });
          }).catch(error => {
            this.$message({
              type: 'error',
              message: '保存失败!'
            });
          });
      },

      // 根据讲师id查询的方法
      getInfo(id) {
        teach.getTeacherInfo(id)
          .then(response => {
            this.teacher = response.data.item;
          })
          .catch(error => {
            this.$message({
              type: 'error',
              message: '获取数据失败'
            })
          });
      },

      // 修改讲师的方法
      updateTeacher() {
        // 打开按钮的禁用
        this.saveBtnDisabled = true;

        teach.updateTeacherInfo(this.teacher)
          .then(response => {
            return this.$message({
              type: 'success',
              message: '修改成功!'
            });
          })
          .then(response => {
            this.$router.push({
              path: '/teacher'
            })
          })
          .catch(error => {
            this.$message({
              type: 'error',
              message: '保存失败!'
            });
          });
      },

      // 关闭上传弹框的方法
      close() {
        this.imagecropperShow = false;
        // 上传组件初始化
        this.imagecropperKey = this.imagecropperKey + 1;
      },

      // 上传成功的方法
      cropSuccess(data) {
        // 关闭弹框
        this.imagecropperShow = false;
        // 上传之后接口返回的图片地址
        this.teacher.avatar = data.url;
        // 上传组件初始化
        this.imagecropperKey = this.imagecropperKey + 1;
      }
    }
  }

</script>
