const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  configureWebpack: {
    performance: {
      hints:false
    }
  },
})
chainWebpack = config => {
  config.plugin('html').tap(args => {
    // 添加初始化变量
    args[0].title = "辣椒粉的日历";
    return args;
  })
}
