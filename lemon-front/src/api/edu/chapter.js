import request from '@/utils/request';

export default {

  /**
   * 根据课程 ID 查询章节和小节的数据
   * @param {*} courseId 课程ID
   */
  getAllChapterVideo(courseId) {
    return request({
      url: `/eduservice/chapter/${courseId}`,
      method: "get"
    });
  },

  /**
   * 添加章节
   * @param {*} chapter 章节信息
   */
  addChapter(chapter) {
    return request({
      url: '/eduservice/chapter/',
      method: "post",
      data: chapter
    });
  },

  /**
   * 根据 ID 获取章节信息
   * @param {*} chapterId 章节ID
   */
  getChapter(chapterId) {
    return request({
      url: `/eduservice/chapter/info/${chapterId}`,
      method: "get"
    });
  },

  /**
   * 修改章节信息
   * @param {*} chapter 修改章节的信息
   */
  updateChapter(chapter) {
    return request({
      url: `/eduservice/chapter/${chapter.id}`,
      method: "put",
      data: chapter
    });
  },

  /**
   * 根据 ID 删除章节
   * @param {*} id 章节ID
   */
  removeChapterById(id) {
    return request({
      url: `/eduservice/chapter/${id}`,
      method: "delete"
    });
  }

}
