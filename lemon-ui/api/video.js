import request from '@/utils/request';

export default {

  /**
   * 获取视频播放凭证
   * @param {*} vid 视频ID
   */
  getPlayAuth(vid) {
    return request({
      url: `/eduvod/video/${vid}`,
      method: 'get'
    })
  }
}
