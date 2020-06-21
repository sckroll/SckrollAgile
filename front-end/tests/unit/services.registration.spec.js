import moxios from 'moxios'
import registrationService from '@/services/registration'

describe('services/registration', () => {
  beforeEach(() => {
    moxios.install()
  })

  afterEach(() => {
    moxios.uninstall()
  })

  it('요청에 성공했을 때 서버의 응답을 반환', () => {
    expect.assertions(2)    // assertion이 호출되는 횟수 검증
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      expect(request).toBeTruthy()
      request.respondWith({
        status: 200,
        response: { result: 'success' }
      })
    })

    return registrationService.register().then(data => {
      expect(data.result).toEqual('success')
    })
  })

  it('요청에 실패했을 때 에러 발생', () => {
    expect.assertions(2)
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      expect(request).toBeTruthy()
      request.reject({
        status: 400,
        response: { message: 'Bad request' }
      })
    })

    return registrationService.register().catch(error => {
      expect(error.response.message).toEqual('Bad request')
    })
  })
})
