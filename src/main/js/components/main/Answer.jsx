import React, {Component} from 'react';
import NavBar from './../obligatory/NavBar';
import {Breadcrumb, Col, Grid, Jumbotron, Panel, Row} from "react-bootstrap";
import {
    Avatar, CircularProgress, Dialog, FlatButton, FloatingActionButton, List, ListItem,
    MuiThemeProvider, TextField
} from "material-ui";
import ContentAdd from 'material-ui/svg-icons/content/add';
import {Link} from "react-router-dom";
import {url} from '../../Constants';
import ReactQueryParams from 'react-query-params';
import Timestamp from 'react-timestamp';


let header = {
    "Content-Type": "application/json"
};

// /*{this.state.circles.map((dynamicData,key)=>*/
// /*{dynamicData.name}:*/
// /*{dynamicData.lastName}*/

class Answer extends ReactQueryParams {


    constructor() {
        super();
        this.saveChanges = this.handleSaveChanges.bind(this);
        this.closeModal = this.handleClose.bind(this);
        this.openDialog = this.handleOpen.bind(this);
        this.state = {
            circle: [],
            topic: [],
            answers: [],
            answerName: "",
            answerNameValid: false,
            formErrors: {answerName: ''},
            formValid: false,
            isLoading: true,
            open: false,
        };
    }


    handleUserInput(e) {
        const name = e.target.name;
        const value = e.target.value;
        this.setState({[name]: value},
            () => {
                this.validateField(name, value)
            });
    }

    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;
        let answerNameValid = this.state.answerNameValid;


