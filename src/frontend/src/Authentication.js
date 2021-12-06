import React from "react";
import CryptJS from "crypto-js";
import AES from "crypto-js/aes";

import { instanceOf } from "prop-types";
import { withCookies,Cookies } from "react-cookie";

import { Redirect } from "./Layout";

var inst;
class Authentication extends React.Component {

  static propTypes = {
      cookies : instanceOf(Cookies).isRequired
  };

  constructor(props) {

      super(props)
      inst = this;

      this.noAuthenticationURLs = ["/","/error/"];

      const {cookies} = this.props;
      let session = cookies.get("session");
      this.session = this.decode(session);

      if ( this.session !== undefined ) {
        var now = new Date()
        var ex = new Date(this.session.exiry)
        if ( now <= ex ) {
          //TODO アプリに依存する為、書き方を変更
          //有効期限切れ
          Redirect("/message/Logout");
        }
      } else {
        if ( !this.noAuth() ) {
          //認証なしでのアクセス
          Redirect("/message/Logout");
        }
      }
  }

  noAuth() {
      //TODO なんかだめ
    const l = global.location;
    let path = l.pathname;
    if ( path === "/" ) {
        return true;
    }

    if ( path.indexOf("/error/") !== -1 ) {
        return true;
    }
    if ( path.indexOf("/message/") !== -1 ) {
        return true;
    }

    return false;
  }

  save(obj) {
      const {cookies} = this.props;
      var buf = this.encode(obj)
      cookies.set("session",buf,{path: "/"});
  }

  remove() {
      const {cookies} = this.props;
      cookies.remove("session");
  }

  decode(buf) {
    if ( buf === undefined || buf === null || buf === "" ) {
        return undefined;
    }
    var bytes = AES.decrypt(buf,this.SECRETKEY);
    var obj = JSON.parse(bytes.toString(CryptJS.enc.Utf8));
    return obj;
  }

  encode(obj) {
    if ( obj === undefined ) {
        return undefined;
    }
    var buf = JSON.stringify(obj);
    var ret = AES.encrypt(buf,this.SECRETKEY);
    return ret.toString();
  }

  render() {
    return (<></>);
  }

  SECRETKEY = 'aes-256-cbc-text';
}

export function Save(obj) {
    inst.save(obj);
    return true;
}

export function CreateJWT() {
    var buf;
    if ( inst.session !== undefined ) {
      buf = inst.encode(inst.session);
    }
    return buf;
}

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

export function LoginPage(props) {
    if ( inst.noAuth() ) {
      return (<></>);
    }
    return (<>{props.children}</>);
}

export function Logout(props) {
    inst.remove();
    Redirect("/message/Logout");
    return;
}

export function Name() {
    if ( inst.session !== undefined ) {
        return inst.session.name;
    }
    return "";
}

export default withCookies(Authentication)
