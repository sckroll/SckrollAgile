import { mount, createLocalVue } from '@vue/test-utils'
import Vuelidate from 'vuelidate'
import VueRouter from 'vue-router'
import LoginPage from '@/views/LoginPage'
import authenticationService from '@/services/authentication'

// 로컬 Vue와 Vuelidate 설정
const localVue = createLocalVue()
localVue.use(Vuelidate)
localVue.use(VueRouter)
const router = new VueRouter()

// registrationService의 mock
jest.mock('@/services/authentication')

describe('LoginPage.vue', () => {
  // 로그인 창에서는 username은 사용자명과 이메일 주소 중 하나 입력 가능
  let wrapper
  let fieldUsername
  let fieldPassword
  let buttonSubmit
  let authenticateSpy

  beforeEach(() => {
    wrapper = mount(LoginPage, {
      localVue,
      router
    })
    fieldUsername = wrapper.find('#username')
    fieldPassword = wrapper.find('#password')
    buttonSubmit = wrapper.find('form button[type="submit"]')

    // 인증 서비스를 위한 스파이 생성
    authenticateSpy = jest.spyOn(authenticationService, 'authenticate')
  })

  afterEach(() => {
    authenticateSpy.mockReset()
    authenticateSpy.mockRestore()
  })

  afterAll(() => {
    jest.restoreAllMocks()
  })

  it('로그인 폼 렌더링', () => {
    expect(wrapper.find('.logo').attributes().src)
      .toEqual('/static/images/logo.png')
    expect(wrapper.find('.tagline').text())
      .toEqual('Sckroll\'s kanban board project')
    expect(fieldUsername.element.value).toEqual('')
    expect(fieldPassword.element.value).toEqual('')
    expect(buttonSubmit.text()).toEqual('로그인')
    expect(wrapper.find('.link-sign-up').attributes().href)
      .toEqual('/register')
    expect(wrapper.find('.link-forgot-password')).toBeTruthy()
  })

  it('초깃값을 갖는 데이터 모델 포함', () => {
    expect(wrapper.vm.form.username).toEqual('')
    expect(wrapper.vm.form.password).toEqual('')
  })

  it('데이터 모델과 연결된 폼 입력을 가짐', async () => {
    const username = 'sckroll'
    const password = 'q1w2e3r4'

    wrapper.vm.form.username = username
    wrapper.vm.form.password = password

    // $nextTick()으로 wrapper에 변경된 데이터 모델 적용
    await wrapper.vm.$nextTick()

    expect(fieldUsername.element.value).toEqual(username)
    expect(fieldPassword.element.value).toEqual(password)
  })

  it('`submitForm` 폼 제출 핸들러를 가짐', () => {
    const stub = jest.fn()

    // setMethods()는 deprecated된 상태
    // 대체 방법을 몰라 우선 남겨둠
    wrapper.setMethods({ submitForm: stub })
    buttonSubmit.trigger('submit')

    expect(stub).toBeCalled()
  })

  it('자격이 유효하면 제출 성공', async () => {
    expect.assertions(2)
    const stub = jest.fn()
    wrapper.vm.$router.push = stub

    wrapper.vm.form.username = 'sckroll'
    wrapper.vm.form.password = 'q1w2e3r4'
    wrapper.vm.submitForm()

    expect(authenticateSpy).toBeCalled()
    await wrapper.vm.$nextTick()

    expect(stub).toHaveBeenCalledWith({ name: 'home' })
  })

  it('자격이 유효하지 않으면 제출 실패', async () => {
    expect.assertions(3)

    // mock에서는 'sckroll' 혹은 'sckroll@sckroll.com'과 연결된
    // 비밀번호 'q1w2e3r4'만 유효함
    wrapper.vm.form.username = 'sckroll'
    wrapper.vm.form.password = 'failPassword'

    // submitForm() 호출 전에 에러 메시지가 보이지 않음
    expect(wrapper.find('.failed').isVisible()).toBe(false)
    wrapper.vm.submitForm()
    expect(authenticateSpy).toBeCalled()

    // $nextTick()을 한 번만 사용하면 테스트 실패 (원인은 모름)
    await wrapper.vm.$nextTick()
    await wrapper.vm.$nextTick()

    // submitForm() 호출 전에 에러 메시지가 보임
    expect(wrapper.find('.failed').isVisible()).toBe(true)
  })

  it('유효하지 않은 데이터 제출을 방지하기 위한 검증 존재', async () => {
    // 빈 폼
    wrapper.vm.submitForm()
    expect(authenticateSpy).not.toHaveBeenCalled()

    // 사용자명만 유효할 경우
    wrapper.vm.form.username = 'sckroll'
    wrapper.vm.submitForm()
    expect(authenticateSpy).not.toHaveBeenCalled()

    // 이메일 주소만 유효할 경우
    wrapper.vm.form.username = 'sckroll@sckroll.com'
    wrapper.vm.submitForm()
    expect(authenticateSpy).not.toHaveBeenCalled()

    // 비밀번호만 유효할 경우
    wrapper.vm.form.password = 'q1w2e3r4'
    wrapper.vm.form.username = ''
    wrapper.vm.submitForm()
    expect(authenticateSpy).not.toHaveBeenCalled()
  })
})
