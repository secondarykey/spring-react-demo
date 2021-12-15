import React from "react";
import { 
  Container,
  Card,Row,Col,Button
} from "react-bootstrap";

import {Role} from "../Authentication";
import {Redirect} from "../Layout";

import "../css/Main.css";

class Menu extends React.Component {

  gotoPlan = () => {
    Redirect("/pages/plan/");
    return;
  }

  gotoDate = () => {
    Redirect("/pages/date/");
    return;
  }

  gotoToDo = () => {
    Redirect("/pages/todo/");
    return;
  }

  gotoPaging = () => {
    Redirect("/pages/paging/");
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
          <Card.Title>Sample Plan</Card.Title>
          <Card.Text> 
          </Card.Text>
          <Button variant="primary" onClick={this.gotoPlan}>Go Plan </Button>
        </Card.Body>
      </Card>
    </Col>

    <Col>
      <Card className="Menu-Card">
        <Card.Body>
          <Card.Title>Sample Paging</Card.Title>
          <Card.Text> 
          </Card.Text>
          <Button variant="primary" onClick={this.gotoPaging}>Go Database Date</Button>
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
