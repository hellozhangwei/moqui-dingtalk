<script src="https://g.alicdn.com/dingding/h5-dingtalk-login/0.21.0/ddlogin.js"></script>

<!-- STEP1：在HTML中添加包裹容器元素 -->
<div id="self_defined_element" class="self-defined-classname"></div>
<style>
    /* STEP2：指定这个包裹容器元素的CSS样式，尤其注意宽高的设置 */
    .self-defined-classname {
        width: 300px;
        height: 300px;
    }
</style>
<script>
  // STEP3：在需要的时候，调用 window.DTFrameLogin 方法构造登录二维码，并处理登录成功或失败的回调。
  window.DTFrameLogin(
      {
        id: 'self_defined_element',
        width: 300,
        height: 300,
      },
      {
        redirect_uri: encodeURIComponent('http://ae94-101-30-126-62.ngrok-free.app/ddlogin/auth'),
        client_id: 'dingzcsmodzfv5ec3vng',
        scope: 'openid',
        response_type: 'code',
        //state: 'xxxxxxxxx',
        prompt: 'consent',
      },
      (loginResult) => {
        debugger
        const {redirectUrl, authCode, state} = loginResult;
        // 这里可以直接进行重定向
        window.location.href = redirectUrl;
        // 也可以在不跳转页面的情况下，使用code进行授权
        console.log(authCode);
      },
      (errorMsg) => {
        debugger
        // 这里一般需要展示登录失败的具体原因
        alert(`Login Error: ${errorMsg}`);
      },
  );
</script>