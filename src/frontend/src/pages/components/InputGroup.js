import React from "react";

import {Container,Row,Col} from "react-bootstrap";

import "../../css/Main.css";

class InputGroup extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data: []
        }
    }

    get = () => {
        var rtn = [];
        this.state.data.forEach( (elm) => {
            rtn.push(elm.key);
        });
        return rtn;
    }

    set = (obj) => {
        this.setState({data:obj});
    }

    add = (id,name) => {
        var data = this.state.data;
        data.push({
            key:id,
            value:name
        });
        this.setState({data:data});
    }

    remove = (id) => {
        var data = this.state.data;
        var n = data.filter( (obj) => {
            return id !== obj.key;
        });
        if ( data.length === n.length ) {
            console.log("not found:" + id);
        }
        this.setState({data:n});
    }

    render() {
        return (<>
        <div className={this.props.className}>
          <Container>
            <Row>

          { this.state.data.map( (elm,idx) => {
              return <Col key={idx} className="InputGroup-Data">
              <div className="InputGroup-Inner">
              <div className="InputGroup-Value"> {elm.value}({elm.key}) </div>
              <button type="button" className="btn-close InputGroup-Close" aria-label="Close" onClick={() => this.remove(elm.key)}></button>
              </div>
              </Col>
          })
          }
            </Row>
          </Container>
        </div>
        </>);
    }
}

export default InputGroup;