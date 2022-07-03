/**
 * @fileoverview 
 * このファイルは共通的なレイアウトを提供します。
 * データファイルは"locale-data"に"言語コード".jsonで存在します。
 * @module Layout
 */
import React from "react";
import { useParams } from "react-router-dom";
import { Helmet, HelmetProvider } from "react-helmet-async";
import {
  Container, Navbar, Breadcrumb,
  Alert, Accordion, Button
} from 'react-bootstrap';

import Locale, { Label, Message, GetLabel } from "./Locale";
import Authentication from "./Authentication";
import Dialog from "./Dialog";
import Progress from "./Progress";
import { LoginPage, Name, Logout } from "./Authentication";

import "./css/Main.css"

var inst;

/**
 * レイアウトコンポーネント
 * <pre>
 * ページ共通のレイアウトと機能を提供する
 * タイトル変更 -> Helmetを利用
 * 認証機能 -> Authenticationを利用
 * 国際化機能 -> Localeを利用
 * ダイアログ（問い合わせ、情報）-> Dialogを利用
 * プログレス -> Progressを利用
 * </pre>
 * @param {element} children - 子ページ
 * @returns 共通レイアウトを持つページ
 */
class Layout extends React.Component {

  constructor(props) {
    super(props);

    inst = this;

    this.children = props.children;

    this.state = {
      messageId:"",
      messageType:"danger",
      messages:[],
      messageDetail:"",
      title:"Loading...",
      crumbs:[]
    }
  }

  setMessageId(msg) {
    this.setState({messageId:msg});
  }

  setMessages(msgs) {
    this.setState({messages:msgs});
  }

  setMessageType(t) {
    this.setState({messageType:t});
  }

  setErrorDetail(detail) {
    this.setState({messageDetail:detail});
  }

  changeTitle(title) {
    this.setState({title:title});
  }

  setBreadcrumbs(crumbs) {
    this.setState({crumbs:crumbs});
  }

  render() {

    return (<>

      <HelmetProvider>
        <Helmet title={this.state.title}> </Helmet>
      </HelmetProvider>

      <Authentication />

      <Locale>

        <Navbar bg="light">
          <Container>
            <Navbar.Brand href={process.env.PUBLIC_URL + "/pages/menu"}>Demo</Navbar.Brand>
            <LoginPage>
              <div><Name /><br />
                <Button className="linkText" onClick={logout}>ログアウト</Button>
              </div>
            </LoginPage>
          </Container>
        </Navbar>

        <LoginPage>
          <Breadcrumb className="Layout-Breadcrumbs">
            {this.state.crumbs.map((val, idx) => {
              var active = (idx + 1 === this.state.crumbs.length);
              var name = GetLabel(val.id);
              var link = val.link;
              return (
                <Breadcrumb.Item active={active} key={"breadcrumbs-" + idx} href="#"
                  onClick={() => Redirect(link)}>{name}</Breadcrumb.Item>
              )
            })}
          </Breadcrumb>
        </LoginPage>

        <main>
          {this.state.messageId !== "" &&
            <Alert key="1" variant={this.state.messageType}>
              {this.state.messages.length === 0 &&
                <Message id={this.state.messageId} />
              }
              {this.state.messages.length !== 0 &&
                <ul>
                  {this.state.messages.map((msg, idx) => {
                    return <li key={idx}>{msg}</li>
                  })}
                </ul>
              }

              {this.state.messageDetail !== "" &&
                <Accordion>
                  <Accordion.Item eventKey="0">
                    <Accordion.Header className="Layout-SystemDetail">
                      <Label id="PRFN00L000" />
                    </Accordion.Header>
                    <Accordion.Body>{this.state.messageDetail}</Accordion.Body>
                  </Accordion.Item>
                </Accordion>

              }
            </Alert>
          }

          {this.children}

        </main>

        <Dialog />
        <Progress />

      </Locale>

    </>);
  }
}

/**
 * ログアウト
 * <pre>
 * 認証コンポーネントのログアウト機能を呼び出す
 * </pre>
 */
 function logout() {
  Logout();
}

/**
 * リダイレクト
 * <pre>
 * PUBLIC_URLを付与したpathにリダイレクトする
 * </pre>
 * @param {string} path - URL
 */
export function Redirect(path) {
  if (path === undefined) return;
  const l = global.location;
  l.href = process.env.PUBLIC_URL + path;
}

/**
 * メッセージ表示位置をクリア
 */
export function ClearMessage() {
  inst.setMessageId("");
  inst.setMessages([]);
  inst.setMessageType("danger");
  inst.setErrorDetail("");
}

/**
 * 不明エラーメッセージ表示
 * <pre>
 * 不明のエラーオブジェクトを元に不明エラーを作成し、表示する
 * </pre>
 * @param {object} detail - エラーオブジェクト
 */
export function UnknownErrorMessage(detail) {
  let msg = detail;
  if (detail !== null && typeof detail === "object") {
    msg = JSON.stringify(detail);
  }
  inst.setMessageId("PRFN00M000");
  inst.setMessages([]);
  inst.setMessageType("danger");
  inst.setErrorDetail(msg);
}

/**
 * メッセージ表示
 * @param {string} id - メッセージID
 * @param {string} type - タイプ（bootstrapのAlertの属性）
 */
export function WriteMessage(id, type) {
  inst.setMessageType(type);
  inst.setMessageId(id);
}

/**
 * エラーメッセージ表示
 * <pre>
 * 定形のエラーオブジェクト(例外データ)からエラーを表示する
 * </pre>
 * @param {object} err - エラーオブジェクト
 */
export function WriteErrorMessage(err) {

  var resp = err.response;
  var data = resp.data;

  var id = data.messageID
  var msgs = data.messages;
  var detail = data.result;

  inst.setMessageType("danger");
  inst.setMessageId(id);
  inst.setMessages(msgs);

  if (detail === undefined) {
    detail = "";
  }
  inst.setErrorDetail(detail);
}

export const withRouter = WrappedComponent => props => {
  const params = useParams();
  return (
    <WrappedComponent
      {...props}
      params={params}
    />
  );
};

/**
 * タイトル変更
 * <pre>
 * HTMLタイトルを変更
 * </pre>
 * @param {string} titleId タイトルID
 */
export function ChangeTitle(titleId) {
  //TODO 実際のIDに変更
  let name = GetLabel("SYSTEM");
  let page = GetLabel(titleId);
  inst.changeTitle(page + "[" + name + "]");
}

/**
 * パンくずデータ設定
 * @param {Array} crumbs パンくずデータ(id,link)
 */
export function SetBreadcrumbs(crumbs) {
  inst.setBreadcrumbs(crumbs);
}
export default withRouter(Layout);
