module.exports = {
  devServer: {
    port: 3000,
    proxy: {
      // '/api/' 경로로 시작하는 요청은 백엔드로 연결
      '/api/*': {
        target: 'http://localhost:8080'
      }
    }
  },
  configureWebpack: {
    entry: {
      app: './src/main.js',
      style: [
        'bootstrap/dist/css/bootstrap.min.css'
      ]
    }
  }
}
