import request from '@/utils/request';

export default {

  /**
   * 查询前两条的数据
   */
  getListBanner() {
    return request({
      url: '/educms/front/banner',
      method: 'get'
    });
  }
}
