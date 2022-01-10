import axios from 'axios';
import {CreateJWT} from "./Authentication";
import {Show,Hide} from "./Progress";
import {UnknownErrorMessage}  from "./Layout";
import { GetLanguage } from './Locale';

class API {

  static mockMode = false;

  static async testMode() {
    //mockMode = true;
  }
  static async setMock(mock,reject = false) {
    //mockMode = true;
  }

  static createInstance() {
    var jwt = CreateJWT();
    const instance = axios.create({
      headers: {
        'Content-Type': 'application/json',
        'Authorization' : jwt,
        'Language' : GetLanguage()
      }, timeout: 10000,
    });

    console.log(GetLanguage());

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

    var ctx = process.env.PUBLIC_URL + url;
    return await inst.request({
      method : method, url : ctx, data : data}).then(response => {
        return callback(response)
      }).catch( (error) => {
        return Promise.reject(error)
      }).finally( () => {
        Hide();
      });
  }

  static isUnknownError(err) {
    if ( err !== undefined ) {
      var resp = err.response;
      if ( resp !== undefined ) {
        var data = resp.data;
        if ( data.messageID !== undefined ) {
          return false;
        }
      }
    }

    UnknownErrorMessage(err);
    return true;
  }
}
export default API
