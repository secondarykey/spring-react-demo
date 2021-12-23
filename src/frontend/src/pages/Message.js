import React from "react";
import { Link } from "react-router-dom";
import { withRouter,WriteMessage,ChangeTitle } from "../Layout";
import { Remove } from "../Authentication";

class Message extends React.Component {

  constructor(props) {
      super(props);
      if ( props.id !== undefined ) {
          WriteMessage(props.id,props.type);
      } else if ( props.params !== undefined ) {
          var id = props.params.id
          WriteMessage(id,props.type);
      }
      //TODO エラーだった時にエラーにする
      ChangeTitle("メッセージ");
  }
  componentDidMount() {
      Remove();
  }
  render() {
    return ( <>
    <Link to="/">ログインする</Link>
</>
  )}
}

export default withRouter(Message);
