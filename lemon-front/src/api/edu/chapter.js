import request from '@/utils/request';

export default {

  /**
   * 根据课程ID查询章节和小节的数据
   * @param {*} courseId 课程ID
   */
  getAllChapterVideo(courseId) {
    return request({
      url: `/eduservice/chapter/${courseId}`,
      method: "get"
    });
  }
}
