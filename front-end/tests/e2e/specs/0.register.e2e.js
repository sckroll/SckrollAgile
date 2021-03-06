const path = require('path')
const del = require('del')
const Chance = require('chance')
const rw = require('rw')

const user = {}

module.exports = {
  before: function () {
    // 기존 사용자 데이터 삭제
    const userDataPath = path.resolve('tests/e2e/data/user.js')
    del.sync([userDataPath])

    // 무작위의 사용자 데이터 생성
    const chance = new Chance()
    const name = chance.name().split(' ')
    user.firstName = name[0]
    user.lastName = name[1]
    user.username = name[0].toLowerCase() + chance.integer({ min: 0, max: 1000000 })
    user.emailAddress = user.username + '@e2e.sckroll.com'
    user.password = 'q1w2e3r4'

    // 사용자 데이터 파일에 주석 추가
    let fileContent = '// This file is auto-generated by ' + path.basename(__filename) + '\n'
    fileContent += '/* eslint-disable */\n'
    fileContent += 'module.exports = ' + JSON.stringify(user) + '\n'
    fileContent += '/* eslint-enable */\n'

    // 사용자 데이터를 파일에 작성
    rw.writeFileSync(userDataPath, fileContent, 'utf8')
  },
  '등록 페이지는 요소를 렌더링한다': function (browser) {
    const registerPage = browser.page.RegisterPage()

    registerPage
      .navigate()
      .waitForElementVisible('@app', 500)
      .assert.visible('@usernameInput')
      .assert.visible('@emailAddressInput')
      .assert.visible('@passwordInput')
      .assert.visible('@submitButton')
      // hidden(): deprecated
      // .assert.hidden('@formError')
      .assert.not.visible('@formError')

    browser.end()
  },
  '올바르지 않은 데이터로 등록한다': function (browser) {
    const registerPage = browser.page.RegisterPage()

    // 세 개의 입력 칸 모두 빈칸으로 제출
    registerPage
      .navigate()
      .register('', '', '')

    // 로그인 페이지로 리다이렉트 여부만 확인
    // 각각의 입력 칸을 검증하는 것보다 간단히 어설션을 수행할 수 있음
    browser
      .assert.urlEquals(browser.launchUrl + 'register')
      .end()
  },
  '올바른 데이터로 등록한다': function (browser) {
    const registerPage = browser.page.RegisterPage()

    registerPage
      .navigate()
      .register(user.username, user.emailAddress, user.password)

    browser.pause(2000)

    browser
      .assert.urlEquals(browser.launchUrl + 'login')
      .end()
  }
}
