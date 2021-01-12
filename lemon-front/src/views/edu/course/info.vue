<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="最终发布" />
    </el-steps>

    <el-form label-width="120px">
      <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title" placeholder="示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写" />
      </el-form-item>

      <!-- 所属分类：级联下拉列表 -->
      <el-form-item label="课程类别">
        <!-- 一级分类 -->
        <el-select v-model="courseInfo.subjectParentId" @change="subjectLevelOneChanged" placeholder="一级分类">
          <el-option v-for="subject in subjectNestedList" :key="subject.id" :label="subject.title"
            :value="subject.id" />
        </el-select>

        <!-- 二级分类 -->
        <el-select v-model="courseInfo.subjectId" placeholder="二级分类">
          <el-option v-for="subject in subSubjectList" :key="subject.value" :label="subject.title"
            :value="subject.id" />
        </el-select>
      </el-form-item>


      <el-form-item label="课程讲师">
        <el-select v-model="courseInfo.teacherId" placeholder="请选择">
          <el-option v-for="teacher in teacherList" :key="teacher.id" :label="teacher.name" :value="teacher.id" />
        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数" />
      </el-form-item>

      <!-- 课程简介 -->
      <el-form-item label="课程简介">
        <tinymce :height="300" v-model="courseInfo.description" />
      </el-form-item>

      <!-- 课程封面 -->
      <el-form-item label="课程封面">
        <el-upload :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload"
          :action="BASE_API+'/eduoss/file/upload?host=cover'" class="avatar-uploader">
          <img :src="courseInfo.cover">
        </el-upload>
      </el-form-item>

      <el-form-item label="课程价格">
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元" />
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">保存并下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import course from '@/api/edu/course';
  import teacher from '@/api/edu/teacher';
  import subject from '@/api/edu/subject';
  import Tinymce from '@/components/Tinymce';

  const defaultForm = {
    title: '',
    subjectParentId: '',
    subjectId: '',
    teacherId: '',
    lessonNum: 0,
    description: '',
    cover: '',
    price: 0,
    cover: process.env.OSS_PATH + '/cover/default.jpg',
  }

  export default {

    // 声明组件
    components: {
      Tinymce
    },

    data() {
      return {
        saveBtnDisabled: false, // 保存按钮是否禁用
        courseInfo: defaultForm,
        teacherList: [],
        subjectNestedList: [], //一级分类列表
        subSubjectList: [], //二级分类列表
        BASE_API: process.env.BASE_API // 接口API地址
      }
    },

    watch: {
      $route(to, from) {
        console.log('watch $route');
        this.init();
      }
    },

    created() {
      console.log('info created');
      this.init();
    },

    methods: {

      // 初始化
      init() {
        if (this.$route.params && this.$route.params.id) {
          const id = this.$route.params.id;
          //根据id获取课程基本信息
          this.fetchCourseInfoById(id);
        } else {
          this.courseInfo = {
            ...defaultForm
          }
          // 获取讲师列表
          this.initTeacherList();
          // 初始化分类列表
          this.initSubjectList();
        }
      },

      // 查询所有的讲师列表
      initTeacherList() {
        teacher.getListTeacher().then(response => {
          this.teacherList = response.data.items;
        });
      },

      // 查询所有的一级分类
      initSubjectList() {
        subject.getSubjectList().then(response => {
          this.subjectNestedList = response.data.list;
        })
      },

      next() {
        console.log('next');
        this.saveBtnDisabled = true;
        if (!this.courseInfo.id) {
          this.saveData();
        } else {
          this.updateData();
        }
      },

      // 保存
      saveData() {
        course.addCourseInfo(this.courseInfo)
          .then(response => {
            // 提示
            this.$message({
              type: 'success',
              message: '保存成功！'
            });
            return response;
          }).then(response => {
            this.$router.push({
              path: '/course/chapter/' + response.data.courseId
            });
          })
          .catch(error => {
            this.$message({
              type: 'error',
              message: response.error
            });
          });
      },

      // 修改数据
      updateData() {
        this.saveBtnDisabled = true;
        course.updateCourseInfo(this.courseInfo)
          .then(response => {
            this.$message({
              type: 'success',
              message: '修改成功!'
            });
            return response // 将响应结果传递给then
          }).then(response => {
            console.log(response.data.courseId);
            this.$router.push({
              path: '/course/chapter/' + response.data.courseId
            });
          })
          .catch(error => {
            this.$message({
              type: 'error',
              message: '保存失败'
            });
          });
      },

      subjectLevelOneChanged(value) {
        // 一级分类的id
        console.log(value)
        // 遍历所有分类，包含一级和二级
        for (let i = 0; i < this.subjectNestedList.length; i++) {
          if (this.subjectNestedList[i].id === value) {
            this.subSubjectList = this.subjectNestedList[i].children;
            // 清空二级中可能存在的值
            this.courseInfo.subjectId = '';
          }
        }
      },

      // 上传封面成功调用的方法
      handleAvatarSuccess(res, file) {
        console.log(res) // 上传响应
        console.log(URL.createObjectURL(file.raw)) // base64编码
        this.courseInfo.cover = res.data.url
      },

      // 上传之前调用的方法
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg'
        const isLt2M = file.size / 1024 / 1024 < 2
        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!')
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isJPG && isLt2M
      },

      fetchCourseInfoById(id) {
        course.getCourseInfoById(id)
          .then(response => {
            this.courseInfo = response.data.item;
            // 初始化分类列表
            subject.getSubjectList()
              .then(responseSubject => {
                this.subjectNestedList = responseSubject.data.list;
                // 把所有的一级分类数组进行遍历
                for (let i = 0; i < this.subjectNestedList.length; i++) {
                  // 比较当前courseInfo里面一级分类ID和所有的一级分类id
                  if (this.subjectNestedList[i].id === this.courseInfo.subjectParentId) {
                    // 获取一级分类的所有二级分类
                    this.subSubjectList = this.subjectNestedList[i].children;
                  }
                }
              })
            // 获取讲师列表
            this.initTeacherList();
          })
          .catch(error => {
            this.$message({
              type: 'error',
              message: error.message
            });
          });
      }
    }
  }

</script>

<style scoped>
  .tinymce-container {
    line-height: 29px;
  }

</style>
