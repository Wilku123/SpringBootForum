import React, {Component} from 'react';
import NavBar from './../obligatory/NavBar';
import {
    Breadcrumb, Col, ControlLabel, Form, FormControl, FormGroup, Grid, InputGroup, Jumbotron, Panel, Row,
    Table
} from "react-bootstrap";
import {
    Avatar,
    Checkbox, CircularProgress, Dialog, Divider, FlatButton, List, ListItem, MuiThemeProvider, RaisedButton, Tab,
    Tabs, TextField
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

class Config extends React.Component {


    constructor() {
        super();
        this.link = this.handleForm.bind(this);
        this.saveChanges = this.handleSaveChanges.bind(this);
        this.closeModal = this.handleClose.bind(this);
        this.openDialog = this.handleOpen.bind(this);
        this.loadAvatar = this.handleLoadingAvatar.bind(this);
        this.saveAvatar = this.handleSaveAvatar.bind(this);
        this.state = {
            isLoading: false,
            open: false,
            status: [],
            avatarLink: "",
            avatar: "",
            passwordOld:"",
            oldPasswordValid:false,
            password: '',
            confirmPassword: '',
            formErrors: {password: '', confirmPassword: ''},
            passwordValid: false,
            formValid: false,
            confirmPasswordValid: false,
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
        let passwordValid = this.state.passwordValid;
        let confirmPasswordValid = this.state.confirmPasswordValid;
        let oldPassValid = this.state.oldPasswordValid;



        switch (fieldName) {

            case 'passwordOld':
                oldPassValid=false;
                if (!this.state.status.status) {
                    oldPassValid = true;
                    fieldValidationErrors.passwordOld = oldPassValid ? '' : 'Podano błędne hasło';
                }
                break;

            case 'password':
                passwordValid = value.length >= 8;
                if (value === this.state.confirmPassword.toString()) {
                    confirmPasswordValid = true;
                    fieldValidationErrors.confirmPassword = confirmPasswordValid ? '' : 'Hasła niezgodne';
                }
                else {
                    confirmPasswordValid = false;
                    fieldValidationErrors.confirmPassword = confirmPasswordValid ? '' : 'Hasła niezgodne';
                }
                fieldValidationErrors.password = passwordValid ? '' : ' Hasło za krótkie';
                break;
            case 'confirmPassword':
                if (value === this.state.password.toString())
                    confirmPasswordValid = true;

                else
                    confirmPasswordValid = false;

                fieldValidationErrors.confirmPassword = confirmPasswordValid ? '' : 'Hasła niezgodne';
                break;
            default:
                break;
        }
        this.setState({

            formErrors: fieldValidationErrors,
            passwordValid: passwordValid,
            confirmPasswordValid: confirmPasswordValid,
            oldPasswordValid:oldPassValid
        }, this.validateForm);
        // if(this.state.passO===this.state.passT){
        //     this.state.validPass=false;
        //     this.state.passError="";
        // }else{
        //     this.state.validPass=true;
        //     this.state.passError="Hasła niezgodne";
        // }

    }
    validateForm() {
        this.setState({formValid: this.state.passwordValid && this.state.confirmPasswordValid && this.state.oldPasswordValid});
    }
    handleSaveAvatar() {
        fetch(url + "/react/main/saveAvatar", {
            method: 'POST',
            body: JSON.stringify({
                avatar: this.state.avatarLink
            }),
            headers: header,
            credentials: 'same-origin'
        }).then((Response) => Response.json()).then((findresponse) => {
            this.setState({
                status: findresponse,
                open: true
            })
        })
    }

    handleLoadingAvatar() {
        this.setState({avatar: this.state.avatarLink});

    }

    handleForm(e) {
        this.setState({avatarLink: e.target.value});
    }

    handleOpen() {
        this.setState({open: true});
    };

    handleClose() {
        this.setState({open: false});
        window.location.replace(url + "/main/config");
    };

    handleSaveChanges() {
        let oldPass;
        let oldPassValid = this.state.oldPasswordValid;
        let fieldValidationErrors = this.state.formErrors;
        fetch(url + "/react/main/changePass", {
            method: 'POST',
            body: JSON.stringify({
                oldPass:this.state.passwordOld,
                newPass: this.state.password
            }),
            headers: header,
            credentials: 'same-origin'
        }).then((Response) => Response.json()).then((findresponse) => {
            this.setState({
                status: findresponse
            });
        }
        ).then(() =>{
            oldPass = this.state.status.status;
            // console.log("status",checkUser);
            if (oldPass === false) {
                oldPassValid = false;
                fieldValidationErrors.passwordOld = oldPassValid ? '' : 'Wpisano błędne hasło';
                this.setState({
                    formErrors: fieldValidationErrors,
                    oldPasswordValid: oldPassValid
                }, this.validateForm);
            }else {
                this.setState({open: true});
            }
        });

    }


    componentDidMount() {
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
        const actions = [
            <MuiThemeProvider>
                <FlatButton
                    label="Ok"
                    primary={true}
                    onClick={this.closeModal}
                />,
            </MuiThemeProvider>
        ];
        let content =
            <Jumbotron>
                <MuiThemeProvider>

                    <div>
                        <Panel>
                            <Grid>
                                <Row className="show-grid">
                                    <Col xs={12} md={3}>
                                    </Col>
                                    <Col xs={6} md={6}>


                                        <h3>Ustaw avatar</h3>

                                        <ControlLabel>Avatar</ControlLabel>
                                        <Form>
                                            <FormGroup>
                                                <FormControl
                                                    type="text"
                                                    value={this.state.value}
                                                    placeholder="www.<link>.com/<avatar>.jpg"
                                                    onChange={this.link}
                                                />
                                                <div align="center">
                                                    <RaisedButton label="Potwierdź" primary={true}
                                                                  onClick={this.loadAvatar}/>
                                                </div>
                                            </FormGroup>
                                        </Form>
                                        <div align="center">
                                            <h3>Tak Wygląda twój avatar</h3>
                                            <Avatar size={120} src={this.state.avatar}/>

                                        </div>
                                        <div align="center">
                                            <RaisedButton label="Zapisz Avatar" primary={true}
                                                          onClick={this.saveAvatar}/>
                                        </div>
                                        <Divider style={{marginTop: 15}}/>
                                        <h3>Zmiana Hasła</h3>
                                        <div>
                                            <TextField
                                                hintText=""
                                                floatingLabelText="Wpisz aktualne hasło"
                                                value={this.state.passwordOld}
                                                name={"passwordOld"}
                                                onChange={(event)=>this.handleUserInput(event)}
                                                errorText={this.state.formErrors.passwordOld}
                                                // floatingLabelFixed={true}
                                                type="password"
                                            /><br/>
                                            <TextField
                                                name={"password"}
                                                value={this.state.password}
                                                onChange={(event)=>this.handleUserInput(event)}
                                                hintText=""
                                                floatingLabelText="Wpisz nowe haslo"
                                                errorText={this.state.formErrors.password}
                                                type="password"
                                            /><br/>
                                            <TextField
                                                name={"confirmPassword"}
                                                value={this.state.confirmPassword}
                                                onChange={(event)=>this.handleUserInput(event)}
                                                hintText=""
                                                floatingLabelText="Powtórz hasło"
                                                errorText={this.state.formErrors.confirmPassword}

                                                type="password"
                                            /><br/>

                                            <RaisedButton label="Zmień Hasło" primary={true}
                                            disabled={!this.state.formValid} onClick={this.saveChanges}/>
                                        </div>
                                    </Col>
                                    <Col xs={6} md={3}>

                                    </Col>
                                </Row>
                            </Grid>

                        </Panel>
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
                <link rel="stylesheet" href="../../css/circle.css"/>


                <Row className="show-grid">
                    <Col xs={6} md={1}/>

                    <Col xs={6} md={10}>


                        <Breadcrumb>
                            <Breadcrumb.Item href="/main/circle">
                                Strona Główna
                            </Breadcrumb.Item>
                            <Breadcrumb.Item href="#" active>
                                Ustawienia
                            </Breadcrumb.Item>
                        </Breadcrumb>
                        {this.state.isLoading ? loader : content}


                    </Col>

                    <Col xsHidden md={1}/>
                </Row>

                <MuiThemeProvider>
                    <Dialog
                        title="Zapisano"
                        actions={actions}
                        modal={true}
                        open={this.state.open}
                        onRequestClose={this.handleClose}
                    >
                        Pomyślnie zapisano zmiany w profilu.
                    </Dialog>
                </MuiThemeProvider>
            </div>

        )
    }
}

export default Config