import React from "react";

import {Button} from "react-bootstrap"

import InputGroup from "../components/InputGroup";
import UserSearch from "../UserSearch";

class UserSearchView extends React.Component {

    constructor(props) {
        super(props);
        this.users = React.createRef();
        this.dialog = React.createRef();

        this.handleClick = this.handleClick.bind(this);
        this.handleSelect = this.handleSelect.bind(this);
    }

    componentDidMount() {
        this.users.current.set([]);
    }

    handleSelect(data) {
        var rowdata = [];
        data.forEach((elm) => {
            var obj = {
                key : elm.id,
                value : elm.name
            }
            rowdata.push(obj);
        })
        this.users.current.set(rowdata);
    }

    handleClick() {
        this.dialog.current.show("search","");
    }

    render() {
        return <>
        <InputGroup className="InputGroup-Frame" ref={this.users}></InputGroup>
        <UserSearch ref={this.dialog} onSelect={this.handleSelect}></UserSearch>
        <Button onClick={this.handleClick}>Search</Button>
        </>
    }
}

export default UserSearchView;