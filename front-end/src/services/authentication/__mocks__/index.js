export default {
  authenticate (detail) {
    return new Promise((resolve, reject) => {
      (detail.username === 'sckroll' || detail.username === 'sckroll@sckroll.com') &&
      detail.password === 'q1w2e3r4'
        ? resolve({result: 'success'})
        : reject(new Error('Invalid credentials'))
    })
  }
}
