import React from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

import axios from 'axios';

import {WriteErrorMessage} from '../../../Layout';
import API from "../../../API"

class UploadFile extends React.Component {

  constructor(props) {
    super(props);
    this.file = React.createRef();
    this.upload = this.upload.bind(this);

    this.state = {
      id:0, filename:""
    }
  }
  
  componentDidMount() {
  }

  upload() {

    var formData = new FormData();
    var f = this.file.current.files[0];
    formData.append("file",f);
    var name = f.name;

    API.put("/api/demo/file/upload",resp => {

      var id = resp.data.result;
      this.setState({
        id : id,
        filename:name
      });

    },formData).catch((err) => {
      if ( API.isUnknownError(err) ) {
        return;
      }
      WriteErrorMessage(err);
    });
  }
  
  handleDownload(id) {
    let url = "/api/demo/file/get/" + id;
    //window.open("/api/demo/file/get/" + id);
    axios.get(url, {
      responseType: 'blob',
    }).then((res) => {
      //fileDownload(res.data, filename)
        const url = window.URL.createObjectURL(new Blob([res.data]));
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download",this.state.filename);
        document.body.appendChild(link);
        link.click();

        // Clean up and remove the link
        link.parentNode.removeChild(link);
    });
  }

  render() {
    return ( <>	
      <Form.Group controlId="formFile" className="mb-3">
        <Form.Label>Default file input example</Form.Label>
        <Form.Control type="file" ref={this.file}/>
      </Form.Group>
      <Button onClick={(e) => this.upload()}>Upload</Button>

<br/><br/>

  {this.state.id != 0 &&
<>
  <a href="#" onClick={() => this.handleDownload(this.state.id)}>Download1</a>
  {/** 
  <a href={"/api/demo/file/get/" + this.state.id} download>Download2</a>
  */}
</>
  }
	</>);
  }
}

export default UploadFile;