import axios from 'axios';
import { MessageBox, Message } from 'element-ui';
import cookie from 'js-cookie';

// 创建axios实例
const service = axios.create({
  baseURL: 'http://localhost:9527', // api的base_url
  timeout: 20000 // 请求超时时间
})

// http request 拦截器
service.interceptors.request.use(
  config => {
    // debugger
    if (cookie.get('lemon_token')) {
      config.headers['token'] = cookie.get('lemon_token');
    }
    return config;
  },

  err => {
    return Promise.reject(err);
  }
)

service.interceptors.response.use(
  response => {
    //debugger
    if (response.data.code == 28004) {
        console.log("response.data.resultCode是28004")
        // 返回 错误代码-1 清除ticket信息并跳转到登录页面
        //debugger
        window.location.href="/login"
        return
    }else{
      if (response.data.code !== 20000) {
        //25000：订单支付中，不做任何提示
        if(response.data.code != 25000) {
          Message({
            message: response.data.message || 'error',
            type: 'error',
            duration: 5 * 1000
          })
        }
      } else {
        return response;
      }
    }
  },
  error => {
    return Promise.reject(error.response)   // 返回接口返回的错误信息
});

export default service
