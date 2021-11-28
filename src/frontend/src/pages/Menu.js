import React from "react";

import {FormattedMessage} from "react-intl";

import Container from "react-bootstrap/container";
import Card from "react-bootstrap/card";
import Row from "react-bootstrap/row";
import Col from "react-bootstrap/col";
import Button from "react-bootstrap/button";


import "../css/Main.css";
//import {FormattedMessage} from "react-intl";


class Menu extends React.Component {

  handle = () => {
      return false;
  }

  render() {
    return (<>

<Container>
  <Row>
    <Col>
      <Card className="Menu-Card">
        <Card.Body>
          <Card.Title>Function A</Card.Title>
          <Card.Text> 
            <FormattedMessage id="PRFN99M999"/>
          </Card.Text>
          <Button variant="primary">Go A</Button>
        </Card.Body>
      </Card>
    </Col>

    <Col>
      <Card className="Menu-Card">
        <Card.Body>
          <Card.Title>Function B</Card.Title>
          <Card.Text> 
          </Card.Text>
          <Button variant="primary">Go B</Button>
        </Card.Body>
      </Card>
    </Col>

    <Col>
      <Card className="Menu-Card">
        <Card.Body>
          <Card.Title>Function C</Card.Title>
          <Card.Text> 
          </Card.Text>
          <Button variant="primary">Go C</Button>
        </Card.Body>
      </Card>
    </Col>

  </Row>
</Container>

</>)};
}

export default Menu;
