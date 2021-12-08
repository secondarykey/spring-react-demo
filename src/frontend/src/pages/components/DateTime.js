import React from "react";
import DatePicker from "react-datepicker";

import { Form } from "react-bootstrap";
import Select from "./Select";

import "react-datepicker/dist/react-datepicker.css"
import "../../css/Main.css";

class DateTime extends React.Component {

    constructor(props) {
        super(props);

        let showDay = true;
        let showTime = true;
        this.step = 1;
        this.empty = false;

        var t = props.type;
        if ( t !== undefined ) {
            if ( t === "date" ) {
                showTime = false;
            } else if ( t === "time" ) {
                showDay = false;
            }
        }

        if ( props.step !== undefined ) {
            this.step = parseInt(props.step);
        }

        var day = null;
        var hour = 0;
        var minute = 0;
        if ( props.value !== undefined ) {
            day = new Date(props.value);
            hour = day.getHours();
            minute = day.getMinutes();
        } else {
            if ( props.empty !== undefined ) {
                this.empty = true;
                hour = "";
                minute = "";
            }
        }

        this.state = {
            hour : hour,
            minute : minute,
            day : day,
            showDay: showDay,
            showTime: showTime
        }
    }

    handleDay(day) {
        this.setState({
            day : day
        })
    }

    handleHour(hour) {
        this.setState({
            hour : hour
        })
    }

    handleMinute(minute) {
        this.setState({
            minute : minute
        })
    }

    get() {
        let rtn = "";
        let day = this.state.day;
        let hour = this.state.hour;
        let minute = this.state.minute;

        if ( this.state.showDay ) {
            if ( day != null ) {
                rtn = day.getFullYear() +"/" + (day.getMonth()+1) + "/"+day.getDate();
            } else {
                return undefined;
            }
        }

        if ( this.state.showTime ) {
            if ( rtn !== "" ) {
                rtn += " ";
            }
            if ( this.empty ) {
                if ( hour === "" || minute === "" ) {
                    if ( hour !== "" || minute !== "" ) {
                        if ( rtn === "" ) return null;
                        return undefined;
                    }
                }
            }

            rtn += hour;
            rtn += ":" + minute;
        }

        return rtn;
    }

    render() {
        let hours = []
        for ( let i = 0; i < 24; ++i ) {
            hours[i] = i;
        }

        let minutes = []
        for ( let i = 0; i < 60; i = i + this.step ) {
            minutes[i] = i;
        }

        return (
            <>
              <div className="DateTime">
              {this.state.showDay && 
                <DatePicker 
                  dateFormat="yyyy-MM-dd"
                  customInput={
                    <Form.Control type="text" />
                  }
                  selected={this.state.day}
                  onChange={(date) => this.handleDay(date)}
                /> 
              }

              {this.state.showTime && 
                <>
                  <Select values={hours}   className="DateTime-Hour"   value={this.state.hour} empty={this.empty} onChange={ (h)=> this.handleHour(h)}/>
                  <Select values={minutes} className="DateTime-Minute" value={this.state.minute} empty={this.empty} onChange={ (m)=> this.handleMinute(m)}/>
                </>
              }
              </div>
            </>
        )
    }
}

export default DateTime;