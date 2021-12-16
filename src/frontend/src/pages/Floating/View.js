import React from "react";

import {Container} from "react-bootstrap";

import FloatInput from "../components/FloatInput";

import "../../css/Main.css";
class View extends React.Component {
    render() {



        return (<>
        <FloatInput>
            <Container>
                AAa <br/> BBB
            </Container>
        </FloatInput>

        <div>Other Component</div>
        </>)
    }
}

export default View;