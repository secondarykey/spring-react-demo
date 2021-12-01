import React from "react";

import Toast from 'react-bootstrap/Toast';
import Spinner from 'react-bootstrap/Spinner';

var inst;
class Progress extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            show : false
        }
        inst = this;
    }

    render() {
      return (<>
  <Toast className="Progress" show={this.state.show}>
    <Toast.Body>
      <strong>Loading...</strong>
      <Spinner animation="border"/>
    </Toast.Body>
  </Toast>
</>);
    }
}

export function Show() {
    inst.setState({show:true})
}
export function Hide() {
    inst.setState({show:false})
}

export default Progress;
