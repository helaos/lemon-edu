import request from '@/utils/request';

export default {

  /**
   * 根据手机号码发送短信
   * @param {*} mobile 手机号
   */
  getMobile(mobile) {
    return request({
      url: `/edumsm/send/${mobile}`,
      method: 'get'
    });
  },

  /**
   * 用户注册
   * @param {*} formItem 注册信息
   */
  submitRegister(formItem) {
    return request({
      url: '/educenter/member/register',
      method: 'post',
      data: formItem
    })
  }
}
