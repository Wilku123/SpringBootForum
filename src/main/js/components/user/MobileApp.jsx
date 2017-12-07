import React, {Component} from 'react';
import NavBar from './../obligatory/NavBar';
import {Breadcrumb, Col, Grid, Image, Jumbotron, Panel, Row} from "react-bootstrap";
import {Avatar, CircularProgress, List, ListItem, MuiThemeProvider} from "material-ui";
import {Link} from "react-router-dom";
import {url} from '../../Constants';

let header = {
    "Content-Type": "application/json"
};

// /*{this.state.circles.map((dynamicData,key)=>*/
// /*{dynamicData.name}:*/
// /*{dynamicData.lastName}*/

class MobileApp extends React.Component {


    constructor() {
        super();
        this.state = {qr: [], isLoading: false};
    }

    componentDidMount() {
        // fetch(url + "/react/main/qr", {
        //     method: 'POST',
        //     body: "",
        //     headers: header,
        //     credentials: 'same-origin'
        // }).then((Response) => Response.json()).then((findresponse) => {
        //     this.setState({
        //         qr: findresponse,
        //         isLoading: false
        //     })
        // })
    }

    render() {
        let content =
            <Jumbotron>
                <Grid>
                    <Row className="show-grid">
                        <Col xs={12} md={2}>

                        </Col>
                        <Col xs={6} md={8}>
                            <div align={"center"}>
                            <p>Zeskanuj kod QR by zalogować się na urządzeniu moblinym</p>
                            <Image  src={"/react/main/qr"}/>
                            </div>
                        </Col>
                        <Col xs={6} md={2}>

                        </Col>
                    </Row>
                </Grid>



            </Jumbotron>;
        let loader = <MuiThemeProvider>
            <div align="center">
                <CircularProgress/>
            </div>
        </MuiThemeProvider>;

        return (

            <div>
                <NavBar/>
                <link rel="stylesheet" href="../../css/mobileApp.css"/>


                <Row className="show-grid">
                    <Col xs={6} md={2}/>

                    <Col xs={6} md={8}>

                        <Breadcrumb>
                            <Breadcrumb.Item>
                                <Link to={"/main/circle"}>Strona Główna</Link>
                            </Breadcrumb.Item>
                            <Breadcrumb.Item active>
                                Aplikacja Moblina
                            </Breadcrumb.Item>
                        </Breadcrumb>

                        {this.state.isLoading ? loader : content}


                    </Col>

                    <Col xsHidden md={2}/>
                </Row>

            </div>

        )
    }
}

export default MobileApp;