const data = require('../data/user')

module.exports = {
  '로그인 페이지는 요소를 렌더링한다': function (browser) {
    const loginPage = browser.page.LoginPage()

    loginPage
      .navigate()
      .waitForElementVisible('@app', 500)
      .assert.visible('@usernameInput')
      .assert.visible('@passwordInput')
      .assert.visible('@submitButton')
      // hidden(): deprecated
      // .assert.hidden('@formError')
      .assert.not.visible('@formError')

    browser.end()
  },
  '존재하지 않는 사용자로 로그인한다': function (browser) {
    const loginPage = browser.page.LoginPage()

    loginPage
      .navigate()
      .login('not-exist', 'incorrect')

    browser.pause(500)

    loginPage
      .assert.visible('@formError')
      .assert.containsText('@formError', 'Invalid credentials')

    browser
      .assert.urlEquals(browser.launchUrl + 'login')
      .end()
  },
  '사용자명으로 로그인한다': function (browser) {
    const loginPage = browser.page.LoginPage()
    const homePage = browser.page.HomePage()

    loginPage
      .navigate()
      .login(data.username, data.password)

    browser.pause(2000)

    homePage
      .navigate()
      .expect.element('@pageTitle').text.to.contain('Home Page')

    browser.end()
  },
  '이메일 주소로 로그인한다': function (browser) {
    const loginPage = browser.page.LoginPage()
    const homePage = browser.page.HomePage()

    loginPage
      .navigate()
      .login(data.emailAddress, data.password)

    browser.pause(2000)

    homePage
      .navigate()
      .expect.element('@pageTitle').text.to.contain('Home Page')

    browser.end()
  }
}
