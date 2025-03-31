module.exports = {
  devServer: {
    port: 8027,
    proxy: {
      '/api': {
        target: 'http://localhost:9000',
        changeOrigin: true,
        pathRewrite: { '^/api': '' }
      }
    }
  }
}
