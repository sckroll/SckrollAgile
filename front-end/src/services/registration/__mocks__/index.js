export default {
  register (detail) {
    return new Promise((resolve, reject) => {
      detail.emailAddress === 'sckroll@local'
        ? resolve({ result: 'success' })
        : reject(new Error('The user already exists'))
    })
  }
}
