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

let header = {
    "Content-Type": "application/json"
};

// /*{this.state.circles.map((dynamicData,key)=>*/
// /*{dynamicData.name}:*/
// /*{dynamicData.lastName}*/

class Circle extends React.Component {


    constructor() {
        super();
        this.saveChanges = this.handleSaveChanges.bind(this);
        this.closeModal = this.handleClose.bind(this);
        this.openDialog = this.handleOpen.bind(this);
        this.state = {
            newCircle:[],
            circles: [],
            circleName: "",
            circleNameValid: false,
            circleDescription: "",
            circleDescriptionValid: false,
            formErrors: {circleName: '', circleDescription: ''},
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
        let circleNameValid = this.state.circleNameValid;
        let circleDescriptionValid = this.state.circleDescriptionValid;


        switch (fieldName) {
            case 'circleName':
                circleNameValid = value.length >= 3 && value.length <= 40;
                fieldValidationErrors.circleName = circleNameValid ? '' : 'Nie poprawna długość';
                break;
            case 'circleDescription':
                circleDescriptionValid = value.length >= 5 && value.length <= 120;

                fieldValidationErrors.circleDescription = circleDescriptionValid ? '' : "Nie poprawna długość";
                break;
            default:
                break;
        }
        this.setState({

            formErrors: fieldValidationErrors,
            circleNameValid: circleNameValid,
            circleDescriptionValid: circleDescriptionValid,
        }, this.validateForm);
    }

    validateForm() {
        this.setState({formValid: this.state.circleNameValid && this.state.circleDescriptionValid});
    }

    handleOpen() {
        this.setState({open: true});
    };


    handleClose() {
        this.setState({open: false});
    };

    componentDidMount() {
        fetch(url + "/react/main/getCircle", {
            method: 'POST',
            body: "",
            headers: header,
            credentials: 'same-origin'
        }).then((Response) => Response.json()).then((findresponse) => {
            this.setState({
                circles: findresponse,
                isLoading: false
            })
        })
    }

    handleSaveChanges() {
        var myHeaders = new Headers({"Content-Type": "application/json"});

        fetch(url + '/react/main/addCircle', {
            method: 'POST',
            headers: myHeaders,
            credentials: 'same-origin',
            body: JSON.stringify({
                name: this.state.circleName,
                description: this.state.circleDescription
            })

        })
            .then(function (response) {
                return response.json()
            }).then((findresponse) => {
            this.setState({
                newCircle: findresponse
            });
            return findresponse
        }).then((findresponse) => {
            let circles = this.state.circles;
            let newCircle = this.state.newCircle;
            let newCircles = newCircle.concat(circles);
            this.setState(
                {circles:newCircles}
            );
        }).then(() => {
            this.setState({
                open: false,
                circleName: "",
                circleDescription: ""
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
                {this.state.circles.map((dynamicData, key) => (
                    <Panel key={key}>
                        <Grid>
                            <Row className="show-grid">
                                <Col xs={12} md={8}>
                                    <Link to={"/main/topic?circle=" + dynamicData.idCircle}>
                                        <h4>
                                            {dynamicData.name}
                                        </h4>
                                    </Link>

                                    {dynamicData.description}
                                </Col>
                                <Col xs={6} md={2}>
                                    <h4>{dynamicData.countTopic}</h4> Tematów

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
                ))}
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
                    <Col xs={6} md={2}/>

                    <Col xs={6} md={8}>

                        <Breadcrumb>
                            <Breadcrumb.Item href="/" active>
                                Strona Główna
                            </Breadcrumb.Item>
                        </Breadcrumb>
                        <Panel id="mainPanel">
                            <h4>
                                Koła zainteresowań
                            </h4>
                        </Panel>
                        <div id={"circles"}>
                        </div>
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
                                        label={<strong>Dodaj koło</strong>} primary={true} onClick={this.openDialog}/>
                        </div>

                    </div>


                    <Dialog
                        title="Dodaj krąg"
                        actions={actions}
                        modal={true}
                        open={this.state.open}
                        onRequestClose={this.handleClose}
                    >
                        <div className={"dialog"}>
                            <TextField
                                floatingLabelText="Nazwa kręgu"


                                name="circleName"
                                id="circleName" value={this.state.circleName}

                                errorText={this.state.formErrors.circleName}
                                onChange={(event) => this.handleUserInput(event)}
                            /><br/>
                            <TextField
                                floatingLabelText="Krótki opis kręgu"
                                multiLine={true}
                                name="circleDescription"
                                hintText={"Opis musi mieć minimum 5 liter i nie więcej jak 120"}

                                id="circleDescription" value={this.state.circleDescription}
                                errorText={this.state.formErrors.circleDescription}
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

export default Circle