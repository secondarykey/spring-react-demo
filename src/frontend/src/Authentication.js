/**
 * @fileoverview 
 * このファイルは認証機構を提供するモジュールです。
 */
import React from "react";

import { instanceOf } from "prop-types";
import { withCookies,Cookies } from "react-cookie";

import { Redirect } from "./Layout";
import Package from "../package.json";
import Encrypt from "./Encrypt";
import Util from "./Util";

/** 認証なしURLの群 */
const noAuthenticationURLs = ["/","/error/*","/message/*"];

var inst;

/**
 * 認証クラス
 * <pre>
 * クッキーを利用し、認証情報を扱う。暗号化はEncryptにより行う
 * </pre>
 */
class Authentication extends React.Component {

  static propTypes = {
      cookies : instanceOf(Cookies).isRequired
  };

  /**
   * コンストラクタ
   * <pre>
   * 認証ページ時にセッション情報の存在を確認する
   * - セッション自体の存在
   * - 有効期限の確認j
   * </pre>
   * @constructor
   * @param {Cookies} props - なし
   * @example
   * Layoutにより埋め込み
   * 
   * <Authentication/>
   * 
   * 特に描画はない
   */
  constructor(props) {

      super(props)
      inst = this;

      this.urls = [];
      noAuthenticationURLs.forEach( (v) => {
        this.urls.push(process.env.PUBLIC_URL + v);
      })

      const {cookies} = this.props;
      let session = cookies.get("session");
      this.session = Encrypt.decode(session);

      if ( this.session !== undefined ) {
        var now = new Date()
        var ex = new Date(this.session.exiry)
        if ( now <= ex ) {
          //有効期限切れ
          Redirect("/message/expiry","Authentication 有効期限切れ");
          return;
        }
      } else {
        if ( this.isAuthPage() ) {
          //認証なしでのアクセス
          Redirect("/message/noneAuth","Authentication 認証なしアクセス");
          return;
        }
      }

  }

  /**
   * コンポーネントマウント時の処理 
   * <pre>
   * リフレッシュのタイマーを設定する
   * mousemoveにより、マウスが動作している場合はタイマーのリセットを行う
   * 動いている判定は負荷軽減の為、5秒おきに行う。
   * ※リフレッシュタイマー自体はリセット時の説明に記載
   * 非認証ページ時の時は行わない
   * </pre>
   */
  componentDidMount() {

    this.refreshReload = undefined;
    var mouseMove = false;
    var self = this;

    if ( !this.isAuthPage() ) {
      return;
    }
    var refreshFunc = function() {
      if ( mouseMove ) {
        self.refreshTimer();
        mouseMove = false;
        console.log("refresh:" + new Date());
      }
    }

    var once = true;
    document.addEventListener("mousemove",function() {
      mouseMove = true;
      if ( once ) {
        setInterval(refreshFunc,5000);
        once = false;
      }
    });
    this.refreshTimer();
  }

  /**
   * タイマーリフレッシュ
   * <pre>
   * リフレッシュページへのリダイレクトのタイマーを設定する。
   * 既にタイマーが設定されている場合のタイマーは削除
   * タイマーが動作する時刻は外部設定値による。
   * 非認証ページ時は行わない
   * </pre>
   */
  refreshTimer() {
    if ( !this.isAuthPage() ) {
      return;
    }

    if ( this.refreshReload !== undefined ) {
      clearTimeout(this.refreshReload);
    }
    this.refreshReload = setTimeout(function() {
      Redirect("/error/refresh","Authentication refresh")
    },1000 * Package.clientExpiry);
  }

  /**
   * 認証が必要なページかを判定
   * @returns 必要な場合、true
   */
  isAuthPage() {
    const l = global.location;
    let path = l.pathname;
    return !Util.match(path,this.urls);
  }

  /**
   * 認証情報を暗号化してクッキーに設定
   * @param {object} obj - 認証情報
   */
  save(obj) {
      const {cookies} = this.props;
      var buf = Encrypt.encode(obj);
      cookies.set("session",buf,{path: "/"});
  }

  /**
   * 認証情報をクッキーから削除
   */
  remove() {
      const {cookies} = this.props;
      cookies.remove("session",{path:"/"});
  }

  /**
   * レンダリング
   * @returns 空
   */
  render() {
    return (<></>);
  }
}

/**
 * 認証情報保存
 * @param {object} obj - 暗号化対象データ 
 */
export function Save(obj) {
    inst.save(obj);
}

/**
 * JWTデータの生成
 * <pre>
 * クッキー情報を暗号化したデータを返す
 * クッキーが存在しない場合空を返す
 * </pre>
 * @returns 認証情報（暗号化データ）
 */
export function CreateJWT() {
    if ( inst.session !== undefined ) {
      return Encrypt.encode(inst.session);
    }
    return "";
}

/**
 * ロール時表示切替
 * <pre>
 * ロールによる表示切り替えを行う
 * TODO 実際には権限を作成して、オブジェクトIDを指定
 * </pre>
 * @param {object} props - permission:オブジェクトID
 * @returns 権限ありの場合のみ子を表示
 */
export function Role(props) {
    if ( inst.session !== undefined ) {
        var role = inst.session.role;
        if ( Array.isArray(props.permission) ) {
            if ( props.permission.includes(role) ) {
                return (<>{props.children}</>);
            }
        } else if ( Array.isArray(props.ignore) ) {
            if ( !props.permission.includes(role) ) {
                return (<>{props.children}</>);
            }
        }
    } 
    return (<></>)
}

/**
 * ログイン状態表示
 * <pre>
 * ログイン状態の場合、子の表示を行う
 * notを指定した場合、逆になる。
 * </pre>
 * @param {object} props - not:認証有無
 * @returns ありなしの判定により、子かもしくは空
 * @example
 * <LoginPage>aaa</LoginPage>
 * とした場合、ログイン状態の場合のみaaaを表示
 * 
 * <LoginPage not="true">aaa</LoginPage>
 * とした場合、ログインをしてない場合のみaaaを表示
 */
export function LoginPage(props) {
    if ( !inst.isAuthPage() ) {
      return (<></>);
    }
    return (<>{props.children}</>);
}

/**
 * クッキー情報の削除
 * @param {object} props - 指定なし
 */
export function Remove(props) {
  inst.remove();
}

/**
 * ログアウトページへの遷移
 * @param {object} props - 指定なし
 */
export function Logout(props) {
    Redirect("/message/Logout","Logout");
}

/**
 * ユーザ名称を取得
 * @returns ユーザ名称
 */
export function Name() {
    if ( inst.session !== undefined ) {
        return inst.session.name;
    }
    return "";
}

export default withCookies(Authentication)
