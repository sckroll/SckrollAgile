<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="register-form">
        <logo />
        <form @submit.prevent="submitForm">
          <div class="alert alert-danger failed" v-show="errorMessage">{{ errorMessage }}</div>

          <div class="form-group">
            <label for="username">사용자명</label>
            <input type="text" class="form-control" id="username" v-model="form.username">
            <div class="field-error" v-if="$v.form.username.$dirty">
              <div class="error" v-if="!$v.form.username.required">사용자명을 입력해주세요.</div>
              <div class="error" v-if="!$v.form.username.alphaNum">사용자명은 영문자 및 숫자만 가능합니다.</div>
              <div class="error" v-if="!$v.form.username.minLength">사용자명은 최소 {{$v.form.username.$params.minLength.min}}글자여야 합니다.</div>
              <div class="error" v-if="!$v.form.username.maxLength">사용자명은 최대 {{$v.form.username.$params.maxLength.max}}글자여야 합니다.</div>
            </div>
          </div>
          <div class="form-group">
            <label for="emailAddress">이메일 주소</label>
            <input type="email" class="form-control" id="emailAddress" v-model="form.emailAddress">
            <div class="field-error" v-if="$v.form.emailAddress.$dirty">
              <div class="error" v-if="!$v.form.emailAddress.required">이메일 주소를 입력해주세요.</div>
              <div class="error" v-if="!$v.form.emailAddress.email">올바른 이메일 양식이 아닙니다.</div>
              <div class="error" v-if="!$v.form.emailAddress.maxLength">이메일 주소는 최대 {{$v.form.emailAddress.$params.maxLength.max}}글자여야 합니다.</div>
            </div>
          </div>
          <div class="form-group">
            <label for="password">비밀번호</label>
            <input type="password" class="form-control" id="password" v-model="form.password">
            <div class="field-error" v-if="$v.form.password.$dirty">
              <div class="error" v-if="!$v.form.password.required">비밀번호를 입력해주세요.</div>
              <div class="error" v-if="!$v.form.password.minLength">비밀번호는 최소 {{$v.form.password.$params.minLength.min}}글자여야 합니다.</div>
              <div class="error" v-if="!$v.form.password.maxLength">비밀번호는 최대 {{$v.form.password.$params.maxLength.max}}글자여야 합니다.</div>
            </div>
          </div>
          <button type="submit" class="btn btn-primary btn-block">계정 만들기</button>
          <p class="text-center text-muted">계정이 있으신가요? <a href="/login">로그인</a></p>
        </form>
      </div>
    </div>
    <page-footer />
  </div>
</template>

<script>
import { required, email, minLength, maxLength, alphaNum } from 'vuelidate/lib/validators'
import registrationService from '@/services/registration'
import Logo from '@/components/Logo.vue'
import PageFooter from '@/components/PageFooter.vue'

export default {
  name: 'RegisterPage',
  components: {
    Logo,
    PageFooter
  },
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
  validations: {
    form: {
      username: {
        required,
        minLength: minLength(2),
        maxLength: maxLength(50),
        alphaNum
      },
      emailAddress: {
        required,
        email,
        maxLength: maxLength(100)
      },
      password: {
        required,
        minLength: minLength(6),
        maxLength: maxLength(30)
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

      // TODO: 데이터 검증
      registrationService.register(this.form).then(() => {
        this.$router.push({ name: 'login' })
      }).catch(err => {
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
</style>
