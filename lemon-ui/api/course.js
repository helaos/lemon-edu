import request from '@/utils/request';

export default {

  /**
   * 获取课程列表
   * @param {*} page 当前页码
   * @param {*} limit 每页记录数
   * @param {*} searchObj 查询条件
   */
  getCourseList(page, limit, searchObj) {
    return request({
      url: `/eduservice/front/course/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },

  /**
   * 获取二级分类数据 
   */
  getAllSubject() {
    return request({
      url: '/eduservice/subject',
      method: 'get'
    })
  },

  /**
   * 根据课程ID查询课程的详情
   * @param {*} id 课程ID
   */
  getCourseInfo(id) {
    return request({
      url: `/eduservice/front/course/${id}`,
      method: 'get'
    })
  }

}
