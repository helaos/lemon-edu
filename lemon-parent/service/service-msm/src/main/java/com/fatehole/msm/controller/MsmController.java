package com.fatehole.msm.controller;

import com.fatehole.commonutil.Result;
import com.fatehole.msm.service.MsmService;
import com.fatehole.msm.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/29/20:39
 */
@RestController
// @CrossOrigin
@RequestMapping("/edumsm")
public class MsmController {

    private MsmService msmService;

    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setMsmService(MsmService msmService) {
        this.msmService = msmService;
    }

    @GetMapping("/send/{phone}")
    public Result sendMsm(@PathVariable("phone") String phone) {

        // 从redis中取验证码，如果取到直接返回
        String code = redisTemplate.opsForValue().get(phone);

        if (!StringUtils.isEmpty(code)) {
            return Result.ok();
        }

        // 如果redis取不到值，进行阿里云发送
        // 生成随机的值，传给阿里云进行发送
        code = RandomUtil.getSixBitRandom();

        Map<String, Object> param = new HashMap<>(1);
        param.put("code", code);

        // 执行发送验证码到目标手机号
        boolean isSend = msmService.send(phone, param);

        if (isSend) {
            // 发送成功，再将验证码放入redis里
            // 并设置有效时间 5分钟
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.error().message("验证码发送失败！");
        }

    }
}
