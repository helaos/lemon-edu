import request from '@/utils/request';

export default {

  /**
   * 查询课程和名师
   */
  getIndexData() {
    return request({
      url: '/eduservice/index',
      method: 'get'
    });
  }
}