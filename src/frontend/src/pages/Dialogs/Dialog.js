import React from "react";

import {
    Modal,Button
} from "react-bootstrap";
import { ShowConfirm } from "../../Dialog";

class Dialog extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            show : false
        }
    }

    show = (name) => {
        this.setState({
            show : true,
            brightness:1,
            title : name + "から呼び出し",
            message:"更新でダイアログの二重起動を行います。"
        })
    }

    handleClose = () => {
        this.setState({
            show : false
        })
    }

    handleUpdate = () => {

        let self = this;
        this.setState({
          brightness : 0.5
        })

        ShowConfirm("２重起動","２重起動側").then( () => {
          self.handleClose();
        }).finally(function() {
          self.setState({
            brightness : 1
          })
        })
    }

    render() {
        return (<>
<Modal show={this.state.show} style={ { filter: "brightness(" + this.state.brightness + ")"} }onHide={this.handleClose} backdrop="static">
  <Modal.Header closeButton>
	  <Modal.Title>{this.state.title}</Modal.Title>
  </Modal.Header>
  <Modal.Body style={ {height:"200px"} }>{this.state.message}</Modal.Body>
  <Modal.Footer>
    <Button variant="secondary" onClick={this.handleClose}>
        とじる
    </Button>
    <Button variant="primary" onClick={this.handleUpdate}>
        更新
    </Button>
  </Modal.Footer>
</Modal>
        </>)
    }
}

export default Dialog;