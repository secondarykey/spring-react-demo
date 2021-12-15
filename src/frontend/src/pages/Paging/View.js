import React from "react";

import {Table,Button,Container} from "react-bootstrap";
import Paging from "../components/Paging";
import DateTime from "../components/DateTime";
import Select from "../components/Select";
import API from "../../API";
import {WriteErrorMessage} from "../../Layout";

class Time extends React.Component {
    constructor (props) {
        super(props)
        this.date = React.createRef();

        this.limitValues = [];
        this.limitValues[5] = 5;
        this.limitValues[10] = 10;
        this.limitValues[100] = 100;

        this.timezoneList = {};
        this.timezoneList["ACT"]="Australia/Darwin(ACT)";
        this.timezoneList["AET"]="Australia/Sydney(AET)";
        this.timezoneList["AGT"]="America/Argentina/Buenos_Aires(AGT)";
        this.timezoneList["ART"]="Africa/Cairo(ART)";
        this.timezoneList["AST"]="America/Anchorage(AST)";
        this.timezoneList["BET"]="America/Sao_Paulo(BET)";
        this.timezoneList["BST"]="Asia/Dhaka(BST)";
        this.timezoneList["CAT"]="Africa/Harare(CAT)";
        this.timezoneList["CNT"]="America/St_Johns(CNT)";
        this.timezoneList["CST"]="America/Chicago(CST)";
        this.timezoneList["CTT"]="Asia/Shanghai(CTT)";
        this.timezoneList["EAT"]="Africa/Addis_Ababa(EAT)";
        this.timezoneList["ECT"]="Europe/Paris(ECT)";
        this.timezoneList["EST"]="-05:00(EST)";
        this.timezoneList["HST"]="-10:00(HST)";
        this.timezoneList["IET"]="America/Indiana/Indianapolis(IET)";
        this.timezoneList["IST"]="Asia/Kolkata(IST)";
        this.timezoneList["JST"]="Asia/Tokyo(JST)";
        this.timezoneList["MIT"]="Pacific/Apia(MIT)";
        this.timezoneList["MST"]="-07:00(MST)";
        this.timezoneList["NET"]="Asia/Yerevan(NET)";
        this.timezoneList["NST"]="Pacific/Auckland(NST)";
        this.timezoneList["PLT"]="Asia/Karachi(PLT)";
        this.timezoneList["PNT"]="America/Phoenix(PNT)";
        this.timezoneList["PRT"]="America/Puerto_Rico(PRT)";
        this.timezoneList["PST"]="America/Los_Angeles(PST)";
        this.timezoneList["SST"]="Pacific/Guadalcanal(SST)";
        this.timezoneList["VST"]="Asia/Ho_Chi_Minh(VST)";

        this.timezone = "JST";

        this.state = {
            times : [],
            paging : { 
                current : 1,
                numberOfDisplay : 5 
            }
        }

        this.handleInsert = this.handleInsert.bind(this);
    }

    componentDidMount() {
        this.viewTimes(1,5);
    }

    viewTimes(num,max) {
        let args = {
            paging : {
              currentPage:num,
              numberOfDisplay:max
            }
        }
        API.post("/api/demo/times/view",resp => {
            let rtn = resp.data.result;
            this.setState({
                times : rtn.times,
                paging : rtn.paging
            });
        },args).catch( (err) => {
            if ( API.isUnknownError(err) ) {
                return;
              }
              WriteErrorMessage(err);
        });
    }

    handleTimezone = (v) => {
        this.timezone = v;
    }

    handleChange = (v) => {
        //let limit = this.limitValues[v];
        this.viewTimes(1,v);
    }

    handleInsert = () => {
        let val = this.date.current.get();
        let args = {
            value : val,
            zone : this.timezone,
            paging : {
              currentPage:1,
              numberOfDisplay:this.state.paging.numberOfDisplay
            }
        }

        API.post("/api/demo/times/register",resp => {
            let rtn = resp.data.result;
            this.setState({
                times : rtn.times,
                paging : rtn.paging
            });
            this.date.current.set(new Date());
        },args).catch( (err) => {
            if ( API.isUnknownError(err) ) {
                return;
              }
              WriteErrorMessage(err);
        });

    }

    handleClicked = (p) => {
        this.viewTimes(p,this.state.paging.numberOfDisplay);
    }

    render() {
        return (<>
    <Container>
        <Select className="Paging-Timezone" values={this.timezoneList} value={this.timezone} onChange={(v) => this.handleTimezone(v)}/>
        <DateTime type="datetime" ref={this.date} value={new Date().toString()}/>
        <Button onClick={this.handleInsert}>追加</Button>
    </Container>

    <Container>

        <Table striped bordered size="sm">
          <thead>
            <tr>
                <th>ID</th>
                <th>値</th>
                <th>Date</th>
                <th>Time</th>
                <th>Date To Without</th>
                <th>Date To With</th>
                <th>OffsetDateTime To Without</th>
                <th>OffsetDateTime To With</th>
            </tr>
          </thead>
          <tbody>

        {this.state.times.map( (obj,idx) => {
            return (
            <tr key={idx}>
                <td>{obj.id}</td>
                <td>{obj.value}</td>
                <td>{obj.date}</td>
                <td>{obj.time}</td>
                <td>{obj.dateToWithout}</td>
                <td>{obj.dateToWith}</td>
                <td>{obj.offsetToWithout}</td>
                <td>{obj.offsetToWith}</td>
            </tr>
            )
        })}

          </tbody>
        </Table>
    </Container>

    <Container style={ {display:"flex"} }>
      <Select className="Paging-Limit" values={this.limitValues} value={this.state.paging.numberOfDisplay}  onChange={ (v)=> this.handleChange(v)}/>
      <Paging paging={this.state.paging} max={5} onClick={(p) => this.handleClicked(p)} />
    </Container>
        </>)
    }

}

export default Time;