import React from "react";

import { instanceOf } from "prop-types";
import { withCookies,Cookies } from "react-cookie";

import { Redirect } from "./Layout";
import Package from "../package.json";
import Encrypt from "./Encrypt";
import Util from "./Util";

const noAuthenticationURLs = ["/","/error/*","/message/*"];

var inst;
class Authentication extends React.Component {

  static propTypes = {
      cookies : instanceOf(Cookies).isRequired
  };

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
          //TODO アプリに依存する為、書き方を変更
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

  componentDidMount() {
      this.refreshReload = undefined;
      this.refreshTimer();
  }

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

  isAuthPage() {
    const l = global.location;
    let path = l.pathname;
    return !Util.match(path,this.urls);
  }

  save(obj) {
      const {cookies} = this.props;
      var buf = Encrypt.encode(obj);
      cookies.set("session",buf,{path: "/"});
  }

  remove() {
      const {cookies} = this.props;
      cookies.remove("session");
  }

  render() {
    return (<></>);
  }
}

export function ExtendRefresh() {
  inst.refreshTimer();
}

export function Save(obj) {
    inst.save(obj);
    return true;
}

export function CreateJWT() {
    if ( inst.session !== undefined ) {
      return Encrypt.encode(inst.session);
    }
    return "";
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
    if ( !inst.isAuthPage() ) {
      return (<></>);
    }
    return (<>{props.children}</>);
}

export function Logout(props) {
    inst.remove();
    Redirect("/message/Logout","Logout");
    return;
}

export function Name() {
    if ( inst.session !== undefined ) {
        return inst.session.name;
    }
    return "";
}

export default withCookies(Authentication)
