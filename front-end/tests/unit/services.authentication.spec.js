import moxios from 'moxios'
import authenticationService from '@/services/authentication'

describe('services/authentication', () => {
  beforeEach(() => {
    moxios.install()
  })

  afterEach(() => {
    moxios.uninstall()
  })

  it('`/authentications` API를 호출한다', () => {
    expect.assertions(1)
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      expect(request.url).toEqual('/authentications')
      request.respondWith({
        status: 200,
        response: { result: 'success' }
      })
    })
    return authenticationService.authenticate()
  })

  it('요청에 성공하면 호출한 곳에 응답을 전달한다', () => {
    expect.assertions(2)
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      expect(request).toBeTruthy()
      request.respondWith({
        status: 200,
        response: { result: 'success' }
      })
    })
    return authenticationService.authenticate().then(data => {
      expect(data.result).toEqual('success')
    })
  })

  it('요청에 실패하면 호출한 곳에 에러를 전파한다', () => {
    expect.assertions(2)
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      expect(request).toBeTruthy()
      request.reject({
        response: {
          status: 400,
          data: { message: 'Bad request' }
        }
      })
    })
    return authenticationService.authenticate().catch(error => {
      expect(error.message).toEqual('Bad request')
    })
  })
})
