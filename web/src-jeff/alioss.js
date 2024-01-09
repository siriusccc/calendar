var OSS = require('ali-oss');
export function client() {
  var client = new OSS({
    region: 'beijing',//填写Bucket所在地域
    accessKeyId: 'LTAI5tFoKjjj7seCLtdV8BAZ',  //这自己keyId
    accessKeySecret: 'WbChmtcR23qDIaxLVJPYcNLKxprx83', //这里写自己的key密码 
    bucket: 'jeff-pic' ,// 填写Bucket名称。
	secure:true,
  })  //后端提供数据 
  return client
}
/**
 * 生成随机文件名称
 * 规则八位随机字符，加下划线连接时间戳
 */
export const getFileNameUUID = () => {
  function rx() {
    return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1)
  }
  return `${+new Date()}_${rx()}${rx()}`
}