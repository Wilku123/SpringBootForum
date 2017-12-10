import React, {Component} from 'react';
import NavBar from './../obligatory/NavBar';
import {Breadcrumb, Col, Grid, Jumbotron, Panel, Row, Table} from "react-bootstrap";
import {
    Checkbox, CircularProgress, Dialog, FlatButton, MuiThemeProvider, RaisedButton, Tab,
    Tabs
} from "material-ui";
import ActionFavorite from 'material-ui/svg-icons/action/favorite';
import ActionFavoriteBorder from 'material-ui/svg-icons/action/favorite-border';
import Timestamp from 'react-timestamp';
import {Link} from "react-router-dom";
import {url} from '../../Constants';

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
            isLoading: true,
            circleInTopic: "",
            unSubListCircle: [],
            unSubListTopic: [],
            openCircle: false,
            openTopic: false,
            status: []
        };

    }

    handleUnSubCircle(checked, id) {

        if (checked) {
            this.state.unSubListCircle.push(id);
        } else {
            this.state.unSubListCircle.splice(this.state.unSubListCircle.indexOf(id), 1);
        }

    }
    handleUnSubTopic(checked, id) {

        if (checked) {
            this.state.unSubListTopic.push(id);
        } else {
            this.state.unSubListTopic.splice(this.state.unSubListTopic.indexOf(id), 1);
        }

    }

    handleOpenCircle() {
        this.setState({openCircle: true});
    };
    handleOpenTopic() {
        this.setState({openTopic: true});
    };

    handleClose() {
        this.setState({openCircle: false, openTopic:false});
    };

    handleSaveChangesCircle() {

        if (this.state.unSubListCircle.length>0) {
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






        if (this.state.unSubListTopic.length>0) {
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
        fetch(url + "/react/main/subbedCircle", {
            method: 'POST',
            body: "",
            headers: header,
            credentials: 'same-origin'
        }).then((Response) => Response.json()).then((findresponse) => {
            this.setState({
                subbedCircle: findresponse,
                isLoading: false
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
                isLoading: false
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
        let content =
            <Jumbotron>
                <MuiThemeProvider>


                    <Tabs
                        value={this.state.value}
                        onChange={this.handleChange}
                    >
                        <Tab label="Subskrybowane Koła" value="a">
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
                                                onCheck={(event, isChecked) => this.handleUnSubCircle(isChecked, dynamicData.idCircle)}/>
                                        </td>
                                        <td>{dynamicData.name}</td>
                                        <td>{dynamicData.description}</td>
                                        <td><Timestamp time={(dynamicData.publishDate)/1000} format='date'/></td>
                                        <td>{dynamicData.author.name + " " + dynamicData.author.lastName}</td>
                                    </tr>
                                )}
                                </tbody>
                            </Table>
                            <RaisedButton label="Usuń Zaznaczone subskrypcje" onClick={this.openDialogCircle} fullWidth={true}/>
                        </Tab>
                        <Tab label="Subskrybowane Tematy" value="b">
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
                                                onCheck={(event, isChecked) => this.handleUnSubTopic(isChecked, dynamicData.idTopic)}/>
                                        </td>
                                        <td>{dynamicData.name}</td>
                                        <td>{dynamicData.description}</td>
                                        <td><Timestamp time={dynamicData.publishDate.toString()} format='date'/></td>
                                        <td>{dynamicData.author.name + " " + dynamicData.author.lastName}</td>
                                    </tr>
                                )}
                                </tbody>
                            </Table>
                            <RaisedButton label="Usuń Zaznaczone subskrypcje" onClick={this.openDialogTopic} fullWidth={true}/>
                        </Tab>
                    </Tabs>


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
                <link rel="stylesheet" href="../../css/circle.css"/>


                <Row className="show-grid">
                    <Col xs={6} md={1}/>

                    <Col xs={6} md={10}>


                        <Breadcrumb>
                            <Breadcrumb.Item href="/main/circle">
                                Strona Główna
                            </Breadcrumb.Item>
                            <Breadcrumb.Item href="/main/circle" active>
                                Subskrybowane
                            </Breadcrumb.Item>
                        </Breadcrumb>
                        {this.state.isLoading ? loader : content}


                    </Col>

                    <Col xsHidden md={1}/>
                </Row>

                <MuiThemeProvider>
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