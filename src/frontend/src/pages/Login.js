import React from "react";
import {
  Container,Row,Col,
  Alert,Form,Button
 } from 'react-bootstrap';

import {SelectLanguage,
        GetLabel,Label,Message} from '../Locale';

import {WriteErrorMessage,ClearMessage,Redirect,ChangeTitle}  from "../Layout";
import {Save} from "../Authentication";
import API from "../API";

class Login extends React.Component {

  constructor(props) {

      super(props);

      this.userId = React.createRef(); 
      this.password = React.createRef(); 
      this.oldPassword = React.createRef(); 
      this.newPassword1 = React.createRef(); 
      this.newPassword2 = React.createRef(); 

      this.state = { expiry : false, messageId : "" }
      ClearMessage();
      ChangeTitle("ログイン")
  }


  handleLoginClick = (e) => {

    ClearMessage();

    let data = {
        id : this.userId.current.value,
        password : this.password.current.value,
        msgId : ""
    }

    API.post("/api/v1/login",
      resp => {
        Save(resp.data.result.user);
        Redirect('/pages/menu');
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
          this.setState({ expiry : expiry, messageId : msgId });
          break;
        default:
          WriteErrorMessage(err);
          break;
      }
    });

    return false;
  }

  handleUpdateClick = (e) => {

    let new1 = this.newPassword1.current.value;
    let new2 = this.newPassword2.current.value;

    if ( new1 !== new2 ) {
        this.setState({ messageId : "PRFN00M201" });
        return;
    }

    this.setState({ messageId : "" });
    let data = {
        userId      : this.userId.current.value,
        oldPassword : this.oldPassword.current.value,
        newPassword : new1
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

  render() {

    const expiry = this.state.expiry;
    const msgId = this.state.messageId;

    return ( <>

<Form>
  <Container>
    <SpaceRow>
      <Form.Group>
        <Form.Label> <Label id="PRFN00L101"/> </Form.Label>
        <Form.Control type="email" placeholder={GetLabel("PRFN00L101")} ref={this.userId} />
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

    {/*メッセージ表示 */}
    {msgId !== "" &&
    <SpaceRow>
      <Alert key="1" variant="danger"> <Message id={msgId}/> </Alert>
    </SpaceRow>
    }

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
        <Form.Label> <Label id="PRFN00L201"/> </Form.Label>
        <Form.Control type="password" placeholder="Password" ref={this.oldPassword} />
      </Form.Group>
    </SpaceRow>

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
