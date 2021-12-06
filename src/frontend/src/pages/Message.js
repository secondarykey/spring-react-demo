import React from "react";
import { withRouter,WriteMessage } from "../Layout";

class Message extends React.Component {

  constructor(props) {
      super(props);
      if ( props.id !== undefined ) {
          WriteMessage(props.id,props.type);
      } else if ( props.params !== undefined ) {
          var id = props.params.id
          WriteMessage(id,props.type);
      }
  }

  render() {
    return ( <>
    <a href="/">ログインする</a>
</>
  )}
}

export default withRouter(Message);
