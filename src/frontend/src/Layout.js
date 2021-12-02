import { useState } from "react";
import Navbar    from 'react-bootstrap/Navbar';
import Container from 'react-bootstrap/Container';

import {FormattedMessage} from "react-intl";
import Alert     from 'react-bootstrap/Alert';
import Accordion from 'react-bootstrap/Accordion';
import Button from 'react-bootstrap/Button';

import Locale from "./Locale";
import Dialog from "./Dialog";
import Progress from "./Progress";
import {LoginPage,Name,Logout} from "./Authentication";

import "./css/Main.css"

var setMessage;
var setErrorDetail;

function logout() {
  Logout();
  global.location.href = "/error/LogoutID";
  return false;
}

const Layout =({children}) => {

  const [messageId,messageIdFunc] = useState("");
  const [detail,detailFunc] = useState("");
  setMessage = messageIdFunc;
  setErrorDetail = detailFunc;
  return (<>

<Locale>

  <Navbar bg="light">
    <Container>
      <Navbar.Brand href="/pages/menu">Demo</Navbar.Brand>
      <LoginPage> 
        <div><Name/><br/>
          <Button className="linkText" onClick={logout}>ログアウト</Button>
        </div>
      </LoginPage>
    </Container>
  </Navbar>

  <main>
    {messageId !== "" &&
      <Alert key="1" variant="danger"> 
        <FormattedMessage id={messageId}/> 
        {detail !== "" &&

        <Accordion>
          <Accordion.Item eventKey="0">
            <Accordion.Header className="Layout-SystemDetail">
              <FormattedMessage id="PRFN00L000"/> 
            </Accordion.Header>
            <Accordion.Body>{detail}</Accordion.Body>
          </Accordion.Item>
        </Accordion>

        }
      </Alert>
    }
    {children}
  </main>

  <Dialog/>
  <Progress/>

</Locale>

</>)}

export function ClearMessage() {
  setMessage("");
  setErrorDetail("");
}

export function UnknownErrorMessage(detail) {
  let msg = detail;
  if ( detail !== null && typeof detail === "object" ) {
    msg = JSON.stringify(detail);
  }
  setMessage("PRFN00M000");
  setErrorDetail(msg);
}

export function WriteMessage(id) {
  setMessage(id);
}

export function WriteErrorMessage(err) {
  var resp = err.response;
  var data = resp.data;

  console.log(data);
  var id = data.messageID
  var detail = data.result;

  setMessage(id);
  if (detail === undefined ) {
      detail = "";
  }
  setErrorDetail(detail);
}

export default Layout;
