import React from "react";

import Container from "react-bootstrap/container";
import Card from "react-bootstrap/card";
import Row from "react-bootstrap/row";
import Col from "react-bootstrap/col";
import Button from "react-bootstrap/button";

import {Role} from "../Authentication";
import {Redirect} from "../Layout";

import "../css/Main.css";

class Menu extends React.Component {

  handle = () => {
      return false;
  }

  gotoToDo = () => {
    Redirect("/pages/todo");
  }

  render() {
    return (<>

<Container>
  <Row>
    <Col>
      <Card className="Menu-Card">
        <Card.Body>
          <Card.Title>Sample TODO</Card.Title>
          <Card.Text> 
            Card Text
          </Card.Text>

          <Button variant="primary" onClick={this.gotoToDo}>Go SampleToDo </Button>

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

    <Role permission={["admin"]}>
      <Col>
      <Card className="Menu-Card">
        <Card.Body>
          <Card.Title>Function Admin</Card.Title>
          <Card.Text> 
          </Card.Text>
          <Button variant="primary">Go C</Button>
        </Card.Body>
      </Card>
    </Col>
    </Role>

  </Row>
</Container>

</>)};
}

export default Menu;
