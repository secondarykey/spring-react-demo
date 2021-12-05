import React from "react";

import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

var inst;

export function ShowDialog(title,message,fn) {
	inst.handleShow(title,message,fn);
}

class Dialog extends React.Component {

	constructor(props) {
		super(props)
		inst = this;
		this.state = {
			show : false,
			okHandle:undefined
		}
	}

	handleShow = (title,message,fn) => {
		this.setState({
			title:title,
			message:message,
			show:true,
			okHandle:fn
		});
	}

	handleClose = () => {
		this.setState({show:false})
	}

	handleYes = () => {
		this.state.okHandle();
		this.handleClose();
	}

	render = () => {
	  return (<>
<Modal show={this.state.show} onHide={this.handleClose}>
  <Modal.Header closeButton>
	  <Modal.Title>{this.state.title}</Modal.Title>
  </Modal.Header>
	  <Modal.Body>{this.state.message}</Modal.Body>
  <Modal.Footer>

	{ this.state.okHandle === undefined &&
    <Button variant="secondary" onClick={this.handleClose}>
		とじる
    </Button>
	}	

	{ this.state.okHandle !== undefined &&
	<>
    <Button variant="secondary" onClick={this.handleClose}>
		いいえ
    </Button>
    <Button variant="primary" onClick={this.handleYes}>
		はい
    </Button>
	</>
	}
  </Modal.Footer>
</Modal>
		</>
	)
  }
}  

export default Dialog;