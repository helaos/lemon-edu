import request from '@/utils/request';

export default {

  /**
   * 待条件的分页查询
   * @param {*} page 当前页
   * @param {*} limit 每页的记录数
   * @param {*} searchObj 查询条件对象
   */
  getBannerList(page, limit, searchObj) {
    return request({
      url: `/educms/admin/banner/${page}/${limit}`,
      method: 'post',
      data: searchObj
    });
  },

  /**
   * 根据iD删除轮播图
   * @param {*} id BannerID
   */
  deleteBannerInfo(id) {
    return request({
      url: `/educms/admin/banner/${id}`,
      method: 'delete'
    });
  },

  /**
   * 修改信息
   * @param {*} bannerObj 修改信息对象
   */
  updateBannerInfo (bannerObj) {
    return request({
      url: '/educms/admin/banner',
      method: 'put',
      data: bannerObj
    });
  },

  /**
   * 根据ID查询轮播图
   * @param {*} id BannerID
   */
  getBannerInfo(id) {
    return request({
      url: `/educms/admin/banner/${id}`,
      method: 'get',
    });
  },

  /**
   * 保存轮播图
   * @param {*} bannerObj 保存信息
   */
  addBannerInfo (bannerObj) {
    return request({
      url: '/educms/admin/banner',
      method: 'post',
      data: bannerObj
    });
  }
}
