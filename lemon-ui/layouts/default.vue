<template>
  <div class="in-wrap">
    <!-- 公共头引入 -->
    <header id="header">
      <section class="container">
        <h1 id="logo">
          <a href="#" title="柠檬🍋教育">
            <img src="~/assets/img/logo.png" width="100%" alt="柠檬🍋教育" />
          </a>
        </h1>
        <div class="h-r-nsl">
          <ul class="nav">
            <router-link to="/" tag="li" active-class="current" exact>
              <a>首页</a>
            </router-link>
            <router-link to="/course" tag="li" active-class="current">
              <a>课程</a>
            </router-link>
            <router-link to="/teacher" tag="li" active-class="current">
              <a>名师</a>
            </router-link>
            <router-link to="/article" tag="li" active-class="current">
              <a>文章</a>
            </router-link>
            <router-link to="/qa" tag="li" active-class="current">
              <a>问答</a>
            </router-link>
          </ul>

          <!-- / nav -->
          <ul class="h-r-login">
            <li v-if="!loginInfo.id" id="no-login">
              <a href="/login" title="登录">
                <em class="icon18 login-icon">&nbsp;</em>
                <span class="vam ml5">登录</span>
              </a>
              |
              <a href="/register" title="注册">
                <span class="vam ml5">注册</span>
              </a>
            </li>
            <li v-if="loginInfo.id" id="is-login-one" class="mr10">
              <a id="headerMsgCountId" href="#" title="消息">
                <em class="icon18 news-icon">&nbsp;</em>
              </a>
              <q class="red-point" style="display: none">&nbsp;</q>
            </li>
            <li v-if="loginInfo.id" id="is-login-two" class="h-r-user">
              <a href="/ucenter" title>
                <img :src="loginInfo.avatar" width="30" height="30" class="vam picImg" alt>
                <span id="userName" class="vam disIb">
                  {{ loginInfo.nickname }}
                </span>
              </a>
              <a href="javascript:void(0);" title="退出" @click="logout()" class="ml5">退出</a>
            </li>
            <!-- /未登录显示第1 li；登录后显示第2，3 li -->
          </ul>

          <aside class="h-r-search">
            <form action="#" method="post">
              <label class="h-r-s-box">
                <input type="text" placeholder="输入你想学的课程" name="queryCourse.courseName" value />
                <button type="submit" class="s-btn">
                  <em class="icon18">&nbsp;</em>
                </button>
              </label>
            </form>
          </aside>
        </div>
        <aside class="mw-nav-btn">
          <div class="mw-nav-icon"></div>
        </aside>
        <div class="clear"></div>
      </section>
    </header>

    <!-- /公共头引入 -->

    <nuxt />

    <!-- 公共底引入 -->


    <footer id="footer">
      <section class="container">
        <div class>
          <h4 class="hLh30">
            <span class="fsize18 f-fM c-999">友情链接</span>
          </h4>
          <ul class="of flink-list">
            <li>
              <a href="http://www.atguigu.com/" title="尚硅谷" target="_blank">尚硅谷</a>
            </li>
          </ul>
          <div class="clear"></div>
        </div>
        <div class="b-foot">
          <section class="fl col-7">
            <section class="mr20">
              <section class="b-f-link">
                <a href="#" title="关于我们" target="_blank">关于我们</a>|
                <a href="#" title="联系我们" target="_blank">联系我们</a>|
                <a href="#" title="帮助中心" target="_blank">帮助中心</a>|
                <a href="#" title="资源下载" target="_blank">资源下载</a>|
                <span>服务热线：xxx-xxxxxxxx(北京) xxx-xxxxxxxx(深圳)</span>
                <span>Email：cifanse@outlook.com</span>
              </section>
              <section class="b-f-link mt10">
                <span>©2018课程版权均归柠檬🍋教育所有 xICP备xxxxxxxx号</span>
              </section>
            </section>
          </section>
          <aside class="fl col-3 tac mt15">
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wx-icon.png" alt />
              </span>
            </section>
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wb-icon.png" alt />
              </span>
            </section>
          </aside>
          <div class="clear"></div>
        </div>
      </section>
    </footer>
    <!-- /公共底引入 -->
  </div>
</template>
<script>
  import "~/assets/css/reset.css";
  import "~/assets/css/theme.css";
  import "~/assets/css/global.css";
  import "~/assets/css/web.css";
  import cookie from 'js-cookie';
  import login from '@/api/login';
  import '~/assets/css/reset.css'
  import '~/assets/css/theme.css'
  import '~/assets/css/global.css'
  import '~/assets/css/web.css'
  import '~/assets/css/base.css'
  import '~/assets/css/activity_tab.css'
  import '~/assets/css/bottom_rec.css'
  import '~/assets/css/nice_select.css'
  import '~/assets/css/order.css'
  import '~/assets/css/swiper-3.3.1.min.css'
  import "~/assets/css/pages-weixinpay.css"

  export default {

    data() {
      return {
        token: '',
        loginInfo: {
          id: '',
          age: '',
          avatar: '',
          mobile: '',
          nickname: '',
          sex: ''
        }
      }
    },

    created() {
      // 获取路径中的Token值
      this.token = this.$route.query.token;

      // 判断路径中是否有token值
      if (this.token) {
        this.wxLogin();
      }

      this.showInfo();
    },

    methods: {
      showInfo() {
        // 从cookie获取用户信息
        let userStr = cookie.get('lemon_ucenter');
        // 把字符串转换未json对象
        if (userStr) {
          this.loginInfo = JSON.parse(userStr)
        }
      },

      logout() {
        // debugger
        cookie.set('lemon_ucenter', '', {
          domain: 'localhost'
        })
        cookie.set('lemon_token', '', {
          domain: 'localhost'
        })
        //跳转页面
        window.location.href = '/'
      },

      // 微信登陆的方法
      wxLogin() {
        // 把token值放入cookie中
        cookie.set('lemon_token', this.token, {
          domain: 'localhost'
        });
        cookie.set('lemon_ucenter', '', {
          domain: 'localhost'
        });

        // 根据你的token值
        login.getLoginUserInfo()
          .then(response => {
            this.loginInfo = response.data.data.info;
            cookie.set('lemon_ucenter', this.loginInfo, {
              domain: 'localhost'
            });
          })
      }
    }

  };

</script>
