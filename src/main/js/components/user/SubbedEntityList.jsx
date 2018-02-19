import React, {Component} from 'react';
import NavBar from './../obligatory/NavBar';
import {Breadcrumb, Col, Grid, Jumbotron, Panel, Row, Table} from "react-bootstrap";
import {
    Checkbox, CircularProgress, Dialog, FlatButton, MuiThemeProvider, RaisedButton, Tab,
    Tabs
} from "material-ui";

import Timestamp from 'react-timestamp';
import {url} from '../../Constants';
import Link from "react-router-dom/es/Link";

let header = {
    "Content-Type": "application/json"
};

// /*{this.state.circles.map((dynamicData,key)=>*/
// /*{dynamicData.name}:*/
// /*{dynamicData.lastName}*/

class SubbedEntityList extends React.Component {


    constructor() {
        super();
        this.saveChangesCircle = this.handleSaveChangesCircle.bind(this);
        this.saveChangesTopic = this.handleSaveChangesTopic.bind(this);
        this.closeModal = this.handleClose.bind(this);
        this.openDialogCircle = this.handleOpenCircle.bind(this);
        this.openDialogTopic = this.handleOpenTopic.bind(this);
        this.state = {
            subbedCircle: [],
            subbedTopic: [],
            isLoadingCircle: true,
            isLoadingTopic: true,
            circleInTopic: "",
            unSubListCircle: [],
            unSubListTopic: [],
            openCircle: false,
            openTopic: false,
            status: [],
            circleKeys:[],
            topicKeys:[],
            uncheckBoxCircle:false,
            uncheckBoxTopic:false,
        };

    }

    handleUnSubCircle(checked, id,key) {

        if (checked) {
            this.state.unSubListCircle.push(id);
            this.state.circleKeys.push(key);
        } else {
            this.state.unSubListCircle.splice(this.state.unSubListCircle.indexOf(id), 1);
            this.state.circleKeys.splice(this.state.circleKeys.indexOf(key),1);

        }

    }

