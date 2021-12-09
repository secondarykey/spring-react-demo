import React from "react";
import {Container,Table,Button,Row,Col} from "react-bootstrap";

//import Select from "../components/Select";
import {Redirect} from "../../Layout";
import {Link} from "react-router-dom";
import Util from "../../Util";
import API from "../../API";
import {WriteErrorMessage} from '../../Layout';

class PlanView extends React.Component {

    constructor(props) {
        super(props);
        this.places = [];
        this.places[1] = "日本";
        this.places[2] = "中国";

        this.state = {
            plans : []
        }
    }
    componentDidMount() {
        var args = {
            placeId : 1
        };
        API.post("/api/demo/plan/view",resp => {
          let plans = resp.data.result.plans;
          this.setState({
            plans : plans
          })
        },args).catch((err) => {
          if ( API.isUnknownError(err) ) {
            return;
          }
          WriteErrorMessage(err);
        });
      }
    
    handleInput = () => {
        var target = Util.nowString();
        Redirect("/pages/plan/input/" + target);
    }

    render() {
        return (<>
        <Container>

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
        </Container>

        <Table>
          <tbody>

       { this.state.plans.map( (obj,idx) => {
           return (
               <tr key={idx}>
                   <td>
            <Link key={idx} to={"/pages/plan/input/" + obj.date.slice(0,10)}>{obj.date.slice(0,10)}</Link>
                   </td>
            </tr>
           )
       })} 
          </tbody>
        </Table>

        </>)
    }
}

export default PlanView;