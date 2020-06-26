<template>
  <div class="container public">
    <div class="row justify-content-center">
      <div class="login-form">
        <logo />
        <form @submit.prevent="submitForm">
          <div v-show="errorMessage" class="alert alert-danger failed">{{ errorMessage }}</div>

          <div class="form-group">
            <label for="username">사용자명 혹은 이메일 주소</label>
            <input type="text" class="form-control" id="username" v-model="form.username">
            <div class="field-error" v-if="$v.form.username.$dirty">
              <div class="error" v-if="!$v.form.username.required">사용자명이나 이메일 주소를 입력해주세요.</div>
            </div>
          </div>
          <div class="form-group">
            <label for="password">비밀번호</label>
            <input type="password" class="form-control" id="password" v-model="form.password">
            <div class="field-error" v-if="$v.form.password.$dirty">
              <div class="error" v-if="!$v.form.password.required">비밀번호를 입력해주세요.</div>
            </div>
          </div>
          <button type="submit" class="btn btn-primary btn-block">로그인</button>

          <div class="links">
            <p class="sign-up text-muted">회원이 아니신가요? <a href="/register" class="link-sign-up">회원가입</a></p>
            <p><a href="#">비밀번호를 잊어버렸나요?</a></p>
          </div>
        </form>
      </div>
    </div>
    <page-footer />
  </div>
</template>

<script>
import { required } from 'vuelidate/lib/validators'
import authenticationService from '@/services/authentication'
import Logo from '@/components/Logo.vue'
import PageFooter from '@/components/PageFooter.vue'

export default {
  name: 'LoginPage',
  components: {
    Logo,
    PageFooter
  },
  data () {
    return {
      form: {
        username: '',
        password: ''
      },
      errorMessage: ''
    }
  },
  validations: {
    form: {
      username: {
        required
      },
      password: {
        required
      }
    }
  },
  methods: {
    submitForm () {
      // Vuelidate로 데이터 검증 시작
      this.$v.$touch()
      if (this.$v.$invalid) {
        return
      }

      // TODO: 데이터 인증
      authenticationService.authenticate(this.form).then(() => {
        this.$router.push({ name: 'home' })
      }).catch(error => {
        this.errorMessage = error.message
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.links {
  margin: 30px 0 50px 0;
  text-align: center;
}
.container {
  max-width: 900px;
}
.login-form {
  margin-top: 50px;
  max-width: 320px;
}
</style>
