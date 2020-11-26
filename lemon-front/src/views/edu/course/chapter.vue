<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="最终发布" />
    </el-steps>

    <el-button type="text" @click="dialogChapterFormVisible = true">添加章节</el-button>
    <!-- 章节 -->
    <ul class="chanpterList">
      <li v-for="chapter in chapterNestedList" :key="chapter.id">
        <p>
          {{ chapter.title }}
          <span class="acts">
            <el-button type="text" @click="dialogVideoFormVisible = true; chapterId = chapter.id">添加小节</el-button>
            <el-button type="text" @click="editChapter(chapter.id)">编辑</el-button>
            <el-button type="text" @click="removeChapter(chapter.id)">删除</el-button>
          </span>
        </p>

        <!-- 视频 -->
        <ul class="chanpterList videoList">
          <li v-for="video in chapter.children" :key="video.id">
            <p>{{ video.title }}
              <span class="acts">
                <el-button type="text" @click="editVideo(video.id)">编辑</el-button>
                <el-button type="text" @click="removeVideo(video.id)">删除</el-button>
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>

    <el-form style="text-align: center;">
      <el-form-item>
        <el-button @click="previous">上一步</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
      </el-form-item>
    </el-form>

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节" @close="helpSave">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title" />
        </el-form-item>

        <el-form-item label="章节排序">
          <el-input-number v-model="chapter.sort" :min="0" controls-position="right" />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加小节" @close="helpVideoSave">
      <el-form :model="video" label-width="120px">
        <el-form-item label="小节标题">
          <el-input v-model="video.title" />
        </el-form-item>

        <el-form-item label="小节排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right" />
        </el-form-item>

        <el-form-item label="是否免费">
          <el-radio-group v-model="video.isFree">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="上传视频">
          <el-upload :on-success="handleVodUploadSuccess" :on-remove="handleVodRemove" :before-remove="beforeVodRemove"
            :on-exceed="handleUploadExceed" :file-list="fileList" :action="BASE_API+'/eduvod/video/upload'" :limit="1"
            class="upload-demo">
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">最大支持1G，<br>
                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
              <i class="el-icon-question" />
            </el-tooltip>
          </el-upload>

        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="saveOrUpdateVideo">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import chapter from '@/api/edu/chapter';
  import video from '@/api/edu/video';

  export default {

    data() {
      return {
        saveBtnDisabled: false, // 保存按钮是否禁用
        courseId: '', // 所属课程
        chapterNestedList: [], // 章节嵌套课时列表
        chapter: {
          title: '',
          sort: 0
        }, // 封装章节属性
        dialogChapterFormVisible: false, // 章节弹框
        saveVideoBtnDisabled: false, // 课时按钮是否禁用
        dialogVideoFormVisible: false, // 是否显示课时表单
        chapterId: '', // 课时所在的章节id
        video: { // 课时对象
          title: '',
          sort: 0,
          isFree: false,
          videoSourceId: '',
          videoOriginalName: ''
        },
        fileList: [], //上传文件列表
        BASE_API: process.env.BASE_API // 接口API地址
      }
    },

    created() {
      console.log('chapter created');
      this.init();
    },

    methods: {

      //==========================章节操作==========================//
      init() {
        if (this.$route.params && this.$route.params.id) {
          this.courseId = this.$route.params.id;
          // 根据id获取课程基本信息
          this.getChapterVideo();
        }
      },

      // 刷新列表
      getChapterVideo() {
        chapter.getAllChapterVideo(this.courseId)
          .then(response => {
            this.chapterNestedList = response.data.items;
          })
      },

      previous() {
        // 返回到第一步
        this.$router.push({
          path: '/course/info/' + this.courseId
        })
      },

      next() {
        // 跳转到第二步
        this.$router.push({
          path: '/course/publish/' + this.courseId
        })
      },

      saveOrUpdate() {
        this.saveBtnDisabled = true;
        if (!this.chapter.id) {
          this.saveData();
        } else {
          this.updateDate();
        }
      },

      // 保存数据
      saveData() {
        // 设置课程id
        this.chapter.courseId = this.courseId;
        chapter.addChapter(this.chapter)
          .then(response => {
            this.$message({
              type: 'success',
              message: '保存成功！'
            });
            this.helpSave();
          });
      },

      // 修改数据
      updateDate() {
        chapter.updateChapter(this.chapter)
          .then(response => {
            this.$message({
              type: 'success',
              message: '修改成功'
            });
            this.helpSave();
          })
          .catch(error => {
            this.$message({
              type: 'error',
              message: error.message
            });
          })
      },

      helpSave() {
        this.dialogChapterFormVisible = false; // 如果保存成功则关闭对话框
        this.getChapterVideo(); // 刷新列表
        this.chapter.title = ''; // 重置章节标题
        this.chapter.sort = 0; // 重置章节标题
        this.saveBtnDisabled = false;
      },

      // 编辑章节的响应
      editChapter(chapterId) {
        this.dialogChapterFormVisible = true;
        chapter.getChapter(chapterId)
          .then(response => {
            this.chapter = response.data.item;
          });
      },

      // 删除章节的响应
      removeChapter(chapterId) {
        this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
          .then(response => {
            return chapter.removeChapterById(chapterId);
          })
          .then(response => {
            this.getChapterVideo(); // 刷新列表
            this.$message({
              type: 'success',
              message: '删除成功！'
            });
          })
          .catch(error => { // 失败
            if (error === 'cancel') {
              this.$message({
                type: 'info',
                message: '已取消删除！'
              });
            }
          });
      },

      //==========================小节操作==========================//

      saveOrUpdateVideo() {
        // 关闭弹框
        this.saveVideoBtnDisabled = true;
        if (!this.video.id) {
          this.saveVideoData();
        } else {
          this.updateVideoData();
        }
      },

      saveVideoData() {
        this.video.courseId = this.courseId;
        this.video.chapterId = this.chapterId;
        video.saveVideoInfo(this.video)
          .then(response => {
            this.$message({
              type: 'success',
              message: '保存成功！'
            });
            this.helpVideoSave();
          })
      },

      updateVideoData() {
        video.updateVideoInfoById(this.video)
          .then(response => {
            this.$message({
              type: 'success',
              message: '修改成功'
            });
            this.helpVideoSave();
          })
      },

      helpVideoSave() {
        this.dialogVideoFormVisible = false; // 如果保存成功则关闭对话框
        this.getChapterVideo(); // 刷新列表
        this.video.id = ''; // 重置小节ID
        this.video.title = ''; // 重置章节标题
        this.video.sort = 0; // 重置章节标题
        this.video.videoSourceId = ''; // 重置视频资源id
        this.video.isFree = false;
        this.saveVideoBtnDisabled = false;
      },

      editVideo(videoId) {
        this.dialogVideoFormVisible = true;
        video.getVideoInfoById(videoId)
          .then(response => {
            this.video = response.data.item;
            this.fileList = [{
              'name': this.video.videoOriginalName
            }]
          });
      },

      removeVideo(videoId) {
        this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
          .then(response => {
            return video.removeById(videoId);
          })
          .then(response => {
            this.getChapterVideo(); // 刷新列表
            this.$message({
              type: 'success',
              message: '删除成功！'
            });
          })
          .catch(error => {
            if (error === 'cancel') { // 失败
              this.$message({
                type: 'info',
                message: '已取消删除'
              });
            }
          });
      },

      // 上传视频成功调用的方法
      handleVodUploadSuccess(response, file, fileList) {
        this.video.videoSourceId = response.data.videoId;
        this.video.videoOriginalName = file.name;
      },

      //视图上传多于一个视频
      handleUploadExceed(files, fileList) {
        this.$message.warning('想要重新上传视频，请先删除已上传的视频');
      },

      beforeVodRemove(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`);
      },

      handleVodRemove(file, fileList) {
        video.deleteAliyunVideo(this.video.videoSourceId)
          .then(response => {
            this.$message({
              type: 'success',
              message: response.message
            });
            // 文件列表清空
            this.fileList = [];
            this.video.videoSourceId = '';
            this.video.videoOriginalName = '';
          });
      }

    }
  }

</script>

<style scoped>
  .chanpterList {
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
  }

  .chanpterList li {
    position: relative;
  }

  .chanpterList p {
    float: left;
    font-size: 20px;
    margin: 10px 0;
    padding: 10px;
    height: 70px;
    line-height: 50px;
    width: 100%;
    border: 1px solid #DDD;
  }

  .chanpterList .acts {
    float: right;
    position: relative;
    z-index: 1;
    font-size: 14px;
  }

  .videoList {
    padding-left: 50px;
  }

  .videoList p {
    float: left;
    font-size: 14px;
    margin: 10px 0;
    padding: 10px;
    height: 50px;
    line-height: 30px;
    width: 100%;
    border: 1px dotted #DDD;
  }

</style>
