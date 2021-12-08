import { useState } from "react";
import { useParams } from "react-router-dom";
import {
  Container,Navbar,
  Alert,Accordion,Button
} from 'react-bootstrap';
import {FormattedMessage} from "react-intl";

import Locale from "./Locale";
import Authentication from "./Authentication";
import Dialog from "./Dialog";
import Progress from "./Progress";
import {LoginPage,Name,Logout} from "./Authentication";

import "./css/Main.css"

var setMessage;
var setMessageType;
var setErrorDetail;

function logout() {
  Logout();
  return false;
}

const Layout =({children}) => {

  const [messageId,messageIdFunc] = useState("");
  const [messageType,messageTypeFunc] = useState("danger");
  const [detail,detailFunc] = useState("");
  setMessage = messageIdFunc;
  setMessageType = messageTypeFunc;
  setErrorDetail = detailFunc;

  return (<>
<Authentication />
<Locale>

  <Navbar bg="light">
    <Container>
      <Navbar.Brand href={process.env.PUBLIC_URL + "/pages/menu"}>Demo</Navbar.Brand>
      <LoginPage> 
        <div><Name/><br/>
          <Button className="linkText" onClick={logout}>ログアウト</Button>
        </div>
      </LoginPage>
    </Container>
  </Navbar>

  <main>
    {messageId !== "" &&
      <Alert key="1" variant={messageType}> 
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

export function Redirect(path) {
  const l = global.location;
  l.href = process.env.PUBLIC_URL + path;
}

export function ClearMessage() {
  setMessage("");
  setMessageType("danger");
  setErrorDetail("");
}

export function UnknownErrorMessage(detail) {
  let msg = detail;
  if ( detail !== null && typeof detail === "object" ) {
    msg = JSON.stringify(detail);
  }
  setMessage("PRFN00M000");
  setMessageType("danger");
  setErrorDetail(msg);
}

export function WriteMessage(id,type) {
  setMessageType(type);
  setMessage(id);
}

export function WriteErrorMessage(err) {

  var resp = err.response;
  var data = resp.data;

  var id = data.messageID
  var detail = data.result;

  setMessageType("danger");
  setMessage(id);
  if (detail === undefined ) {
      detail = "";
  }
  setErrorDetail(detail);
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

export default Layout;
