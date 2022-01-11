import React from "react";
import {Button,Container,Table} from "react-bootstrap";
import PlanInputDialog,{ShowInput} from "./Dialog";

import DateTime from "../../components/DateTime";
import API from "../../../API";
import Util from "../../../Util";
import {withRouter,WriteErrorMessage} from '../../../Layout';

class PlanInput extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            targetDate : props.params.date,
            details : []
        }
    }
    componentDidMount() {
        var args = {
            placeId : 1,
            date : this.state.targetDate
        };
        API.post("/api/demo/plan/detail/view",resp => {
          let details = resp.data.result.details;
          this.setState({
            details : details
          })
        },args).catch((err) => {
          if ( API.isUnknownError(err) ) {
            return;
          }
          WriteErrorMessage(err);
        });
      }
    handleInput = () => {
        //ダイアログを呼ぶ
        ShowInput(this.state.targetDate);
    }

    render() {
        return (<>
        <DateTime type="date" value={this.state.targetDate}></DateTime>
        <Button onClick={this.handleInput}>追加</Button>
        <Container>
            <Table>
                <tbody>

       { this.state.details.map( (obj,idx) => {
           return (
           <tr key={idx}>
              <td> {Util.formatTime(obj.start)} </td>
              <td> {Util.formatTime(obj.end)} </td>
              <td> {obj.name} </td>
            </tr>
           )
       })} 
                    </tbody>
            </Table>
        </Container>

        <PlanInputDialog/>
        </>)
    }
}

export default withRouter(PlanInput);