    handleUnSubTopic(checked, id,key) {

        if (checked) {
            this.state.unSubListTopic.push(id);
            this.state.topicKeys.push(key);
        } else {
            this.state.unSubListTopic.splice(this.state.unSubListTopic.indexOf(id), 1);
            this.state.topicKeys.splice(this.state.topicKeys.indexOf(key),1);

        }

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
                    openCircle: false,
                });
                // window.location.replace(url + "/main/subbed");
            }).then(()=>{
                let newCircleList = [];
                let reversedSubbedList=[];
                let i;
                for (i=0;i<this.state.circleKeys.length;i++)
                {
                    newCircleList.push(this.state.subbedCircle[this.state.circleKeys[i]]);
                }
                for (i=0;i<this.state.subbedCircle.length; i++)
                {
                    if(!newCircleList.includes(this.state.subbedCircle[i])) {
                        reversedSubbedList.push(this.state.subbedCircle[i]);
                    }
                }
                // let intersection = newCircleList.filter(x => this.state.unSubListCircle.includes(x));
                this.setState({
                    subbedCircle:reversedSubbedList,
                    uncheckBoxCircle:false,
                    isLoadingCircle:true,
                })
            }).then(()=> {
                this.setState({
                    circleKeys: [],
                    isLoadingCircle:false
                })
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
                // window.location.replace(url + "/main/subbed");
            }).then(()=> {
                let newTopicList = [];
                let reversedSubbedList = [];
                let i;
                for (i = 0; i < this.state.topicKeys.length; i++) {
                    newTopicList.push(this.state.subbedTopic[this.state.topicKeys[i]]);
                }
                for (i = 0; i < this.state.subbedTopic.length; i++) {
                    if (!newTopicList.includes(this.state.subbedTopic[i])) {
                        reversedSubbedList.push(this.state.subbedTopic[i]);
                    }
                }
                // let intersection = newCircleList.filter(x => this.state.unSubListCircle.includes(x));
                this.setState({
                    subbedTopic: reversedSubbedList,
                    uncheckBoxTopic:false,
                    isLoadingTopic:true,
                })
            }).then(()=>{
                this.setState({
                    topicKeys: [],
                    isLoadingTopic:false,


                })
            })
        } else {
            this.handleClose();
        }
    }


    componentDidMount() {
        fetch(url + "/react/main/subbedCircle", {
            method: 'POST',
            body: "",
            headers: header,
            credentials: 'same-origin'
        }).then((Response) => Response.json()).then((findresponse) => {
            this.setState({
                subbedCircle: findresponse,
                isLoadingCircle: false
            })
        });
        fetch(url + "/react/main/subbedTopic", {
            method: 'POST',
            body: "",
            headers: header,
            credentials: 'same-origin'
        }).then((Response) => Response.json()).then((findresponse) => {
            this.setState({
                subbedTopic: findresponse,
                isLoadingTopic: false

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
        const actionsCircle = [
            <MuiThemeProvider>
                <FlatButton
                    label="Anuluj"
                    primary={true}
                    onClick={this.closeModal}
                />,
                <FlatButton
                    label="Potwierdź"
                    primary={true}
                    keyboardFocused={true}
                    onClick={this.saveChangesCircle}
                />,
            </MuiThemeProvider>
        ];
        const actionTopic = [
            <MuiThemeProvider>
                <FlatButton
                    label="Anuluj"
                    primary={true}
                    onClick={this.closeModal}
                />,
                <FlatButton
                    label="Potwierdź"
                    primary={true}
                    keyboardFocused={true}
                    onClick={this.saveChangesTopic}
                />,
            </MuiThemeProvider>
        ];
        let contentTopic =
            <Jumbotron>
                <Table bordered striped hover>
                    <thead>
                    <tr>
                        <th></th>
                        <th>Nazwa</th>
                        <th>Opis</th>
                        <th>Data Utworzenia</th>
                        <th>Autor</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.subbedTopic.map((dynamicData, key) =>
                        <tr>
                            <td>
                                <Checkbox
                                    defaultChecked={this.state.uncheckBox}
                                    onCheck={(event, isChecked) => this.handleUnSubTopic(isChecked, dynamicData.idTopic,key)}/>
                            </td>
                            <td> <Link to={"/main/answer?topic=" + dynamicData.idTopic}>{dynamicData.name}</Link></td>
                            <td>{dynamicData.description}</td>
                            <td><Timestamp time={(dynamicData.publishDate) / 1000} format='date'/></td>
                            <td>{dynamicData.author.name + " " + dynamicData.author.lastName}</td>
                        </tr>
                    )}
                    </tbody>
                </Table>
                <RaisedButton label="Usuń Zaznaczone subskrypcje" onClick={this.openDialogTopic}
                              fullWidth={true}/>
            </Jumbotron>;
        let contentCircle =
            <Jumbotron>
                <MuiThemeProvider>



                            <Table bordered striped hover>
                                <thead>
                                <tr>
                                    <th></th>
                                    <th>Nazwa</th>
                                    <th>Opis</th>
                                    <th>Data Utworzenia</th>
                                    <th>Autor</th>
                                </tr>
                                </thead>
                                <tbody>
                                {this.state.subbedCircle.map((dynamicData, key) =>
                                    <tr>
                                        <td>
                                            <Checkbox
                                                defaultChecked={this.state.uncheckBoxTopic}
                                                onCheck={(event, isChecked) => this.handleUnSubCircle(isChecked, dynamicData.idCircle,key)}/>
                                        </td>
                                        <td> <Link to={"/main/topic?circle=" + dynamicData.idCircle}>{dynamicData.name}</Link></td>
                                        <td>{dynamicData.description}</td>
                                        <td><Timestamp time={(dynamicData.publishDate) / 1000} format='date'/></td>
                                        <td>{dynamicData.author.name + " " + dynamicData.author.lastName}</td>
                                    </tr>
                                )}
                                </tbody>
                            </Table>
                            <RaisedButton label="Usuń Zaznaczone subskrypcje" onClick={this.openDialogCircle}
                                          fullWidth={true}/>



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
                                Subskrybowane
                            </Breadcrumb.Item>
                        </Breadcrumb>
                        <Tabs
                            value={this.state.value}
                            onChange={this.handleChange}
                        >
                            <Tab label="Subskrybowane Koła" value="a">
                                {this.state.isLoadingCircle ? loader : contentCircle}
                            </Tab>
                            <Tab label="Subskrybowane Tematy" value="b">
                                {this.state.isLoadingTopic ? loader : contentTopic}
                            </Tab>
                        </Tabs>

                    </Col>

                    <Col xs={1} md={1}/>
                </Row>


                    <Dialog
                        title="Usunąć Subksrypcje?"
                        actions={actionsCircle}
                        modal={true}
                        open={this.state.openCircle}
                        onRequestClose={this.handleClose}
                    >
                        Potwierdzając usuniesz zaznaczone subskrypcje.
                    </Dialog>
                    <Dialog
                        title="Usunąć Subksrypcje?"
                        actions={actionTopic}
                        modal={true}
                        open={this.state.openTopic}
                        onRequestClose={this.handleClose}
                    >
                        Potwierdzając usuniesz zaznaczone subskrypcje.
                    </Dialog>
                </MuiThemeProvider>
            </div>

        )
    }
}

export default SubbedEntityList