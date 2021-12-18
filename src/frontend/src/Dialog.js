import React from "react";

import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

var inst;

export function ShowInformation(title,message,fn) {
	return inst.handleShow(title,message,"information");
}

export function ShowConfirm(title,message) {
	return inst.handleShow(title,message,"confirm");
}

class Dialog extends React.Component {

	constructor(props) {
		super(props)
		inst = this;
		this.state = {
			show : false,
		}
	}

	handleShow = (title,message,t) => {
		let rtn = new Promise((resolve,reject) => {
		  this.setState({
			  promise : {
				  resolve : resolve,
				  reject : reject,
			  },
			  type:t,
			  title:title,
			  message:message,
			  show:true,
		  });
		})
		return rtn;
	}

	handleClose = () => {
		this.state.promise.reject();
		this.setState({show:false})
	}

	handleYes = () => {
		this.state.promise.resolve();
		this.handleClose();
	}

	render = () => {
	  return (<>
<Modal show={this.state.show} onHide={this.handleClose} centered>

  <Modal.Header closeButton>
	  <Modal.Title>{this.state.title}</Modal.Title>
  </Modal.Header>
	  <Modal.Body>{this.state.message}</Modal.Body>
  <Modal.Footer>

	{ this.state.type === "information" &&
    <Button variant="secondary" onClick={this.handleClose}>
		とじる
    </Button>
	}	

	{ this.state.type === "confirm" &&
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