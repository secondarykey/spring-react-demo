import React,{createRef} from "react";
import {
    Row,Col,
    Button, Modal, Form
} from "react-bootstrap";

import DateTime from "../../components/DateTime";
import API from "../../../API";
import {ClearMessage, WriteErrorMessage} from "../../../Layout";

export function ShowInput(date) {
    inst.handleShow(date);
}

var inst;
class PlanInputDialog extends React.Component {
    constructor(props) {
        super(props);
        inst = this;

        this.state = {
            targetDate : undefined,
			show : false,
            name : "計画 詳細",
            start: "0 8:00",
            end: "0 12:00"
		}

        this.start = createRef(); 
        this.end = createRef(); 
        this.name = createRef(); 

        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.handleCommit = this.handleCommit.bind(this);
    }

	handleShow = (date) => {
        ClearMessage();
		this.setState({
            show:true,
            targetDate:date
        });
	}

	handleClose = () => {
		this.setState({show:false})
	}

	handleCommit = () => {

        let obj = {
            place : 1,
            name : this.name.current.value,
            date : this.state.targetDate,
            start : this.start.current.get(),
            end : this.end.current.get()
        }

        API.post("/api/demo/plan/edit", resp => {
          console.log(resp)
          //API呼び出し
		  this.handleClose();
        },obj).catch( (err) => {
          if ( API.isUnknownError(err) ) {
            return;
          }
          WriteErrorMessage(err);
        });

	}

    render() {
        return (<>
<Modal show={this.state.show} onHide={this.handleClose}>
  <Modal.Header closeButton>
	  <Modal.Title>計画入力 {this.state.targetDate}</Modal.Title>
  </Modal.Header>
  <Modal.Body>
      <Row>
      <Col>名称</Col>
      <Col><Form.Control defaultValue={this.state.name} ref={this.name}></Form.Control></Col>
      </Row>
      <Row>
      <Col>開始</Col>
      <Col><DateTime type="time" value={this.state.start} ref={this.start}/></Col>
      </Row>
      <Row>
      <Col>終了</Col>
      <Col><DateTime type="time" value={this.state.end} ref={this.end}/></Col>
      </Row>
  </Modal.Body>
  <Modal.Footer>
    <Button variant="primary" onClick={this.handleCommit}>
        作成
    </Button>
  </Modal.Footer>
</Modal>
        </>)
    }
}

export default PlanInputDialog;