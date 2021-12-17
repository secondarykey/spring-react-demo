import React from "react";
import { 
  Container,
  Card,Row,Col,Button
} from "react-bootstrap";

import {Role} from "../Authentication";
import {Redirect,ChangeTitle} from "../Layout";

import "../css/Main.css";

class Menu extends React.Component {

  constructor(props) {
    super(props)
    ChangeTitle("メニュー")
  }

  handle = () => {
      return false;
  }

  gotoDate = () => {
    Redirect("/pages/date/");
    return;
  }

  gotoToDo = () => {
    Redirect("/pages/todo/");
    return;
  }

  gotoFloat = () => {
    Redirect("/pages/floating/");
    return;
  }

  gotoDialogs = () => {
    Redirect("/pages/dialogs/");
    return;
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
          <Card.Title>Sample Date</Card.Title>
          <Card.Text> 
          </Card.Text>
          <Button variant="primary" onClick={this.gotoDate}>Go Sample Date </Button>
        </Card.Body>
      </Card>
    </Col>

    <Col>
      <Card className="Menu-Card">
        <Card.Body>
          <Card.Title>Sample Float</Card.Title>
          <Card.Text> 
          </Card.Text>
          <Button variant="primary" onClick={this.gotoFloat}>Go Sample Float </Button>
        </Card.Body>
      </Card>
    </Col>

    <Col>
      <Card className="Menu-Card">
        <Card.Body>
          <Card.Title>Sample Dialog</Card.Title>
          <Card.Text> 
          </Card.Text>
          <Button variant="primary" onClick={this.gotoDialogs}>Go Sample Dialog</Button>
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
