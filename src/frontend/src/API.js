/**
 * @fileoverview 
 * サーバサイド呼び出し用API群
 */
import axios from 'axios';
import { CreateJWT } from "./Authentication";
import { Show, Hide } from "./Progress";
import { UnknownErrorMessage } from "./Layout";
import { GetLanguage } from './Locale';

/**
 * サーバサイド呼び出しクラス
 */
class API {

  static mockMode = false;

  static async testMode() {
    //mockMode = true;
  }
  static async setMock(mock, reject = false) {
    //mockMode = true;
  }

  /**
   * インスタンス生成
   * <pre>
   * クッキーからJWTを取得し、
   * ヘッダにJWT、Languageをヘッダに設定
   * </pre>
   * @private
   * @returns アクセス用のインスタンス
   */
  static createInstance() {
    var jwt = CreateJWT();
    const instance = axios.create({
      headers: {
        'Content-Type': 'application/json',
        'Authorization': jwt,
        'Language': GetLanguage()
      }, timeout: 10000,
    });
    return instance;
  }

  /**
   * POST呼び出し
   * @param {string} url - URL
   * @param {function} callback - 正常時関数
   * @param {object} data - 引数
   * @returns {Promise} 
   */
  static async post(url, callback, data) {
    return await this.caller("POST", url, callback, data);
  }

  /**
   * PUT呼び出し
   * @param {string} url - URL
   * @param {function} callback - 正常時関数
   * @param {object} data - 引数
   * @returns {Promise} 
   */
  static async put(url, callback, data) {
    return await this.caller("PUT", url, callback, data);
  }

  /**
   * PATCH呼び出し
   * @param {string} url - URL
   * @param {function} callback - 正常時関数
   * @param {object} data - 引数
   * @returns {Promise} 
   */
  static async patch(url, callback, data) {
    return await this.caller("PATCH", url, callback, data);
  }

  /**
   * DELETE呼び出し
   * @param {string} url - URL
   * @param {function} callback - 正常時関数
   * @param {object} data - 引数
   * @returns {Promise} 
   */
  static async delete(url, callback, data) {
    return await this.caller("DELETE", url, callback, data);
  }

  /**
   * サーバサイド呼び出し
   * <pre>
   * 指定したURLに指定したメソッドでAPIとして呼び出す
   * 認証用のインスタンスを準備し、プログレスバー（Progress.Show()）を呼び出す
   * リクエストは正常系の場合コールバックを呼び出し、
   * 異常時はPromiseをRejectする。
   * finallyとしてProgress.Hide()を必ず呼び出す
   * </pre>
   * @private
   * @param {string} method - リクエスト時のHTTPメソッド名
   * @param {string} url - URL
   * @param {function} callback - 正常時関数
   * @param {object} data - 引数
   * @returns {Promise} 
   */
  static async caller(method, url, callback, data) {

    let inst = this.createInstance();
    Show();

    var ctx = process.env.PUBLIC_URL + url;
    return await inst.request({
      method: method, url: ctx, data: data
    }).then(response => {
      return callback(response)
    }).catch((error) => {
      return Promise.reject(error)
    }).finally(() => {
      Hide();
    });
  }

  /**
   * 正常エラー判定
   * <pre>
   * エラーがAPI時の形式として正常かを判定する。
   * ※主に予期しない例外(タイムアウトエラーなど)にtrueになる
   * 
   * true時はLayout.UnknownErrorMessage()を呼び出し、
   * 異常メッセージを表示
   * </pre>
   * @param {object} err - エラー用のオブジェクト
   * @returns エラーが認識できる場合false
   */
  static isUnknownError(err) {
    if (err !== undefined) {
      var resp = err.response;
      if (resp !== undefined) {
        var data = resp.data;
        if (data.messageID !== undefined) {
          return false;
        }
      }
    }

    UnknownErrorMessage(err);
    return true;
  }

  static getSync(uri) {
    //var ctx = Util.serverURL(uri);
    var ctx = process.env.PUBLIC_URL + uri;

    let xhr = new XMLHttpRequest();
    xhr.open('GET', ctx,false);
    xhr.send(null);
    return xhr.responseText;
  }
}
export default API
