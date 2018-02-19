import React, {Component} from 'react';
import NavBar from './../obligatory/NavBar';
import {Breadcrumb, Col, Grid, Image, Jumbotron, Panel, Row} from "react-bootstrap";
import {Avatar, CircularProgress, FlatButton, List, ListItem, MuiThemeProvider} from "material-ui";
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
        this.unRegister = this.handleUnRegister.bind(this);
        this.state = {exists: false, isLoading: false, buttonVisible: false};
    }

    componentDidMount() {
        fetch(url + "/react/main/qrRegistered", {
            method: 'POST',
            body: "",
            headers: header,
            credentials: 'same-origin'
        }).then((Response) => Response.json()).then((findresponse) => {
            this.setState({
                exists: findresponse,
            });
        })
    }

    handleUnRegister() {
        fetch(url + "/react/main/unRegister", {
            method: 'POST',
            body: "",
            headers: header,
            credentials: 'same-origin'
        }).then((Response) => Response.json()).then((findresponse) => {
            this.setState({
                exists: findresponse,
            });
            return this.state.exists
        }).then((exists)=> {
            this.setState({exists});
        })
    }

    render() {
        let nothing = <FlatButton label={"Brak zarejestrowanego urządzenia moblinego"} disabled={true}/>;
        let button = <FlatButton
            label="Wyrejestruj Urządzenie"
            primary={true}
            onClick={this.unRegister}
        />;
        let content =
            <Jumbotron>
                <Grid>
                    <Row className="show-grid">
                        <Col xs={2} md={2}>

                        </Col>
                        <Col xs={8} md={8}>
                            <div align={"center"}>
                                <p>Zeskanuj kod QR by zalogować się na urządzeniu moblinym</p>
                                <Image src={"/react/main/qr"}/>
                            </div>
                            <div align={"center"}>
                                <MuiThemeProvider>
                                    {this.state.exists.status ? button : nothing}
                                </MuiThemeProvider>
                            </div>
                        </Col>
                        <Col xs={2} md={2}>

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
                <link rel="stylesheet" href="/css/mobileApp.css"/>


                <Row className="show-grid">
                    <Col xs={2} md={2}/>

                    <Col xs={8} md={8}>

                        <Breadcrumb>
                            <Breadcrumb.Item>
                                <Link to={"/main/circle"}>Strona Główna</Link>
                            </Breadcrumb.Item>
                            <Breadcrumb.Item active>
                                Aplikacja Moblina
                            </Breadcrumb.Item>
                        </Breadcrumb>

                        {content}


                    </Col>

                    <Col xs={2} md={2}/>
                </Row>

            </div>

        )
    }
}

export default MobileApp;