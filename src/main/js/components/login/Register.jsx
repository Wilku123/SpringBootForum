import React from 'react';
import {BrowserRouter as Router, Route, Link} from 'react-router-dom';
import {FormErrors} from "./FormErrors";
import {url} from '../../Constants';
import {Dialog, FlatButton, MuiThemeProvider, RaisedButton} from "material-ui";
// const FormErrors= require('./FormErrors.jsx');



class Home extends React.Component {

    constructor(props) {
        super(props);
        this.modalOpen = this.handleOpen.bind(this);
        this.modalClose = this.handleClose.bind(this);
        this.onSubmit = this.handleSubmit.bind(this);
        this.state = {
            stat: {status:""},
            name: '',
            lastName: '',
            email: '',
            emailExists: '',
            password: '',
            confirmPassword: '',
            formErrors: {name: '', lastName: '', email: '',emailExists:'', password: '', confirmPassword: ''},
            nameValid: false,
            lastNameValid: false,
            emailValid: false,
            passwordValid: false,
            formValid: false,
            confirmPasswordValid: false,
            open:false
        }
    }
    handleOpen(){
        this.setState({});
    };

    handleClose () {
        this.setState({open: false});

        window.location.replace(url+"/login");
    };
    errorClass(error) {
        return (error.length === 0 ? '' : 'has-error' );
    }

    componentDidMount() {
        // this.state={stat: []};
    }


    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;
        let nameValid = this.state.nameValid;
        let lastNameValid = this.state.lastNameValid;
        let emailValid = this.state.emailValid;
        let passwordValid = this.state.passwordValid;
        let confirmPasswordValid = this.state.confirmPasswordValid;


