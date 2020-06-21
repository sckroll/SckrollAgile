import { mount, createLocalVue } from '@vue/test-utils'
import VueRouter from 'vue-router'
import RegisterPage from '@/views/RegisterPage'

// vm.$router에 접근할 수 있도록 테스트에 Vue Router 추가
const localVue = createLocalVue()
localVue.use(VueRouter)
const router = new VueRouter

// registrationService의 mock
jest.mock('@/services/registration')

describe('RegisterPage.vue', () => {
  let wrapper
  let fieldUsername
  let fieldEmailAddress
  let fieldPassword
  let buttonSubmit

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
  })

  // 모든 테스트 수행 후에 수행할 작업
  afterAll(() => {
    // registrationService 복구
    jest.restoreAllMocks()
  })

  it('회원가입 폼이 정상적으로 렌더링됨', () => {
    expect(wrapper.find('.logo').attributes().src)
      .toEqual('/static/images/logo.png')
    expect(wrapper.find('.tagline').text())
      .toEqual('Sckroll\'s kanban board project')
    expect(fieldUsername.element.value).toEqual('')
    expect(fieldEmailAddress.element.value).toEqual('')
    expect(fieldPassword.element.value).toEqual('')
    expect(buttonSubmit.text()).toEqual('계정 만들기')
  })

  it('데이터 모델이 초깃값을 정상적으로 가짐', () => {
    expect(wrapper.vm.form.username).toEqual('')
    expect(wrapper.vm.form.emailAddress).toEqual('')
    expect(wrapper.vm.form.password).toEqual('')
  })

  it('데이터 모델과 폼 입력 필드 사이의 바인딩 존재', async () => {
    const username = 'sckroll'
    const emailAddress = 'sckroll@local'
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

  it('`submitForm` 폼 이벤트 핸들러가 존재', () => {
    const stub = jest.fn()

    // setMethods()는 deprecated된 상태
    // 대체 방법을 몰라 우선 남겨둠
    wrapper.setMethods({ submitForm: stub })
    buttonSubmit.trigger('submit')

    expect(stub).toBeCalled()
  })

  it('새로운 사용자라면 회원가입 성공', () => {
    const stub = jest.fn()

    wrapper.vm.$router.push = stub
    wrapper.vm.form.username = 'sckroll'
    wrapper.vm.form.emailAddress = 'sckroll@local'
    wrapper.vm.form.password = 'q1w2e3r4'
    wrapper.vm.submitForm()
    wrapper.vm.$nextTick(() => {
      expect(stub).toHaveBeenCalledWith({ name: 'LoginPage' })  // 로그인 페이지로 리다이렉트
    })
  })

  it('이미 등록된 사용자라면 회원가입 실패', () => {
    // mock에서는 오직 sckroll@local만 새로운 사용자
    wrapper.vm.form.emailAddress ='kimsc@local'
    expect(wrapper.find('.failed').isVisible()).toBe(false)   // submitForm() 호출 전에 에러 메시지가 보이지 않음
    wrapper.vm.submitForm()
    wrapper.vm.$nextTick(null, () => {
      expect(wrapper.find('.failed').isVisible()).toBe(true)  // submitForm() 호출 전에 에러 메시지가 보임
    })
  })
})

