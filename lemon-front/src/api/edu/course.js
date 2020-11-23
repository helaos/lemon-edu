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
   * @param {*}} id 课程ID
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
  }

}
