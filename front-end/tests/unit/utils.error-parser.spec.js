import errorParser from '@/utils/error-parser'

describe('utils/error-parser', () => {
  it('HTTP 400 에러를 파싱한다', () => {
    const error = {
      response: {
        status: 400,
        data: {
          result: 'failure',
          message: 'This is an error'
        }
      }
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('This is an error')
  })

  it('HTTP 400 단위 테스트 에러를 파싱한다', () => {
    const error = {
      response: {
        status: 400,
        data: {
          message: 'This is a bad request'
        }
      }
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('This is a bad request')
  })

  it('HTTP 400 메시지가 없는 에러를 파싱한다', () => {
    const error = {
      response: {
        status: 400
      }
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('Bad request')
  })

  it('HTTP 401 에러를 파싱한다', () => {
    const error = {
      response: {
        status: 401,
        statusText: 'Unauthorized'
      }
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('Request not authorized.')
  })

  it('HTTP 403 에러를 파싱한다', () => {
    const error = {
      response: {
        status: 403,
        statusText: 'Forbidden'
      }
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('Request forbidden.')
  })

  it('HTTP 404 에러를 파싱한다', () => {
    const error = {
      response: {
        status: 404,
        statusText: 'Not Found'
      }
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('Request failed. Request endpoint not found on the server.')
  })

  it('HTTP 500 알려진 에러를 파싱한다', () => {
    const error = {
      response: {
        status: 500,
        statusText: 'Internal Server Error',
        data: {
          message: 'This is rephrased error message.'
        }
      }
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('This is rephrased error message. Please try again later.')
  })

  it('HTTP 500 알려지지 않은 에러를 파싱한다', () => {
    const error = {
      response: {
        status: 500,
        statusText: 'Internal Server Error'
      }
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('There is an error on the server side. Please try again later.')
  })

  it('HTTP 503 에러를 파싱한다', () => {
    const error = {
      response: {
        status: 503,
        statusText: 'Service Unavailable'
      }
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('Request failed. Please try again later.')
  })

  it('HTTP 504 에러를 파싱한다', () => {
    const error = {
      response: {
        status: 504,
        statusText: 'Gateway Timeout'
      }
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('Request failed. Please try again later.')
  })

  it('빈 응답 에러를 파싱한다', () => {
    const error = {
      request: {}
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('Request failed. No response from the server.')
  })

  it('알려지지 않은 문자열 에러를 파싱한다', () => {
    const error = 'Unknown error'
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('Unknown error')
  })

  it('알려지지 않은 wrapped 에러를 파싱한다', () => {
    const error = new Error('Unknown error')
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('Unknown error')
  })
})
