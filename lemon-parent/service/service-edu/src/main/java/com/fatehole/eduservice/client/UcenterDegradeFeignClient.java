package com.fatehole.eduservice.client;

import com.fatehole.commonutil.vo.CommentInfoVo;
import org.springframework.stereotype.Component;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/12/09/8:34
 */
@Component
public class UcenterDegradeFeignClient implements UcenterClient {
    @Override
    public CommentInfoVo getMemberInfo(String id) {
        return null;
    }
}
