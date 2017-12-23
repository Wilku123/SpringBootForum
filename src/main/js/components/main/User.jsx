import React, {Component} from 'react';
import NavBar from './../obligatory/NavBar';
import {Breadcrumb, Col, Grid, Jumbotron, Panel, Row} from "react-bootstrap";
import {
    Avatar, CircularProgress, Dialog, Divider, FlatButton, FloatingActionButton, List, ListItem,
    MuiThemeProvider, Paper, TextField
} from "material-ui";
import ContentAdd from 'material-ui/svg-icons/content/add';
import {Link} from "react-router-dom";
import {url} from '../../Constants';
import ReactQueryParams from 'react-query-params';


let header = {
    "Content-Type": "application/json"
};

// /*{this.state.circles.map((dynamicData,key)=>*/
// /*{dynamicData.name}:*/
// /*{dynamicData.lastName}*/

class User extends ReactQueryParams {


    constructor() {
        super();
        this.state = {
            user: [],
            isLoading: true
        };
    }


    componentDidMount() {
        fetch(url + "/react/main/getUser", {
            method: 'POST',
            body: JSON.stringify({
                idUser: this.queryParams.user
            }),
            headers: header,
            credentials: 'same-origin'
        }).then((Response) => Response.json()).then((findresponse) => {
            this.setState({
                user: findresponse,
                isLoading: false
            })
        });

    }

    render() {


        let content =
            <Jumbotron>
                <MuiThemeProvider>
                    <div>
                        <Paper style={"paper"} zDepth={3}>
                            <Grid>
                                <Row className="show-grid">
                                    <Col xs={12} md={3}>
                                        <Paper style={"paper"} zDepth={1}>
                                            <div align={"center"}>
                                                <Avatar src={this.state.user.avatar} size={120}/>
                                            </div>
                                            <br/>
                                            <strong>Imię:</strong> {this.state.user.name}
                                            <br/>
                                            <strong>Nazwisko:</strong> {this.state.user.lastName}
                                            <br/>
                                            <strong>Adres e-mail:</strong> {this.state.user.email}
                                        </Paper>
                                    </Col>
                                    <Col xs={12} md={9}>
                                        <Paper style={"paper"} zDepth={1}>

                                        </Paper>
                                    </Col>
                                </Row>
                            </Grid>
                        </Paper>
                    </div>
                </MuiThemeProvider>
            </Jumbotron>;
        let loader = <MuiThemeProvider>
            <div align="center">
                <CircularProgress/>
            </div>
        </MuiThemeProvider>;

        return (



            <div>
                <NavBar/>
                <link rel="stylesheet" href="../../css/answer.css"/>


                <Row className="show-grid">
                    <Col xs={6} md={2}/>

                    <Col xs={6} md={8}>

                        <Breadcrumb>
                            <Breadcrumb.Item href="/main/circle">
                                Strona Główna
                            </Breadcrumb.Item>
                            <Breadcrumb.Item active>
                                {this.state.user.name + " " + this.state.user.lastName}
                            </Breadcrumb.Item>
                        </Breadcrumb>
                        <Panel id="mainPanel">
                            <h4>
                                Użytkownik
                            </h4>
                        </Panel>
                        {this.state.isLoading ? loader : content}


                    </Col>

                    <Col xsHidden md={2}/>
                </Row>
            </div>

        )
    }
}

export default User