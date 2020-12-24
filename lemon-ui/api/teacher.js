import request from '@/utils/request';

export default {

  /**
   * 教师信息分页查询
   * @param {*} page 当前页码
   * @param {*} limit 每页记录数
   */
  getTeacherList(page, limit) {
    return request({
      url: `/eduservice/front/teacher/${page}/${limit}`,
      method: 'get'
    })
  },

  /**
   * 获取教师详情
   * @param {*} id 教师ID
   */
  getTeacherInfo(id) {
    return request({
      url: `/eduservice/front/teacher/info/${id}`,
      method: 'get'
    })
  }
}
