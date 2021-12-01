import axios from 'axios';
import {Bearer} from "./Authentication";
import {Show,Hide} from "./Progress";

//import {SystemMessage}  from "./Layout";
class API {

  static createInstance() {
    var bearer = Bearer();
    const instance = axios.create({
      headers: {
        'Content-Type': 'application/json',
        'Authorization' : "Bearer " + bearer
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
    //ここでシステムメッセージを設定
    return Promise.reject(config)
  }

  static requestSuccess(config) {
    //request成功した時の処理
    //console.log('// REQUEST SUCCESS', config)
    return config
  }

  static async get(url, callback, data) {
    return await this.caller("GET",url,callback,data);
  }

  static async post(url, callback, data) {
    return await this.caller("POST",url,callback,data);
  }

  static async put(url, callback, data) {
    return await this.caller("PUT",url,callback,data);
  }

  static async delete(url, callback, data) {
    return await this.caller("DELETE",url,callback,data);
  }
   
  static async caller(method,url, callback, data) {
    //TODO axios がdelete,put,getでbody送信を許してない為、requestに変更する
    let inst = this.createInstance();

    Show();
    return await inst.request({
      method : method, url : url, data : data}).then(response => {
        return callback(response)
      }).catch( (error) => {

        //TODO 例外時に業務側で共通化を行う方法を模索中

        var w = error;
        var resp = w.response;
        if ( resp === undefined ) {
            console.log("resp is undefind");
            w.response = {};
            resp = w.response;
        }
        var data = resp.data;
        if ( data === undefined ) {
          console.log("data is undefind");
          w.response.data = {};
          data = w.response.data;
        }
        var msgId = data.messageId;

        if ( msgId === undefined ) {
          console.log("messageId is undefind");
          w.response.data.messageId = "PRFN00M000";
        }
        console.log(w);
        return Promise.reject(w)
      }).finally( () => {
        Hide();
      });
  }
}
export default API
