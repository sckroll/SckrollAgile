<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="register-form">
        <div class="logo-wrapper">
          <img src="/static/images/logo.png" class="logo">
          <div class="tagline">Sckroll's kanban board project</div>
        </div>
        <form>
          <div class="form-group">
            <label for="username">이름</label>
            <input type="text" class="form-control" id="username">
          </div>
          <div class="form-group">
            <label for="emailAddress">이메일 주소</label>
            <input type="email" class="form-control" id="emailAddress">
          </div>
          <div class="form-group">
            <label for="password">비밀번호</label>
            <input type="password" class="form-control" id="password">
          </div>
          <button type="submit" class="btn btn-primary btn-block">계정 만들기</button>
          <p class="text-center text-muted">계정이 있으신가요? <a href="/login">로그인</a></p>
        </form>
      </div>
    </div>
    <footer class="footer">
      <span class="copyright">&copy; 2020 Sckroll</span>
      <ul class="footer-links list-inline float-right">
        <li class="list-inline-item"><a href="#">About</a></li>
        <li class="list-inline-item"><a href="#">Terms of Service</a></li>
        <li class="list-inline-item"><a href="#">Privacy Policy</a></li>
        <li class="list-inline-item"><a href="https://github.com/sckroll/SckrollAgile">GitHub</a></li>
      </ul>
    </footer>
  </div>
</template>

<script>
export default {

}
</script>

<style lang="scss" scoped>
  .container {
    max-width: 900px;
  }
  .register-form {
    margin-top: 50px;
    max-width: 320px;
  }
  .logo-wrapper {
    text-align: center;
    margin-bottom: 40px;

    .logo {
      max-width: 300px;
      margin: 0 auto;
    }

    .tagline {
      line-height: 180%;
      color: #666;
    }
  }
  .footer {
    width: 100%;
    line-height: 40px;
    margin-top: 50px;
  }
</style>
