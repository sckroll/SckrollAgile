import { mount, createLocalVue } from '@vue/test-utils'
import VueRouter from 'vue-router'
import Vuelidate from 'vuelidate'
import registrationService from '@/services/registration'
import RegisterPage from '@/views/RegisterPage'

// vm.$router에 접근할 수 있도록 테스트에 Vue Router 추가
const localVue = createLocalVue()
localVue.use(VueRouter)
localVue.use(Vuelidate)
const router = new VueRouter

// registrationService의 mock
jest.mock('@/services/registration')

describe('RegisterPage.vue', () => {
  let wrapper
  let fieldUsername
  let fieldEmailAddress
  let fieldPassword
  let buttonSubmit
  let registerSpy

  // 각 테스트 수행 전에 수행할 작업
  beforeEach(() => {
    wrapper = mount(RegisterPage, {
      localVue,
      router
    })
    fieldUsername = wrapper.find('#username')
    fieldEmailAddress = wrapper.find('#emailAddress')
    fieldPassword = wrapper.find('#password')
    buttonSubmit = wrapper.find('form button[type=submit]')

    // 회원가입 서비스를 위한 스파이 생성
    registerSpy = jest.spyOn(registrationService, 'register')
  })

  // 각 테스트 수행 후에 수행할 작업
  afterEach(() => {
    registerSpy.mockReset()
    registerSpy.mockRestore()
  })

  // 모든 테스트 수행 후에 수행할 작업
  afterAll(() => {
    // registrationService 복구
    jest.restoreAllMocks()
  })

  it('회원가입 폼이 정상적으로 렌더링된다', () => {
    expect(wrapper.find('.logo').attributes().src)
      .toEqual('/static/images/logo.png')
    expect(wrapper.find('.tagline').text())
      .toEqual('Sckroll\'s kanban board project')
    expect(fieldUsername.element.value).toEqual('')
    expect(fieldEmailAddress.element.value).toEqual('')
    expect(fieldPassword.element.value).toEqual('')
    expect(buttonSubmit.text()).toEqual('계정 만들기')
  })

  it('데이터 모델이 초깃값을 정상적으로 가진다', () => {
    expect(wrapper.vm.form.username).toEqual('')
    expect(wrapper.vm.form.emailAddress).toEqual('')
    expect(wrapper.vm.form.password).toEqual('')
  })

  it('데이터 모델과 폼 입력 필드 사이의 바인딩이 존재한다', async () => {
    const username = 'sckroll'
    const emailAddress = 'sckroll@sckroll.com'
    const password = 'q1w2e3r4'

    wrapper.vm.form.username = username
    wrapper.vm.form.emailAddress = emailAddress
    wrapper.vm.form.password = password

    // $nextTick()으로 wrapper에 변경된 데이터 모델 적용
    await wrapper.vm.$nextTick()

    expect(fieldUsername.element.value).toEqual(username)
    expect(fieldEmailAddress.element.value).toEqual(emailAddress)
    expect(fieldPassword.element.value).toEqual(password)
  })

  it('`submitForm` 폼 이벤트 핸들러가 존재한다', () => {
    const stub = jest.fn()

    // setMethods()는 deprecated된 상태
    // 대체 방법을 몰라 우선 남겨둠
    wrapper.setMethods({ submitForm: stub })
    buttonSubmit.trigger('submit')

    expect(stub).toBeCalled()
  })

  it('새로운 사용자라면 회원가입에 성공한다', async () => {
    expect.assertions(2)
    const stub = jest.fn()
    wrapper.vm.$router.push = stub

    wrapper.vm.form.username = 'sckroll'
    wrapper.vm.form.emailAddress = 'sckroll@sckroll.com'
    wrapper.vm.form.password = 'q1w2e3r4'
    wrapper.vm.submitForm()

    expect(registerSpy).toBeCalled()
    await wrapper.vm.$nextTick()

    // 로그인 페이지로 리다이렉트
    expect(stub).toHaveBeenCalledWith({ name: 'login' })
  })

  it('이미 등록된 사용자라면 회원가입에 실패한다', async () => {
    expect.assertions(3)

    // mock에서는 오직 sckroll@sckroll.com만 새로운 사용자
    wrapper.vm.form.username = 'kimsc'
    wrapper.vm.form.emailAddress ='kimsc@sckroll.com'
    wrapper.vm.form.password = 'kksscc'

    // submitForm() 호출 전에 에러 메시지가 보이지 않음
    expect(wrapper.find('.failed').isVisible()).toBe(false)
    wrapper.vm.submitForm()
    expect(registerSpy).toBeCalled()

    // $nextTick()을 한 번만 사용하면 테스트 실패 (원인은 모름)
    await wrapper.vm.$nextTick()
    await wrapper.vm.$nextTick()

    // submitForm() 호출 전에 에러 메시지가 보임
    expect(wrapper.find('.failed').isVisible()).toBe(true)
  })

  it('이메일 주소가 유효하지 않으면 register() 메소드 호출에 실패한다', () => {
    wrapper.vm.form.username = 'sckroll'
    wrapper.vm.form.emailAddress = 'bad-email-address'
    wrapper.vm.form.password = 'q1w2e3r4'
    wrapper.vm.submitForm()
    expect(registerSpy).not.toHaveBeenCalled()
  })

  it('사용자 이름이 유효하지 않으면 register() 메소드 호출에 실패한다', () => {
    wrapper.vm.form.username = 'a'
    wrapper.vm.form.emailAddress = 'sckroll@sckroll.com'
    wrapper.vm.form.password = 'q1w2e3r4'
    wrapper.vm.submitForm()
    expect(registerSpy).not.toHaveBeenCalled()
  })

  it('비밀번호가 유효하지 않으면 register() 메소드 호출에 실패한다', () => {
    wrapper.vm.form.username = 'sckroll'
    wrapper.vm.form.emailAddress = 'sckroll@sckroll.com'
    wrapper.vm.form.password = 'bad'
    wrapper.vm.submitForm()
    expect(registerSpy).not.toHaveBeenCalled()
  })
})
