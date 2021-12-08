import React from "react";
import {Button,Modal} from "react-bootstrap";

export function ShowInput(date) {
    inst.handleShow(date);
}

var inst;
export class PlanInputDialog extends React.Component {
    constructor(props) {
        super(props);
        inst = this;
        this.state = {
            targetDate : undefined,
			show : false
		}
    }

	handleShow = (date) => {
		this.setState({
            show:true,
            targetDate:date
        });
	}

	handleClose = () => {
		this.setState({show:false})
	}

	handleCommit = () => {
        //API呼び出し
		this.handleClose();
	}

    render() {
        return (<>
<Modal show={this.state.show} onHide={this.handleClose}>
  <Modal.Header closeButton>
	  <Modal.Title>計画入力</Modal.Title>
  </Modal.Header>
  <Modal.Body>
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