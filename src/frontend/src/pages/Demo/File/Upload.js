import React from 'react';
import ListGroup from 'react-bootstrap/ListGroup';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';

import {WriteErrorMessage} from '../../../Layout';
import API from "../../../API"
import {ShowConfirm} from "../../../Dialog"

class UploadFile extends React.Component {

  constructor(props) {

    super(props);
    this.file = React.createRef();
    this.upload = this.upload.bind(this);
  }
  
  componentDidMount() {
  }

  upload() {

    var formData = new FormData();
    console.log(this.file.current);
    formData.append("file",this.file.current.files[0]);

    API.put("/api/demo/file/upload",resp => {

      console.log(resp);

    },formData).catch((err) => {
      if ( API.isUnknownError(err) ) {
        return;
      }
      WriteErrorMessage(err);
    });
  }

  render() {
    return ( <>	
      <Form.Group controlId="formFile" className="mb-3">
        <Form.Label>Default file input example</Form.Label>
        <Form.Control type="file" ref={this.file}/>
      </Form.Group>
      <Button onClick={(e) => this.upload()} />
	</>);
  }
}

export default UploadFile;