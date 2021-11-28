import axios from 'axios';

class API {

  static createInstance() {
    const instance = axios.create({
      headers: {
        'Content-Type': 'application/json',
      }, timeout: 10000,
    });

    instance.interceptors.response.use(
      config => this.requestSuccess(config),
      config => this.requestFailure(config),
    )
    return instance;
  }

  static requestFailure(config) {
    //request失敗した時の処理
    console.log('// REQUEST FAILURE', config)
    return Promise.reject(config)
  }

  static requestSuccess(config) {
    //request成功した時の処理
    //console.log('// REQUEST SUCCESS', config)
    return config
  }

  static async get(url, callback, data) {
    const instance = this.createInstance()
    return await this.caller(url,callback,data,instance.get);
  }

  static async post(url, callback, data) {
    const instance = this.createInstance()
    return await this.caller(url,callback,data,instance.post);
  }

  static async put(url, callback, data) {
    const instance = this.createInstance()
    return await this.caller(url,callback,data,instance.put);
  }

  static async delete(url, callback, data) {
    const instance = this.createInstance()
    return await this.caller(url,callback,data,instance.delete);
  }
   
  static async caller(url, callback, data,fn) {
    //TODO axios がdelete,put,getでbody送信を許してない為、requestに変更する
    return await fn(url, data)
      .then(response => {
        return callback(response)
      })
      .catch( (error) => {
        return Promise.reject(error)
      })
  }
}
export default API
