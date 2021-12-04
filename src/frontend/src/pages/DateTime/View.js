import React, { createRef } from "react";

import Button from "react-bootstrap/Button";
import DateTime from "../../DateTime"

class DateView extends React.Component {
    constructor(props) {
        super(props);
        this.date = createRef();
        this.datetime = createRef();
        this.time = createRef();
    }

    show = () => {
        var d = this.date.current;
        var dt = this.datetime.current;
        var t = this.time.current;
        console.log(d.get())
        console.log(t.get())
        console.log(dt.get())
    }

    render() {
        return(
        <>
        日付のみ
          <br/>
          <DateTime type="date" ref={this.date} value={new Date().toString()}/>
          <br/>
        時刻のみ
          <br/>
          <DateTime type="time" ref={this.time} step="10" value="0 10:10"/>
          <br/>
          両方
          <br/>
          <DateTime type="datetime" ref={this.datetime} empty="true"/>
          <br/>
          <br/>
          <Button onClick={this.show}>Val</Button>
        </>
    )}
}

export default DateView;