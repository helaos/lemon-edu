import request from '@/utils/request';

export default {

  /**
   * 获取课程分类的信息
   */
  getSubjectList () {
    return request({
      url: '/eduservice/subject',
      method: 'get'
    });
  }
}