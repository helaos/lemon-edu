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
  }
  
}
