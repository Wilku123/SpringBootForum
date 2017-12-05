import React, {Component} from 'react';
const API = "http://localhost:8080/react/main/getCircle"; //TODO ZMIEN TO NA URL INNE CNIE
let header = {
    "Content-Type": "application/json"
};
import NavBar from './../obligatory/NavBar';
import {Accordion, Col, Panel, Row} from "react-bootstrap";
import {CircularProgress, MuiThemeProvider} from "material-ui";
const div = document.getElementById('circles');
// /*{this.state.circles.map((dynamicData,key)=>*/
// /*{dynamicData.name}:*/
// /*{dynamicData.lastName}*/

class Circle extends React.Component {


    constructor() {
        super();
        this.state = {circles: [],isLoading : true};
    }

    componentDidMount() {
        fetch(API,{
            method: 'POST',
            body: "",
            headers: header,
            credentials:'same-origin'}).
            then((Response) =>Response.json()).then((findresponse)=>{
            this.setState({
                circles:findresponse,
                isLoading:false
            })
        })
    }

    render() {
        let content = <Accordion>
            {this.state.circles.map((dynamicData,key)=>
                <Panel header={dynamicData.name+" "+ dynamicData.author.name} eventKey={key}>
                    {dynamicData.description}
                </Panel>
            )}
        </Accordion>;
        let loader =<MuiThemeProvider>
            <div align="center">
                <CircularProgress />
            </div>
        </MuiThemeProvider>;

        return(


            <div>
                <NavBar/>

                <Row className="show-grid">
                    <Col xs={6} md={2}/>

                    <Col xs={6} md={8}>

                        {this.state.isLoading ? loader : content}


                    </Col>

                    <Col xsHidden md={2}/>
                </Row>

            </div>

        )
    }
}

export default Circle