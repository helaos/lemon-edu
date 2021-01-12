package com.fatehole.educenter.mapper;

import com.fatehole.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author helaos
 * @since 2020-11-29
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    /**
     * 统计指定日期的注册人数
     * @param day 日期
     * @return 人数
     */
    int selectRegisterCount(String day);
}