        switch (fieldName) {
            case 'email':
                // emailValid = value.match(/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i);
                emailValid = value.match(/^([\w.%+-]+)@us+\.edu+\.pl$/i);
                fieldValidationErrors.email = emailValid ? '' : ' Nie prawidłowy adres E-mail';
                fieldValidationErrors.emailExists ="";
                break;
            case 'name':
                nameValid = value.length >= 3;
                fieldValidationErrors.name = nameValid ? '' : "Imię za krótkie";
                break;
            case 'lastName':
                lastNameValid = value.length >= 3;
                fieldValidationErrors.lastName = lastNameValid ? '' : "Nazwisko za krótkie";
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
            emailValid: emailValid,
            passwordValid: passwordValid,
            confirmPasswordValid: confirmPasswordValid,
            nameValid: nameValid,
            lastNameValid: lastNameValid
        }, this.validateForm);
    }

    validateForm() {
        this.setState({formValid: this.state.emailValid && this.state.passwordValid && this.state.confirmPasswordValid && this.state.nameValid && this.state.lastNameValid});
    }

    handleUserInput(e) {
        const name = e.target.name;
        const value = e.target.value;
        this.setState({[name]: value},
            () => {
                this.validateField(name, value)
            });
    }

    handleSubmit(e) {
        e.preventDefault();


        let checkUser;
        let emailValid = this.state.emailValid;
        let fieldValidationErrors = this.state.formErrors;
        var myHeaders = new Headers({"Content-Type": "application/json"});

        fetch(url + '/react/register', {
            method: 'POST',
            headers: myHeaders,
            body: JSON.stringify({
                name: this.state.name,
                lastName: this.state.lastName,
                email: this.state.email,
                password: this.state.password
            })
        })
            .then(function (response) {
                return response.json()
            }).then((findresponse) => {
            this.setState({
                stat: findresponse
            })
        }).then(() =>{
            checkUser = this.state.stat.status;
            // console.log("status",checkUser);
            if (checkUser === true) {
                emailValid = false;
                fieldValidationErrors.emailExists = emailValid ? '' : 'Adres E-mail już istnieje';
                this.setState({
                    formErrors: fieldValidationErrors,
                    emailValid: emailValid
                }, this.validateForm);
            }else {
                this.setState({open: true});
            }
        });

        // checkUser = this.state.stat.status;
        //
        //
        // console.log(checkUser);
        // if (checkUser === true) {
        //     emailValid = false;
        //     fieldValidationErrors.emailExists = emailValid ? '' : 'Adres E-mail już istnieje';
        //     this.setState({
        //         formErrors: fieldValidationErrors,
        //         emailValid: emailValid
        //     }, this.validateForm);
        // }
        // else {
        //     emailValid = false
        // }





    }


    render() {
        const actions =

            <FlatButton
                label="Ok"
                primary={true}
                keyboardFocused={true}
                onClick={this.modalClose}
            />;

        return (
            <div>
                <MuiThemeProvider>
                <link rel="stylesheet" type="text/css" href="/../../css/loginPage.css"/>

                <div className="container-fluid">
                    <div className="row">
                        <div className="col-md-2">
                        </div>
                        <div className="col-md-8">

                            <div id="octagonWrap">
                                <div id="octagon">
                                    <form role="form" autoComplete="off" className="jumbotron" onSubmit={this.onSubmit}>

                                        <div
                                            className={`form-group has-feedback ${this.errorClass(this.state.formErrors.name)}`}>
                                            <label htmlFor="name">
                                                Imie
                                            </label>
                                            <input type="text" className="form-control" placeholder="Imię" id="name"
                                                   name="name"
                                                   value={this.state.name}
                                                   onChange={(event) => this.handleUserInput(event)} ref="name"/>
                                            <span className="errorSpan">{this.state.formErrors.name}</span>
                                        </div>
                                        <div
                                            className={`form-group has-feedback ${this.errorClass(this.state.formErrors.lastName)}`}>

                                            <label htmlFor="lastName">
                                                Nazwisko
                                            </label>
                                            <input type="text" className="form-control" placeholder="Nazwisko"
                                                   name="lastName"
                                                   id="lastName" value={this.state.lastName}
                                                   onChange={(event) => this.handleUserInput(event)} ref="lastName"/>
                                            <span className="errorSpan">{this.state.formErrors.lastName}</span>
                                        </div>
                                        <div
                                            className={`form-group has-feedback ${this.errorClass(this.state.formErrors.email)} ${this.errorClass(this.state.formErrors.emailExists)}`}>

                                            <label htmlFor="email">
                                                Adres Email(<a href="#"
                                                               title={"Adres email musi kończyć się @us.edu.pl"}>?</a>)
                                            </label>
                                            <input type="email" className="form-control" placeholder="E-mail@us.edu.pl"
                                                   id="email" name="email" value={this.state.email}
                                                   autoComplete="new-email"
                                                   onChange={(event) => this.handleUserInput(event)} ref="email"/>
                                            <span className="errorSpan">{this.state.formErrors.email}</span>
                                            <span className="errorSpan"> {this.state.formErrors.emailExists}</span>
                                        </div>
                                        <div
                                            className={`form-group has-feedback ${this.errorClass(this.state.formErrors.password)}`}>

                                            <label htmlFor="password">
                                                Hasło(<a href="#"
                                                         title={"Hasło musi składać się z minimum 8 znaków"}>?</a>)
                                            </label>

                                            <input type="password" className="form-control glyphicon glyphicon-lock"
                                                   id="password" name="password" placeholder="Hasło"
                                                   value={this.state.password} autoComplete="new-password"
                                                   onChange={(event) => this.handleUserInput(event)} ref="password"/>
                                            <i className="glyphicon glyphicon-lock form-control-feedback"></i>
                                            <span className="errorSpan">{this.state.formErrors.password}</span>
                                        </div>

                                        <div
                                            className={`form-group has-feedback ${this.errorClass(this.state.formErrors.confirmPassword)}`}>
                                            <label htmlFor="confirmPassword">
                                                Potwierdź hasło
                                            </label>

                                            <input type="password" className="form-control glyphicon glyphicon-lock"
                                                   id="confirmPassword" name="confirmPassword"
                                                   placeholder="Potwierdź hasło"
                                                   value={this.state.confirmPassword} autoComplete="new-password"
                                                   onChange={(event) => this.handleUserInput(event)}
                                            />
                                            <i className="glyphicon glyphicon-lock form-control-feedback"></i>
                                            <span className="errorSpan">{this.state.formErrors.confirmPassword}</span>
                                        </div>
                                        <div className="text-center button">
                                            <button type="submit" className="btn btn-default"
                                                    disabled={!this.state.formValid}>
                                                Zarejestruj się
                                            </button>


                                        </div>
                                        <Link to={"/login"}><span className="text-left btn btn-link">Powrót</span></Link>
                                    </form>
                                    <Dialog
                                        title="Aktywacja konta"
                                        actions={actions}
                                        modal={false}
                                        open={this.state.open}
                                        onRequestClose={this.modalClose}
                                    > Na twój adres e-mail wysłalismy link aktywacyjny
                                    </Dialog>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-2">
                        </div>
                    </div>
                </div>
                </MuiThemeProvider>
            </div>
        );
    }

}

export default Home;