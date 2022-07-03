/**
 * @fileoverview 
 * Loginページ用のファイル
 * @module Login
 */
import React from "react";
import {
  Container,Row,Col,
  Form,Button
 } from 'react-bootstrap';

import {SelectLanguage, GetLanguage,SetLanguage,
        GetLabel,Label} from '../Locale';
import {WriteErrorMessage,ClearMessage,Redirect}  from "../Layout";
import {Save} from "../Authentication";
import API from "../API";

/**
 * ログインページ
 * 
 * ここに仕様を書いていきます。
 * @example
 * <Login />
 */
class Login extends React.Component {

  /**
   * コンストラクタ
   * <pre>
   * 利用する入力値のrefを生成
   * state.expiryをfalseに設定
   * </pre>
   * @param {object} props - なし
   */
  constructor(props) {

      super(props);

      this.userId = React.createRef(); 
      this.password = React.createRef(); 
      this.newPassword1 = React.createRef(); 
      this.newPassword2 = React.createRef(); 

      this.loginAPI = this.loginAPI.bind(this);

      this.state = { expiry : false,data : [19,20] }
  }

  componentDidMount() {
  }

  /**
   * ログインボタン押下処理
   * <pre>
   * 入力値(ID、パスワード、言語)を元にLoginAPIを呼び出す
   * LoginAPI: /api/v1/login
   * 正常時：
   *   ログイン情報、言語をクッキーを保存しメニューに遷移
   * 異常時
   *   401時：有効期限切れを確認し、有効期限の場合
   *          state.expiryをtrueにする
   *　 その他はエラーメッセージを表示
   * </pre>
   * @param {Event} e - クリックイベント
   * @returns false
   */
  handleLoginClick = (e) => {
    let data = {
        id : this.userId.current.value,
        password : this.password.current.value,
        language : GetLanguage()
    }
    this.loginAPI(data)
  }

  loginAPI(data) {

    ClearMessage();
    API.post("/api/v1/login",
      resp => {
        Save(resp.data.result.user);
        SetLanguage(resp.data.result.language);

        this.view();

        Redirect('/pages/demo/menu');
    },data).catch( (err) => {

      if ( API.isUnknownError(err) ) {
        return;
      }

      let resp = err.response;
      let result = resp.data;
      switch ( resp.status ) {
        case 401:
          let expiry = false;
          let msgId = result.messageID;
          if ( msgId === "PRFN00M102" ) {
             expiry = true
          }
          this.setState({ expiry : expiry });
          break;
        default:
          break;
      }
      WriteErrorMessage(err);
    });

    return false;
  }

  /**
   * パスワード変更クリックイベント
   * <pre>
   * 変更パスワードをAPIに送信し、パスワードを変更する
   * API[/api/v1/password]
   * 正常時:ログイン画面に遷移する
   * 異常時:メッセージを表示する
   * </pre>
   * @param {Event} e - クリックイベント
   * @returns false
   */
  handleUpdateClick = (e) => {

    let new1 = this.newPassword1.current.value;
    let new2 = this.newPassword2.current.value;
    let data = {
        userId      : this.userId.current.value,
        oldPassword : this.password.current.value,
        newPassword1 : new1,
        newPassword2 : new2
    }

    API.put("/api/v1/password",
      resp => {
        Save(resp.data.result.user);
        Redirect('/pages/menu');
    },data).catch( (err) => {
      if ( API.isUnknownError(err) ) {
        return;
      }
      WriteErrorMessage(err);
    });

    return false;
  }

  view = () => {
    alert("not safari");
  }

  /**
   * ログイン画面表示
   * state.expiry: true時にパスワード変更のコンポーネントを表示
   * ログインボタンを非表示、更新キャンセルボタンを表示
   * @returns ログイン画面
   */
  render() {
    let expiry = this.state.expiry;

    return ( <>
<Form>
  <Container>

    <SpaceRow>
      <Form.Group>
        <Form.Label> <Label id="PRFN00L101"/> </Form.Label>
        <Form.Control type="text" placeholder={GetLabel("PRFN00L101")} ref={this.userId} />
      </Form.Group>
    </SpaceRow>

    <SpaceRow>
      <Form.Group>
        <Form.Label> <Label id="PRFN00L102"/> </Form.Label>
        <Form.Control type="password" placeholder="" ref={this.password} />
      </Form.Group>
    </SpaceRow>

    <SpaceRow>
      <SelectLanguage />
    </SpaceRow>

    {/* ログインボタン */}
    {!expiry &&
    <SpaceRow>
      <Button variant="primary" onClick={this.handleLoginClick}> 
        <Label id="PRFN00L104"/>
      </Button>
    </SpaceRow>
    }

    {/* 有効期限フォーム */}
    {expiry &&
<>
    <SpaceRow>
      <Form.Group>
        <Form.Label> <Label id="PRFN00L202"/> </Form.Label>
        <Form.Control type="password" placeholder="Password" ref={this.newPassword1} />
      </Form.Group>
    </SpaceRow>

    <SpaceRow>
      <Form.Group>
        <Form.Label> <Label id="PRFN00L203"/> </Form.Label>
        <Form.Control type="password" placeholder="Password" ref={this.newPassword2} />
      </Form.Group>
    </SpaceRow>

    <SpaceRow>
      <Button variant="primary" onClick={this.handleUpdateClick}> 
        <Label id="PRFN00L204"/>
      </Button>
    </SpaceRow>
</>
    }

  </Container>

</Form>

</>);
  }
}

/**
 * 描画調整用関数
 * <pre>
 * props.childrenをRow,Col内に入れて調整する
 * </pre>
 * @memberof Login
 * @param {object} props - タグ属性(childrenを使用)
 * @returns <Row>タグ
 * @example
 * <SpaceRow>aaa</SpaceRow>と行うとスペースを開けて真ん中に表示する
 * 
 * <Row>
 * <Col></Col>
 * <Col>aaa</Col>
 * <Col></Col>
 * </Row>
 * 
 * という出力
 */
function SpaceRow(props) {
  return (
<Row className="mb-3">
  <Col></Col>
  <Col xs="6">
    {props.children}
  </Col>
  <Col></Col>
</Row>
  );
}

export default Login
