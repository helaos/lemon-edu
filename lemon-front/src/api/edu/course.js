import request from '@/utils/request';

export default {

  /**
   * 添加课程信息
   * @param {*} courseInfo 页面的信息封装
   */
  addCourseInfo(courseInfo) {
    return request({
      url: '/eduservice/course',
      method: 'post',
      data: courseInfo
    });
  },

  /**
   * 根据课程id查询课程的基本信息
   * @param {*} id 课程ID
   */
  getCourseInfoById(id) {
    return request({
      url: `/eduservice/course/${id}`,
      method: 'get'
    });
  },

  /**
   * 修改课程信息
   * @param {*} courseInfo 课程信息
   */
  updateCourseInfo(courseInfo) {
    return request({
      url: `/eduservice/course/${courseInfo.id}`,
      method: 'put',
      data: courseInfo
    });
  },

  /**
   * 根据课程ID查出确认信息
   * @param {*} id 课程ID
   */
  getPublishCourseInfo(id) {
    return request({
      url: `/eduservice/course/publish/${id}`,
      method: 'get'
    });
  },

  /**
   * 最终发布方法
   * @param {*} id 课程ID
   */
  publishCourse (id) {
    return request({
      url: `/eduservice/course/status/${id}`,
      method: 'put'
    });
  }

}
