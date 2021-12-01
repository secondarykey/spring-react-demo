import { useState } from "react";
import Navbar    from 'react-bootstrap/Navbar';
import Container from 'react-bootstrap/Container';

import {FormattedMessage} from "react-intl";
import Alert     from 'react-bootstrap/Alert';
import Accordion from 'react-bootstrap/Accordion';

import Locale from "./Locale";
import Dialog from "./Dialog";
import {Name} from "./Authentication";

var setMessage;
var setErrorDetail;

const Layout =({children}) => {

  const [messageId,messageIdFunc] = useState("");
  const [detail,detailFunc] = useState("");
  setMessage = messageIdFunc;
  setErrorDetail = detailFunc;
  return (<>

<Locale>

  <Navbar bg="light">
    <Container>
      <Navbar.Brand href="/">Demo</Navbar.Brand>
      <Name/>
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

</Locale>

</>)}

export function SystemMessage(id,detail) {
    setMessage(id);
    if (detail === undefined ) {
        detail = "";
    }
    setErrorDetail(detail);
}

export default Layout;
