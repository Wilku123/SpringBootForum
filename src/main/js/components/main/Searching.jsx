import React, {Component} from 'react';
import NavBar from './../obligatory/NavBar';
import {Breadcrumb, Col, Grid, Jumbotron, Panel, Row, Table} from "react-bootstrap";
import {
    Checkbox, CircularProgress, Dialog, FlatButton, MuiThemeProvider, RaisedButton, Tab,
    Tabs
} from "material-ui";
import ReactQueryParams from 'react-query-params';

import Timestamp from 'react-timestamp';
import {url} from '../../Constants';
import Link from "react-router-dom/es/Link";

let header = {
    "Content-Type": "application/json"
};

// /*{this.state.circles.map((dynamicData,key)=>*/
// /*{dynamicData.name}:*/
// /*{dynamicData.lastName}*/

class Searching extends ReactQueryParams {


    constructor() {
        super();
        this.saveChangesCircle = this.handleSaveChangesCircle.bind(this);
        this.saveChangesTopic = this.handleSaveChangesTopic.bind(this);
        this.closeModal = this.handleClose.bind(this);
        this.openDialogCircle = this.handleOpenCircle.bind(this);
        this.openDialogTopic = this.handleOpenTopic.bind(this);
        this.state = {
            circles: [],
            topics: [],
            answers:[],
            isLoadingCircle: true,
            isLoadingTopic: true,
            isLoadingAnswers:true,
            circleInTopic: "",
            unSubListCircle: [],
            unSubListTopic: [],
            openCircle: false,
            openTopic: false,
            status: []
        };

    }


    handleOpenCircle() {
        this.setState({openCircle: true});
    };

    handleOpenTopic() {
        this.setState({openTopic: true});
    };

    handleClose() {
        this.setState({openCircle: false, openTopic: false});
    };

    handleSaveChangesCircle() {

        if (this.state.unSubListCircle.length > 0) {
            fetch(url + "/react/main/unSubCircle", {
                method: 'POST',
                body: JSON.stringify({
                    list: this.state.unSubListCircle
                }),
                headers: header,
                credentials: 'same-origin'
            }).then((Response) => Response.json()).then((findresponse) => {
                this.setState({
                    status: findresponse,
                    openCircle: false
                });
                window.location.replace(url + "/main/subbed");
            });
        } else {
            this.handleClose();
        }
    }

    handleSaveChangesTopic() {

        if (this.state.unSubListTopic.length > 0) {
            fetch(url + "/react/main/unSubTopic", {
                method: 'POST',
                body: JSON.stringify({
                    list: this.state.unSubListTopic
                }),
                headers: header,
                credentials: 'same-origin'
            }).then((Response) => Response.json()).then((findresponse) => {
                this.setState({
                    status: findresponse,
                    openTopic: false
                });
                window.location.replace(url + "/main/subbed");
            });
        } else {
            this.handleClose();
        }
    }


    componentDidMount() {
        fetch(url + "/react/main/getCirclesByContent", {
            method: 'POST',
            body: JSON.stringify({
                name: this.queryParams.lookFor,
                description:this.queryParams.lookFor
            }),
            headers: header,
            credentials: 'same-origin'
        }).then((Response) => Response.json()).then((findresponse) => {
            this.setState({
                circles: findresponse,
                isLoadingCircle: false
            })
        });
        fetch(url + "/react/main/getTopicsByContent", {
            method: 'POST',
            body: JSON.stringify({
                name: this.queryParams.lookFor,
                description: this.queryParams.lookFor,
            }),
            headers: header,
            credentials: 'same-origin'
        }).then((Response) => Response.json()).then((findresponse) => {
            this.setState({
                topics: findresponse,
                isLoadingTopic: false
            })
        });
        fetch(url + "/react/main/getAnswersByContent", {
            method: 'POST',
            body: JSON.stringify({
                content : this.queryParams.lookFor,
            }),
            headers: header,
            credentials: 'same-origin'
        }).then((Response) => Response.json()).then((findresponse) => {
            this.setState({
                answers: findresponse,
                isLoadingAnswers: false
            })
        })
    }

