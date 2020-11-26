import request from '@/utils/request';

export default {

  /**
   * 保存小节信息
   * @param {*} videoInfo 小节信息
   */
  saveVideoInfo(videoInfo) {
    return request({
      url: '/eduservice/video',
      method: "post",
      data: videoInfo
    });
  },

  /**
   * 根据ID查询小节信息
   * @param {*} id 小节ID
   */
  getVideoInfoById(id) {
    return request({
      url: `/eduservice/video/${id}`,
      method: "get"
    });
  },

  /**
   * 根据传入的信息修改小节
   * @param {*} videoInfo 修改信息
   */
  updateVideoInfoById(videoInfo) {
    return request({
      url: `/eduservice/video/${videoInfo.id}`,
      method: "put",
      data: videoInfo
    });
  },

  /**
   * 根据ID删除小节信息
   * @param {*} id 小节ID
   */
  removeById(id) {
    return request({
      url: `/eduservice/video/${id}`,
      method: "delete"
    });
  },

  /**
   * 删除阿里云视频
   * @param {*} id 视频ID
   */
  deleteAliyunVideo(id) {
    return request({
      url: `/eduvod/video/${id}`,
      method: "delete"
    });
  }

}
