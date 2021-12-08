import React from "react";
import {Button} from "react-bootstrap";
import {withRouter} from "../../Layout";
import DateTime from "../components/DateTime";
import {PlanInputDialog,ShowInput} from "./Dialog";

class PlanInput extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            targetDate : props.params.date
        }
    }

    handleInput = () => {
        //ダイアログを呼ぶ
        ShowInput(this.state.targetDate);
    }

    render() {
        return (<>
        <DateTime type="date" value={this.state.targetDate}></DateTime>
        <Button onClick={this.handleInput}>追加</Button>
        <PlanInputDialog/>
        </>)
    }
}

export default withRouter(PlanInput);