    // {this.state.subbedCircle.map((dynamicData, key) =>
// <th>{dynamicData.name}</th>
// <th>{dynamicData.description}</th>
// <th>{dynamicData.countSubbed}</th>
// <th>{dynamicData.countTopic}</th>
// <th>{dynamicData.publishDate}</th>
// )}

// {/*<TableRow key={key} onRowClick={(dynamicData.idCircle => this.handleUnSub)}>*/}
// {/*<TableRowColumn >{dynamicData.name}</TableRowColumn>*/}
// {/*<TableRowColumn>{dynamicData.description}</TableRowColumn>*/}
// {/*<TableRowColumn>{dynamicData.countSubbed}</TableRowColumn>*/}
// {/*<TableRowColumn>{dynamicData.countTopic}</TableRowColumn>*/}
// {/*<TableRowColumn>{dynamicData.publishDate}</TableRowColumn>*/}
// {/*<TableRowColumn>{dynamicData.author.name} {dynamicData.author.lastName}</TableRowColumn>*/}
// {/*</TableRow>*/}


    render() {

        let contentAnswer =
            <Jumbotron>
                <MuiThemeProvider>



                    <Table bordered striped hover>
                        <thead>
                        <tr>

                            <th>Odpowiedź</th>
                            <th>Data Utworzenia</th>
                            <th>Autor</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.state.answers.map((dynamicData, key) =>
                            <tr>

                                <td> <Link to={"/main/answer?topic=" + dynamicData.topicIdTopic}>{dynamicData.content}</Link></td>
                                <td><Timestamp time={(dynamicData.publishDate) / 1000} format='date'/></td>
                                <td>{dynamicData.author.name + " " + dynamicData.author.lastName}</td>
                            </tr>
                        )}
                        </tbody>
                    </Table>




                </MuiThemeProvider>
            </Jumbotron>;

        let contentTopic =
            <Jumbotron>
                <Table bordered striped hover>
                    <thead>
                    <tr>
                        <th>Nazwa</th>
                        <th>Opis</th>
                        <th>Data Utworzenia</th>
                        <th>Autor</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.topics.map((dynamicData, key) =>
                        <tr>

                            <td> <Link to={"/main/answer?topic=" + dynamicData.idTopic}>{dynamicData.name}</Link></td>
                            <td>{dynamicData.description}</td>
                            <td><Timestamp time={(dynamicData.publishDate) / 1000} format='date'/></td>
                            <td>{dynamicData.author.name + " " + dynamicData.author.lastName}</td>
                        </tr>
                    )}
                    </tbody>
                </Table>

            </Jumbotron>;
        let contentCircle =
            <Jumbotron>
                <MuiThemeProvider>



                    <Table bordered striped hover>
                        <thead>
                        <tr>
                            <th>Nazwa</th>
                            <th>Opis</th>
                            <th>Data Utworzenia</th>
                            <th>Autor</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.state.circles.map((dynamicData, key) =>
                            <tr>

                                <td> <Link to={"/main/topic?circle=" + dynamicData.idCircle}>{dynamicData.name}</Link></td>
                                <td>{dynamicData.description}</td>
                                <td><Timestamp time={(dynamicData.publishDate) / 1000} format='date'/></td>
                                <td>{dynamicData.author.name + " " + dynamicData.author.lastName}</td>
                            </tr>
                        )}
                        </tbody>
                    </Table>




                </MuiThemeProvider>
            </Jumbotron>;


        let loader =
            <MuiThemeProvider>
                <div align="center">
                    <CircularProgress/>
                </div>
            </MuiThemeProvider>;

        return (

            <div>
                <MuiThemeProvider>
                    <NavBar/>
                    <link rel="stylesheet" href="/css/circle.css"/>


                    <Row className="show-grid">
                        <Col xs={1} md={1}/>

                        <Col xs={10} md={10}>


                            <Breadcrumb>
                                <Breadcrumb.Item href="/main/circle">
                                    Strona Główna
                                </Breadcrumb.Item>
                                <Breadcrumb.Item href="/main/circle" active>
                                    Wyszukiwanie
                                </Breadcrumb.Item>
                            </Breadcrumb>
                            <Tabs
                                value={this.state.value}
                                onChange={this.handleChange}
                            >
                                <Tab label="Znalezione Koła" value="a">
                                    {this.state.isLoadingCircle ? loader : contentCircle}
                                </Tab>
                                <Tab label="Znalezione Tematy" value="b">
                                    {this.state.isLoadingTopic ? loader : contentTopic}
                                </Tab>
                                <Tab label="Znalezione Odpowiedzi" value="c">
                                    {this.state.isLoadingAnswers ? loader : contentAnswer}
                                </Tab>
                            </Tabs>

                        </Col>

                        <Col xs={1} md={1}/>
                    </Row>


                </MuiThemeProvider>
            </div>

        )
    }
}

export default Searching