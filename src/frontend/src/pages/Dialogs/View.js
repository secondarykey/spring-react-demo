import React, { createRef } from "react";

import {
    Container, Row,Col,Stack,
    Card,
    Button,
} from "react-bootstrap";

import { ShowInformation,ShowConfirm } from "../../Dialog";
import { ChangeTitle, ClearMessage, WriteMessage } from "../../Layout";

import DoubleDialog from "./Dialog"


class View extends React.Component {

    constructor(props)  {
        super(props)
        ChangeTitle("ダイアログ確認")
        this.state = {
            cards : []
        }
        this.clickCount = 0;

        this.changeColor = this.changeColor.bind(this);
        this.dialog = createRef();
    }

    componentDidMount() {

        let cards = [];

        cards.push(this.createObj(1,"A",0));
        cards.push(this.createObj(2,"B",1));
        cards.push(this.createObj(3,"C",2));
        cards.push(this.createObj(4,"D",0));

        this.setState({
            cards : cards
        })

    }

    createObj = ( id,name,state ) => {
        return {
            id : id,
            name : name,
            state : state
        }
    }

    changeColor = ( obj,state ) => {
        ClearMessage();
        if ( obj.state === state ) {
            //取り消し
            obj.state = 0;
        } else {
            obj.state = state;
        }

        let updateCard = this.state.cards.map( (elm) => {
            if ( obj.id === elm.id ) {
                elm.state = obj.state;
            }
            return elm;
        });

        this.setState({
            cards : updateCard
        })
    }

    handleSingle = (e,zz,obj,state) => {
        this.clickCount++;
        if ( this.clickCount < 2 ) {
            setTimeout(() => {
                if ( this.clickCount > 1 ) {
                    this.changeColor(obj,state);
                } else {
                    this.showDialog(obj,zz)
                }
                this.clickCount = 0;
            },300)
        }
    }

    showDialog = (obj,zz) => {
        ClearMessage();
        switch (zz) {
            case "info":
                ShowInformation(obj.name + "の呼び出し","押したよ")
                break;
            case "confirm":
                ShowConfirm(obj.name + "の呼び出し", "はいを押したらメッセージ").then( () => {
                    WriteMessage("YES!","success");
                });
                break;
            case "original":
                this.dialog.current.show(obj.name);
                break;
            default:
                break;
        }
    }

    createClassName = (obj) => {
        let base = "Dialogs-Card";
        switch ( obj.state ) {
            case 1:
                base += " Dialogs-State1";
                break;
            case 2:
                base += " Dialogs-State2";
                break;
            default:
                break;
        };
        return base;
    }

    render() {
    return (<>
    <Container>
        <Row>

        {this.state.cards.map( (obj) => {
            return (<>
            <Col>
        <Card className={this.createClassName(obj)}>

            <Card.Title>{obj.name}</Card.Title>
            <Stack direction="horizontal" gap={3}>

            <Button variant="primary" className="Dialogs-Button"
                    onClick={(e) => this.handleSingle(e,"info",obj,0)}> 
                Info
            </Button>

            <Button variant="success" className="Dialogs-Button"
                    onClick={(e) => this.handleSingle(e,"confirm",obj,1)}> 
                Confirm
            </Button>

            <Button variant="danger" className="Dialogs-Button"
                    onClick={(e) => this.handleSingle(e,"original",obj,2)}> 
                Original
            </Button>
            </Stack>
        </Card>
            </Col>
            </>);
        })}
        </Row>
    </Container>

    <DoubleDialog ref={this.dialog}/>
    </>)}
}

export default View;