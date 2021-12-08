import React from "react";
import {Link} from "react-router-dom";
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
    <Link to="/">ログインする</Link>
</>
  )}
}

export default withRouter(Message);
