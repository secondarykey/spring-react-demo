/**
 * @fileoverview 
 * このファイルはユーティリティ用のクラスです。
 * 業務的に共通なロジックをstaticメソッドで記述します。
 */
import Package from "../package.json";

/**
 * ユーティリティクラス
 * @package Utility
 * 
 */
class Util {

  /**
   * サーバのURLを取得
   * <pre>
   * 開発環境下でプロキシを通さずにサーバにアクセスするリソースに使用
   * ※ビルド後はPUBLIC_URLのみでアクセス
   * </pre>
   * @param {string} uri  URI
   * @returns 開発環境下ではプロキシ扱いのURL
   */
  static serverURL(uri) {
    var base = process.env.PUBLIC_URL;
    if ( process.env.NODE_ENV === "development" ) {
      base = Package.proxy + base;
    }
    return base + uri;
  }

  /**
   * フォーマット日付変換
   * @param {string} date - 日付(yyyy-mm-dd形式)
   * @returns Date型
   */
  static formatTime(date) {
    let d = new Date(Date.parse(date));
    return this.zeroPadding(d.getHours(),2) + ":" + 
              this.zeroPadding(d.getMinutes(),2);
  }

  /**
   * Date型フォーマット文字列取得
   * @param {Object} date - Dateオブジェクト
   * @returns yyyy-mm-dd形式の文字列
   */
  static formatDate(date) {
    return date.getFullYear() + "-" + 
              this.zeroPadding((date.getMonth() + 1),2) + "-" + 
              this.zeroPadding(date.getDate(),2);
  }

  /**
   * 現在時刻の文字列
   * @returns 現在時刻のyyyy-mm-dd形式文字列 
   */
  static nowString() {
    return this.formatDate(new Date());
  }

  /**
   * ゼロパティング
   * @param {number} num - 対象数値
   * @param {number} len - 長さ
   * @returns numをlenで０埋めした文字列
   */
  static zeroPadding(num,len) {
    return ( Array(len).join("0") + num ).slice(-1 * len);
  }

  /**
   * Glob(文字列検索)
   * <pre>
   * 対象文字列が"*"を有するパターンとマッチングするかを判定
   * eg.)
   * "*"/match , /test/match -> true
   * </pre>
   * @param {string} pattern - 検索パターン
   * @param {string} v - 対象文字列
   * @returns マッチする場合true
   */
  static matchStringGlob(pattern,v) {

    let strs = pattern.split("*")

    if ( strs.length === 1 ) {
      if ( pattern === v ) {
        return true;
      }
      return false;
    }

    let idx = 0;
    for (let i = 0; i < strs.length; i++ ) {
      let key = strs[i];
      if ( key === "" ) {
        continue;
      }
      let p = v.indexOf(key);
      if ( idx <= p ) {
        idx = p + key.length;
      } else {
          return false;
      }
    }

    return true;
  }

  /**
   * パターン検索
   * <pre>
   * パターン文字列の配列と対象文字列がマッチするかを判定
   * </pre> 
   * @param {string} val 対象文字列
   * @param {Array} patterns パターン配列
   * @returns マッチするパターンが存在する場合、true
   */
  static match(val,patterns) {

    if ( !Array.isArray(patterns) ) {
        return undefined;
    }

    for (let i = 0; i < patterns.length; i++ ) {
      let key = patterns[i];
      if ( this.matchStringGlob(key,val) ) {
          return true;
      }
    }
    return false;
  }
  /**
   * オブジェクト判定
   * @param {any} v - 対象引数
   * @returns  対象引数がobjectの場合true
   */
   static isObject(v) {
    if ( (typeof v) == "object" ) {
      return true;
    }
    return false;
  }
  /**
   * 空判定
   * @param {any} val - 対象オブジェクト
   * @returns 配列の場合、長さまで判定
   */
  static isEmpty(val) {
    if ( val === undefined || val === null ) {
      return true;
    }
    if ( Array.isArray(val) ) {
      if ( val.length === 0 ) {
        return true;
      }
    } else if ( !this.isObject(val) ) {
      if ( val === "" ) return true;
    }
    return false;
  }
}

export default Util;