import React from "react";

import {Form} from "react-bootstrap";

import "../../css/Main.css";
import { ChangeTitle } from "../../Layout";

class FloatInput extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            float : false
        }
        this.input = React.createRef();
        this.float = React.createRef();
        this.inputId = props.value;
        this.firstShow = false;

        ChangeTitle("フローティングインプット")
    }

    setValue = (id,name)  => {
        this.input.current.value = name;
        this.inputId = id;
    }

    ID = ()  => {
        return this.inputId;
    }

    show() {
        this.setState({
            float:true
        })
        this.firstShow = true;
        document.addEventListener("click",this.handleDocumentClick);
    }

    hide() {
        this.setState({
            float:false
        })
        document.removeEventListener("click",this.handleDocumentClick);
    }

    handleInputFocus = (e) => {
        this.show();
    }

    handleDocumentClick = (e) => {
        if ( this.float.current.contains(e.target) ) {
            return;
        }
        if ( this.firstShow && this.input.current.contains(e.target) ) {
            this.firstShow = false;
            return;
        }
        this.hide();
    }

    render() {

        let floatStyle = {}
        //TODO 本当はボーダーもコピーしたい
        if ( this.state.float ) {
          floatStyle = {
            width: this.input.current.offsetWidth,
            zIndex: this.input.current.style.zIndex + 10
          };
        }
        
        return (<>
        <Form.Control type="text" readOnly placeholder={this.props.placeholder} ref={this.input} onFocus={ (e) => this.handleInputFocus(e)}/>
        { this.state.float &&
        <div className="FloatComponent" style={floatStyle} ref={this.float}>
            {this.props.children}
        </div>
        }
        </>)
    }
}

export default FloatInput;