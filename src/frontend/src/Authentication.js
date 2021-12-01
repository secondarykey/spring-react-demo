import React from "react";
import CryptJS from "crypto-js";
import AES from "crypto-js/aes";

import { instanceOf } from "prop-types";
import { withCookies,Cookies } from "react-cookie";

var inst;
class Authentication extends React.Component {

  static propTypes = {
      cookies : instanceOf(Cookies).isRequired
  };

  constructor(props) {

      super(props)
      inst = this;
      this.ignoreURLs = ["/"];

      const {cookies} = this.props;

      let session = cookies.get("session");
      this.session = this.decode(session);

      const l = global.location;
      if ( this.session !== undefined ) {
          var now = new Date()
          var ex = new Date(this.session.exiry)
          if ( now <= ex ) {
              //有効期限切れ
              l.href = "/";
          }
      } else {
          if ( !this.ignoreURLs.includes(l.pathname) ) {
              //認証なしでのアクセス
              l.href = "/";
          }
      }
  }

  save(obj) {
      const {cookies} = this.props;
      var buf = this.encode(obj)
      cookies.set("session",buf,{path: "/"});
  }

  decode(buf) {
    if ( buf === null || buf === "" ) {
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

export function Bearer() {
    var buf;
    if ( inst.session !== undefined ) {
      buf = inst.encode(inst.session);
    }
    return buf;
}

export function Role(props) {
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
    return (<></>)
}

export function Name() {
    return inst.session.name;
}

export default withCookies(Authentication)