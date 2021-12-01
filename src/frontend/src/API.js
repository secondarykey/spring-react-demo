import axios from 'axios';
import {CreateJWT} from "./Authentication";
import {Show,Hide} from "./Progress";

//import {SystemMessage}  from "./Layout";
class API {

  static createInstance() {
    var jwt = CreateJWT();
    const instance = axios.create({
      headers: {
        'Content-Type': 'application/json',
        'Authorization' : jwt
      }, timeout: 10000,
    });

    return instance;
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

    let inst = this.createInstance();
    Show();
    return await inst.request({
      method : method, url : url, data : data}).then(response => {
        return callback(response)
      }).catch( (error) => {
        return Promise.reject(error)
      }).finally( () => {
        Hide();
      });
  }

  static isUnknownError(err) {
    var resp = err.response;
    if ( resp === undefined ) {
      return true;
    }
    var data = resp.data;
    if ( data === undefined ) {
      return true;
    }
    var msgId = data.messageID;
    if ( msgId === undefined ) {
      return true;
    }
    return false;
  }
}


export default API