        switch (fieldName) {
            case 'answerName':
                answerNameValid = value.length >= 2 && value.length <= 250;
                fieldValidationErrors.answerName = answerNameValid ? '' : 'Nie poprawna długość';
                break;
            default:
                break;
        }
        this.setState({

            formErrors: fieldValidationErrors,
            answerNameValid: answerNameValid
        }, this.validateForm);
    }

    validateForm() {
        this.setState({formValid: this.state.answerNameValid});
    }

    handleOpen() {
        this.setState({open: true});
    };


    handleClose() {
        this.setState({open: false});
    };

    componentDidMount() {
        fetch(url + "/react/main/getAnswers", {
            method: 'POST',
            body: JSON.stringify({
                topicIdTopic: this.queryParams.topic
            }),
            headers: header,
            credentials: 'same-origin'
        }).then((Response) => Response.json()).then((findresponse) => {
            this.setState({
                answers: findresponse,
                isLoading: false
            })
        });
        fetch(url + "/react/main/getOneTopic", {
            method: 'POST',
            body: JSON.stringify({
                id: this.queryParams.topic
            }),
            headers: header,
            credentials: 'same-origin'
        }).then((Response) => Response.json()).then((findresponse) => {
            this.setState({
                topic: findresponse,
            })
        }).then(() => {
            fetch(url + "/react/main/getOneCircleFromTopic", {
                method: 'POST',
                body: JSON.stringify({
                    id: this.queryParams.topic
                }),
                headers: header,
                credentials: 'same-origin'
            }).then((Response) => Response.json()).then((findresponse) => {
                this.setState({
                    circle: findresponse,
                })
            })
        });
    }

    handleSaveChanges() {
        var myHeaders = new Headers({"Content-Type": "application/json"});

        fetch(url + '/react/main/addAnswer', {
            method: 'POST',
            headers: myHeaders,
            credentials: 'same-origin',
            body: JSON.stringify({
                content: this.state.answerName,
                topicIdTopic: this.queryParams.topic
            })

        })
            .then(function (response) {
                return response.json()
            }).then((findresponse) => {
            this.setState({
                stat: findresponse
            });
            return findresponse;
        }).then((findresponse) => {
            const answers = this.state.answers.concat(findresponse);
            this.setState({answers});
        }).then(() => {
            this.setState({
                open: false,
                answerName: "",
                formValid:false
            });
        });


    }

    render() {
        // const style = {
        //     marginTop: 20,
        //     marginRight:20,
        //     marginBottom:10,
        //     marginLeft:20,
        //     top: 'auto',
        //     right: 0,
        //     bottom: 20,
        //     left: 'auto',
        //     position: 'fixed',
        // };
        const actions = [
            <FlatButton
                label="Anuluj"
                primary={true}
                onClick={this.closeModal}
            />,
            <FlatButton
                label="Potwierdź"
                primary={true}
                keyboardFocused={true}
                onClick={this.saveChanges}
                disabled={!this.state.formValid}
            />,
        ];
        let content =
            <Jumbotron>
                {this.state.answers.map((dynamicData, key) =>
                    <Panel key={key}>
                        <Grid>
                            <Row className="show-grid">
                                <Col xs={8} md={8}>


                                        {dynamicData.content}



                                </Col>
                                <Col xs={2} md={2}>
                                    <h5><Timestamp time={(dynamicData.publishDate) / 1000} format='full'/> </h5>Data publikacji

                                </Col>
                                <Col xs={1}  md={1}>
                                    <MuiThemeProvider>
                                        <List>
                                            <ListItem disabled={true}
                                                      leftAvatar={<Avatar src={dynamicData.author.avatar}/>}
                                                      primaryText={<Link
                                                          to={"/main/profile?user=" + dynamicData.author.idUser}>{dynamicData.author.name + " " + dynamicData.author.lastName}</Link>}>
                                            </ListItem>


                                        </List>

                                    </MuiThemeProvider>
                                </Col>
                                <Col md={1}></Col>
                            </Row>
                        </Grid>

                    </Panel>
                )}
            </Jumbotron>;
        let loader = <MuiThemeProvider>
            <div align="center">
                <CircularProgress/>
            </div>
        </MuiThemeProvider>;

        return (



            <div>
                <NavBar/>
                <link rel="stylesheet" href="/css/answer.css"/>


                <Row className="show-grid">
                    <Col md={1}/>

                    <Col md={10}>

                        <Breadcrumb>
                            <Breadcrumb.Item href="/main/circle">
                                Strona Główna
                            </Breadcrumb.Item>
                            <Breadcrumb.Item href={"/main/topic?circle=" + this.state.circle.idCircle}>
                                {this.state.circle.name}
                            </Breadcrumb.Item>
                            <Breadcrumb.Item active>
                                {this.state.topic.name}
                            </Breadcrumb.Item>
                        </Breadcrumb>
                        <Panel id="mainPanel">
                            <h4>
                                Odpowiedzi
                            </h4>
                        </Panel>
                        {this.state.isLoading ? loader : content}


                    </Col>

                    <Col md={1}/>
                </Row>
                <MuiThemeProvider>

                    <div className={"fab"} align={"center"}>

                        <FloatingActionButton onClick={this.openDialog}>
                            <ContentAdd/>
                        </FloatingActionButton>
                        <div className={"buttonUnderFab"}>
                            <FlatButton hoverColor={"transparent"} disableTouchRipple={true}
                                        label={<strong>Dodaj Odpowiedź</strong>} primary={true}
                                        onClick={this.openDialog}/>
                        </div>
                    </div>
                    <Dialog
                        title="Dodaj Odpowiedź"
                        actions={actions}
                        modal={true}
                        open={this.state.open}
                        onRequestClose={this.handleClose}
                    >
                        <div className={"dialog"}>
                            <TextField
                                floatingLabelText="Odpowiedz"
                                name="answerName"
                                id="answerName" value={this.state.answerName}
                                multiLine={true}
                                errorText={this.state.formErrors.answerName}
                                onChange={(event) => this.handleUserInput(event)}
                                rows={3}
                            /><br/>

                        </div>
                    </Dialog>
                </MuiThemeProvider>
            </div>

        )
    }
}

export default Answer