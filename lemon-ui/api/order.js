import request from '@/utils/request';

export default {

  /**
   * 生成订单
   * @param {*} id 课程ID 
   */
  createOrders(id) {
    return request({
      url: `/eduorder/order/${id}`,
      method: 'post'
    });
  },

  /**
   * 根据订单ID查询订单信息
   * @param {*} id 订单ID
   */
  getOrdersInfo(id) {
    return request({
      url: `/eduorder/order/${id}`,
      method: 'get'
    });
  },

  /**
   * 生成二维码的方法
   * @param {*} no 订单号
   */
  createNative(no) {
    return request({
      url: `/eduorder/pay/${no}`,
      method: 'get'
    });
  },

  /**
   * 查询订单状态的方法
   * @param {*} no 订单号
   */
  queryOrderStatus(no) {
    return request({
      url: `/eduorder/pay/status/${no}`,
      method: 'get'
    });
  },
}
