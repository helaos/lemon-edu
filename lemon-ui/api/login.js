import request from '@/utils/request';

export default {

  /**
   * 用户登陆
   * @param {*} userInfo 用户信息
   */
  submitLogin(userInfo) {
    return request({
      url: '/educenter/member/login',
      method: 'post',
      data: userInfo
    })
  },

  getLoginUserInfo() {
    return request({
      url: '/educenter/member/info',
      method: 'get'
    })
  }
}
