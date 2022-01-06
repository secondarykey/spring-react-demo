import React from "react";
import {Container,Row,Col} from "react-bootstrap";

import DateTime from "../components/DateTime";
import Organization from "../components/Organization";

import API from "../../API";
import {WriteErrorMessage} from "../../Layout";
import Util from "../../Util";

class View extends React.Component {

    constructor(props) {
        super(props);
        this.targetDate = React.createRef();
        this.org = React.createRef();
    }

    componentDidMount() {
      let ref = this.targetDate.current;
      let today = new Date();
      ref.set(today);

      let args = {
        "day" : Util.formatDate(today)
      };

      API.post("/api/demo/day/all",resp => {
        var result = resp.data.result;
        let arr = result.days;

        this.targetDate.current.setStyles(arr);
        this.org.current.set(result.org);

      },args).catch( (err) => {
        if ( API.isUnknownError(err) ) {
          return;
        }
        WriteErrorMessage(err);
      })
    }

    render() {
      return <>
        <Container>
          <Row>
            <Col>
              <DateTime type="date" ref={this.targetDate}/>
            </Col>
            <Col>
              <Organization ref={this.org}/>
            </Col>
          </Row>
        </Container>
        </>
    }
}
export default View;