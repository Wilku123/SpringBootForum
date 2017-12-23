import React, {Component} from 'react';
import NavBar from './../obligatory/NavBar';
import {Breadcrumb, Col, Grid, Jumbotron, Panel, Row} from "react-bootstrap";
import {
    Avatar, CircularProgress, Dialog, Divider, FlatButton, FloatingActionButton, List, ListItem,
    MuiThemeProvider, TextField
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

class Topic extends ReactQueryParams {


    constructor() {
        super();
        this.saveChanges = this.handleSaveChanges.bind(this);
        this.closeModal = this.handleClose.bind(this);
        this.openDialog = this.handleOpen.bind(this);
        this.state = {
            stat:[],
            circle: [],
            topics: [],
            topicName: "",
            topicNameValid: false,
            topicDescription: "",
            topicDescriptionValid: false,
            formErrors: {topicName: '', topicDescription: ''},
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
        let topicNameValid = this.state.topicNameValid;
        let topicDescriptionValid = this.state.topicDescriptionValid;


        switch (fieldName) {
            case 'topicName':
                topicNameValid = value.length >= 3 && value.length <= 40;
                fieldValidationErrors.topicName = topicNameValid ? '' : 'Nie poprawna długość';
                break;
            case 'topicDescription':
                topicDescriptionValid = value.length >= 5 && value.length <= 120;

                fieldValidationErrors.topicDescription = topicDescriptionValid ? '' : "Nie poprawna długość";
                break;
            default:
                break;
        }
        this.setState({

            formErrors: fieldValidationErrors,
            topicNameValid: topicNameValid,
            topicDescriptionValid: topicDescriptionValid,
        }, this.validateForm);
    }

    validateForm() {
        this.setState({formValid: this.state.topicNameValid && this.state.topicDescriptionValid});
    }

    handleOpen() {
        this.setState({open: true});
    };


    handleClose() {
        this.setState({open: false});
    };

    componentDidMount() {
        fetch(url + "/react/main/getTopic", {
            method: 'POST',
            body: JSON.stringify({
                circleIdCircle: this.queryParams.circle
            }),
            headers: header,
            credentials: 'same-origin'
        }).then((Response) => Response.json()).then((findresponse) => {
            this.setState({
                topics: findresponse,
                isLoading: false
            })
        });
        fetch(url + "/react/main/getOneCircle", {
            method: 'POST',
            body: JSON.stringify({
                id: this.queryParams.circle
            }),
            headers: header,
            credentials: 'same-origin'
        }).then((Response) => Response.json()).then((findresponse) => {
            this.setState({
                circle: findresponse,
            })
        })
    }

    handleSaveChanges() {
        var myHeaders = new Headers({"Content-Type": "application/json"});

        fetch(url + '/react/main/addTopic', {
            method: 'POST',
            headers: myHeaders,
            credentials: 'same-origin',
            body: JSON.stringify({
                name: this.state.topicName,
                description: this.state.topicDescription,
                circleIdCircle: this.queryParams.circle
            })

        })
            .then(function (response) {
                return response.json()
            }).then((findresponse) => {
            this.setState({
                stat: findresponse,
            });
            return findresponse
        }).then((findresponse) => {
            let topics = this.state.topics;
            let newTopic = this.state.stat;
            let newTopics = newTopic.concat(topics);
            // const topics = this.state.topics.concat(findresponse);
            this.setState(
                {topics:newTopics}
            );
        }).then(() => {
            this.setState({
                open: false,
                // isLoading: true,
                topicName: "",
                topicDescription: ""
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
                {this.state.topics.map((dynamicData, key) =>
                    <Panel key={key}>
                        <Grid>
                            <Row className="show-grid">
                                <Col xs={12} md={8}>
                                    <Link to={"/main/answer?topic=" + dynamicData.idTopic}>
                                        <h4>
                                            {dynamicData.name}
                                        </h4>
                                    </Link>

                                    {dynamicData.description}
                                </Col>
                                <Col xs={6} md={2}>
                                    <h4>{dynamicData.countAnswer}</h4> Odpowiedzi

                                </Col>
                                <Col xs={6} md={2}>
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
                <link rel="stylesheet" href="../../css/answer.css"/>


                <Row className="show-grid">
                    <Col xs={6} md={2}/>

                    <Col xs={6} md={8}>

                        <Breadcrumb>
                            <Breadcrumb.Item href="/main/circle">
                                Strona Główna
                            </Breadcrumb.Item>
                            <Breadcrumb.Item active>
                                {this.state.circle.name}
                            </Breadcrumb.Item>
                        </Breadcrumb>
                        <Panel id="mainPanel">
                            <h4>
                                Aktwyne tematy
                            </h4>
                        </Panel>
                        {this.state.isLoading ? loader : content}


                    </Col>

                    <Col xsHidden md={2}/>
                </Row>
                <MuiThemeProvider>

                    <div className={"fab"} align={"center"}>

                        <FloatingActionButton onClick={this.openDialog}>
                            <ContentAdd/>
                        </FloatingActionButton>
                        <div className={"buttonUnderFab"}>
                            <FlatButton hoverColor={"transparent"} disableTouchRipple={true}
                                        label={<strong>Dodaj Temat</strong>} primary={true} onClick={this.openDialog}/>
                        </div>
                    </div>
                    <Dialog
                        title="Dodaj temat"
                        actions={actions}
                        modal={true}
                        open={this.state.open}
                        onRequestClose={this.handleClose}
                    >
                        <div className={"dialog"}>
                            <TextField
                                floatingLabelText="Nazwa tematu"
                                name="topicName"
                                id="topicName" value={this.state.topicName}

                                errorText={this.state.formErrors.topicName}
                                onChange={(event) => this.handleUserInput(event)}
                            /><br/>
                            <TextField
                                floatingLabelText="Krótki opis tematu"
                                multiLine={true}
                                name="topicDescription"
                                hintText={"Opis musi mieć minimum 5 liter i nie więcej jak 120"}
                                id="topicDescription" value={this.state.topicDescription}
                                errorText={this.state.formErrors.topicDescription}
                                onChange={(event) => this.handleUserInput(event)}
                                rows={2}
                            /><br/>
                        </div>
                    </Dialog>
                </MuiThemeProvider>
            </div>

        )
    }
}

export default Topic