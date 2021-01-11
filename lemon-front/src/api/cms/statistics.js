import request from '@/utils/request';

export default {

  /**
   * 生成某一课程数据
   * @param {*} day 天
   */
  createStatistics(day) {
    return request({
      url: `/statistics/daily/${day}`,
      method: 'post'
    });
  },

  /**
   * 生成图标数据
   * @param {*} searchObj 条件对象
   */
  showChart(searchObj) {
    return request({
      url: `/statistics/daily/chart/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
      method: 'get'
    });
  }
}
