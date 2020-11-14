import request from '@/utils/request';

export default {

  /**
   * 讲师列表（条件查询分页）
   * @param {current} current 当前页
   * @param {limit} limit 每页的记录数
   * @param {teacherQuery} teacherQuery 查询条件对象
   */
  getTeacherListPage(current, limit, teacherQuery) {
    return request({
      url: `/eduservice/teacher/condition/${current}/${limit}`,
      method: 'post',
      // teacherQuery条件对象，后端使用的是RequestBody获取数据
      // data表示把对象转换json进行传递到接口里面去
      data: teacherQuery
    });
  },

  /**
   * 根据id逻辑删除教师
   * @param {id} id 教师id
   */
  deleteTeacherById(id) {
    return request({
      url: `/eduservice/teacher/${id}`,
      method: 'delete'
    });
  },

  addTeacher(teacher) {
    return request({
      url: `/eduservice/teacher`,
      method: 'post',
      data: teacher
    });
  }

}
