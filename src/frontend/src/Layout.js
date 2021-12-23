import { useState } from "react";
import { useParams } from "react-router-dom";
import { Helmet,HelmetProvider } from "react-helmet-async";

import {
  Container,Navbar,
  Alert,Accordion,Button
} from 'react-bootstrap';

import Locale,{Label,Message} from "./Locale";
import Authentication from "./Authentication";
import Dialog from "./Dialog";
import Progress from "./Progress";
import {LoginPage,Name,Logout} from "./Authentication";

import "./css/Main.css"

var setMessageId;
var setMessages;
var setMessageType;
var setErrorDetail;
var changeStateTitle;

function logout() {
  Logout();
  return false;
}

const Layout = ({children}) => {

  const [title,setTitle] = useState("Loaging... PAS")
  const [messageId,messageIdFunc] = useState("");
  const [messageType,messageTypeFunc] = useState("danger");
  const [detail,detailFunc] = useState("");
  const [messages,messagesFunc] = useState([]);

  setMessageId = messageIdFunc;
  setMessageType = messageTypeFunc;
  setErrorDetail = detailFunc;
  setMessages = messagesFunc;
  changeStateTitle = setTitle;

  return (<>
<HelmetProvider>
  <Helmet title={ title } />
</HelmetProvider>
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
        {messages.length === 0 &&
          <Message id={messageId}/> 
        }
        {messages.length !== 0 &&
          <ul>
            {messages.map( (msg) => {
              return <li>{msg}</li>
            })}
          </ul>
        }

        {detail !== "" &&
        <Accordion>
          <Accordion.Item eventKey="0">
            <Accordion.Header className="Layout-SystemDetail">
              <Label id="PRFN00L000"/> 
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

export function Redirect(path,cause) {
  console.log(cause);
  const l = global.location;
  l.href = process.env.PUBLIC_URL + path;
}

export function ClearMessage() {
  setMessageId("");
  setMessages([]);
  setMessageType("danger");
  setErrorDetail("");
}

export function UnknownErrorMessage(detail) {
  let msg = detail;
  if ( detail !== null && typeof detail === "object" ) {
    msg = JSON.stringify(detail);
  }
  setMessageId("PRFN00M000");
  setMessages([]);
  setMessageType("danger");
  setErrorDetail(msg);
}

export function WriteMessage(id,type) {
  setMessageType(type);
  setMessageId(id);
}

export function WriteErrorMessage(err) {

  var resp = err.response;
  var data = resp.data;

  var id = data.messageID
  var msgs = data.messages;
  var detail = data.result;

  setMessageType("danger");
  setMessageId(id);
  setMessages(msgs);

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

export function ChangeTitle(title) {
  changeStateTitle(title + "[人員配置システム]");
}

export default withRouter(Layout);
