<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="register-form">
        <div class="logo-wrapper">
          <img src="/static/images/logo.png" class="logo">
          <div class="tagline">Sckroll's kanban board project</div>
        </div>
        <form @submit.prevent="submitForm">
          <div class="alert alert-danger failed" v-show="errorMessage">{{ errorMessage }}</div>

          <div class="form-group">
            <label for="username">이름</label>
            <input type="text" class="form-control" id="username" v-model="form.username">
          </div>
          <div class="form-group">
            <label for="emailAddress">이메일 주소</label>
            <input type="email" class="form-control" id="emailAddress" v-model="form.emailAddress">
          </div>
          <div class="form-group">
            <label for="password">비밀번호</label>
            <input type="password" class="form-control" id="password" v-model="form.password">
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
        <li class="list-inline-item"><a href="https://github.com/sckroll/SckrollAgile" target="_blank">GitHub</a></li>
      </ul>
    </footer>
  </div>
</template>

<script>
import registrationService from '@/services/registration'

export default {
  name: 'RegisterPage',
  data () {
    return {
      form: {
        username: '',
        emailAddress: '',
        password: ''
      },
      errorMessage: ''
    }
  },
  methods: {
    submitForm () {
      // TODO: 데이터 검증
      registrationService.register(this.form).then(() => {
        this.$router.push({ name: 'LoginPage' })
      }).catch((err) => {
        this.errorMessage = '회원가입에 실패하였습니다. 원인: ' + (err.message ? err.message : 'Unknown')
      })
    }
  }
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
