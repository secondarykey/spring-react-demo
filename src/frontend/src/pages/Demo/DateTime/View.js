import React, { createRef } from "react";

import {Button,Container} from "react-bootstrap";
import DateTime from "../../components/DateTime";

class DateView extends React.Component {
    constructor(props) {
        super(props);
        this.date = createRef();
        this.datetime = createRef();
        this.time = createRef();
    }

    isSafari() {
      const agent = window.navigator.userAgent.toLowerCase();
      if ( agent.indexOf("chrome") !== -1 ) {
      } else if ( agent.indexOf("safari") !== -1 ) {
        return true;
      }
      return false
    }
    
    componentDidMount() {


      var buf = "2022-03-28 00:00:00";
      if (this.isSafari()) {
        buf = buf.replaceAll(/-/g,"/")
        console.info(buf);
      }
      var date = Date.parse(buf);
      console.info(date);

      let obj = [
        {day:"2022-03-27",value:"1"},
        {day:"2022-03-28",value:"Shift-0"},
        {day:"2022-03-29",value:"0"},
        {day:"2022-03-30",value:"0"},
        {day:"2022-03-31",value:"0"},
        {day:"2022-03-29",value:"Select"}
      ]

      this.date.current.setStyles(obj);
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
        <Container>
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
        </Container>
    )}
}

export default DateView;