import React from "react";
import { useParams } from "react-router-dom";
import { WriteMessage } from "../Layout";

class Error extends React.Component {

  constructor(props) {

      super(props);

      console.log(props);

      if ( props.id !== undefined ) {
          WriteMessage(props.id);
      } else if ( props.params !== undefined ) {
          var id = props.params.id
          WriteMessage(id);
      }
  }

  render() {
    return ( <>
    <a href="/">ログインする</a>
</>
  )}
}

const withRouter = WrappedComponent => props => {
    const params = useParams();
    return (
      <WrappedComponent
        {...props}
        params={params}
      />
    );
  };

export default withRouter(Error);
