import React from 'react';
import ListGroup from 'react-bootstrap/ListGroup';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';

import {WriteErrorMessage} from '../../Layout';
import {ExtendRefresh} from '../../Authentication';
import API from "../../API"
import {ShowDialog} from "../../Dialog"

class ToDo extends React.Component {

  constructor(props) {

    super(props);

    this.addText = React.createRef();

    var todos = [];
    this.state = {
      todos:todos
    }

    this.add = this.add.bind(this); 
    this.del = this.del.bind(this); 
  }
  
  componentDidMount() {
    var args = {
    };
    API.post("/api/demo/todo/view",resp => {
      let todos = resp.data.result.todos;
      this.setState({
        todos : todos
      })
    },args).catch((err) => {
      if ( API.isUnknownError(err) ) {
        return;
      }
      WriteErrorMessage(err);
    });
  }

  add() {
    let val = this.addText.current.value;
    var args = {
      value : val
    };

    ExtendRefresh();

    API.post("/api/demo/todo/register",resp => {
      let todos = [...this.state.todos]
      todos.push({ 
        id : resp.data.result,
        value : val 
      });
      this.setState({
        todos:todos
      });
      this.addText.current.value = "";
    },args).catch( (err) => {
      if ( API.isUnknownError(err) ) {
        return;
      }
      WriteErrorMessage(err);
    }) 
  }

  del(id) {

    ExtendRefresh();

    var args = {
      id : id
    };

    let todos = this.state.todos.filter((x) => { return x.id !== id });
    var self = this;

    ShowDialog("タイトル","メッセージ",function() {
      API.delete("/api/demo/todo/delete",resp => {
        self.setState({
          todos:todos
        });
      },args).catch( (err) => {
        if ( API.isUnknownError(err) ) {
          return;
        }
        WriteErrorMessage(err);
      }) 
    });
  }    

  render() {
    return ( <>	

  <Row className="justify-content-between d-flex" style={{ margin : "2px"}}>
    <Form.Control type="text" placeholder="" ref={this.addText}/>
    <Button variant="primary" onClick={() => this.add()}>Add</Button>
  </Row>

  <ListGroup as="ol" style={ {marginTop:"20px"} }>
      { this.state.todos.map( ( obj ) => {
        return (
    <ListGroup.Item as="li" className="d-flex justify-content-between align-items-start" key={obj.id}>
      <div className="ms-2 me-auto">
        <div>{obj.value}</div>
      </div>
      <Button variant="danger" onClick={() => this.del(obj.id)}>DELETE</Button>
    </ListGroup.Item>
        )
      } ) }
  </ListGroup>  

	</>);
  }
}

export default ToDo;