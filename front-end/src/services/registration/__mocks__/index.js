export default {
  register (detail) {
    return new Promise((resolve, reject) => {
      detail.emailAddress === 'sckroll@sckroll.com'
        ? resolve({ result: 'success' })
        : reject(new Error('User already exists'))
    })
  }
}
