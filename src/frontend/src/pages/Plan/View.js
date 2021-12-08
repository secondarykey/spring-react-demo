import React from "react";
import {Button,Row,Col} from "react-bootstrap";

//import Select from "../components/Select";
import {Redirect} from "../../Layout";
import Util from "../../Util";

class PlanView extends React.Component {

    constructor(props) {
        super(props);
        this.places = [];
        this.places[1] = "日本";
        this.places[2] = "中国";
    }

    handleInput = () => {
        var target = Util.nowString();
        Redirect("/pages/plan/input/" + target);
    }

    render() {
        return (<>
        <Row>
        {/* 
            <Col>
        <Select values={this.places} value={1} />
            </Col>
        */}
            <Col>
        <Button onClick={this.handleInput}>入力</Button>
            </Col>
        </Row>
        </>)
    }
}

export default PlanView;