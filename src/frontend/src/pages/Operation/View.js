import React from "react";
import {Container,Row,Col, Button,
        Table} from "react-bootstrap";

import DateTime from "../components/DateTime";
import Organization from "../components/Organization";
import Images from "../components/Images";

import API from "../../API";
import {WriteErrorMessage} from "../../Layout";
import Util from "../../Util";

class View extends React.Component {

    constructor(props) {
        super(props);
        this.targetDate = React.createRef();
        this.org = React.createRef();

        this.state = {
            operationList : [],
            workersList : []
        }
    }

    handleSearch = () => {
      let date = this.targetDate.current.get();
      let org = this.org.current.get();
      let args = {
        organization: org,
        day: date
      }

      API.post("/api/demo/worker/day",resp => {
        var result = resp.data.result;
        var operationList = result.operationList;
        var workers = result.workersList;

        console.log(result)

        this.setState({
            operationList : operationList,
            workersList : workers
        })

      },args).catch( (err) => {
        if ( API.isUnknownError(err) ) {
          return;
        }
        WriteErrorMessage(err);
      });
    }

    componentDidMount() {
      let ref = this.targetDate.current;
      let today = new Date();
      ref.set(today);

      let args = {
        "day" : Util.formatDate(today)
      };

      API.post("/api/demo/day/all",resp => {
        var result = resp.data.result;
        let arr = result.days;

        this.targetDate.current.setStyles(arr);
        this.org.current.set(result.org);

      },args).catch( (err) => {
        if ( API.isUnknownError(err) ) {
          return;
        }
        WriteErrorMessage(err);
      })
    }

    render() {
      return <>
        <Container>
          <Row>
            <Col>
              <DateTime type="date" ref={this.targetDate} onChange={this.handleDate}/>
            </Col>
            <Col>
              <Organization ref={this.org} onChange={this.handleOrganization}/>
            </Col>
            <Col>
              <Button onClick={this.handleSearch}>Search</Button>
            </Col>
          </Row>
        </Container>

        <Container>
          <Table>
            <thead>
              <tr>
                <td>-</td>
              {this.state.operationList.map( (obj,idx) => {
                  return (
                      <td key={idx}>{obj.operation.name}</td>
                  );
              })}
              </tr>
            </thead>

            <tbody>
            {this.state.workersList.map( (elm,idx) => {

                let cols = [];
                elm.workers.forEach( (elm) => {
                  var elements = <td></td>
                  if ( elm !== null ) {
                      elements = <td>{
                          elm.map( (worker) => {
                              return (<div><Images className="faceImage" src={"faces/" + worker.userID} /><br/>{worker.name}</div>)
                          })
                          }
                      </td>
                  }
                  cols.push(elements);
                });

                return (
                <tr>
                    <td>{idx + 1}</td>
                    {cols}
                </tr>
                )
            })}
            </tbody>
          </Table>
        </Container>

        </>
    }
}
export default View;