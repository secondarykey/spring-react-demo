import { useState } from "react";
import { useParams } from "react-router-dom";
import { Helmet,HelmetProvider } from "react-helmet-async";

import {
  Container,Navbar,Breadcrumb,
  Alert,Accordion,Button
} from 'react-bootstrap';

import Locale,{Label,Message,GetLabel} from "./Locale";
import Authentication from "./Authentication";
import Dialog from "./Dialog";
import Progress from "./Progress";
import {LoginPage,Name,Logout} from "./Authentication";

import "./css/Main.css"

var setMessageId;
var setMessages;
var setMessageType;
var setErrorDetail;
var changeTitle;
var setBreadcrumbs;

function logout() {
  Logout();
  return false;
}

const Layout = ({children}) => {

  const [messageId,messageIdFunc] = useState("");
  const [messageType,messageTypeFunc] = useState("danger");
  const [messages,messagesFunc] = useState([]);

  const [title,setTitle] = useState("Loading... PAS")
  const [detail,detailFunc] = useState("");
  const [crumbs,crumbsFunc] = useState([]);

  setMessageId = messageIdFunc;
  setMessageType = messageTypeFunc;
  setErrorDetail = detailFunc;
  setMessages = messagesFunc;
  changeTitle = setTitle;
  setBreadcrumbs = crumbsFunc;

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

  <LoginPage> 
    <Breadcrumb className="Layout-Breadcrumbs">
      {crumbs.map( (val,idx) => {
        var active = (idx+1 === crumbs.length);
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
    {messageId !== "" &&
      <Alert key="1" variant={messageType}> 
        {messages.length === 0 &&
          <Message id={messageId}/> 
        }
        {messages.length !== 0 &&
          <ul>
            {messages.map( (msg,idx) => {
              return <li key={idx}>{msg}</li>
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
  if ( path === undefined ) return;
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

export function ChangeTitle(titleId) {
  let name = GetLabel("SYSTEM");
  let page = GetLabel(titleId);
  changeTitle(page + "[" + name + "]");
}

export function SetBreadcrumbs(crumbs) {
  setBreadcrumbs(crumbs);
}
export default withRouter(Layout);
