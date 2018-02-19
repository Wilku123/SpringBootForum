import React from 'react';
import {BrowserRouter as Router, Route, Link} from 'react-router-dom';
import ReactQueryParams from 'react-query-params';
import {url} from '../../Constants'
import {MuiThemeProvider, Snackbar} from "material-ui";

let header = {
    "Content-Type": "application/json"
};

class Home extends ReactQueryParams {


    constructor(props) {
        super(props);
        this.onSubmit = this.handleSubmit.bind(this);
        this.closeSnack = this.handleRequestClose.bind(this);
        this.state = {
            stat: {status: ""},
            username: '',
            password: '',
            formErrors: {username: '', password: ''},
            usernameValid: false,
            passwordValid: false,
            formValid: false,
            activated: [],
            open: false,
            openError: false,
            token:[],
            intel: "Konto Było aktywne, jest już gotowe do zalogowania"
        }
    }

    componentDidMount() {

        let token = this.queryParams.token;

        if (token !== undefined) {
            fetch(url + "/react/activate", {
                method: 'POST',
                body: JSON.stringify({
                    activeToken: token
                }),
                headers: header

            }).then((Response) => Response.json()).then((findresponse) => {
                this.setState({
                    activated: findresponse
                })
            }).then(() => {
                if (this.state.activated.status)
                {
                    this.setState({
                        intel: "Konto zostało aktywowane"

                    })
                }
                this.setState({
                    open:true
                });

            })
        }


        let error = this.queryParams.error;

        if (error === true) {
            this.setState({
                openError: true,
            })
        }


    }

    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;
        let usernameValid = this.state.usernameValid;
        let passwordValid = this.state.passwordValid;


        switch (fieldName) {
            case 'email':
                // emailValid = value.match(/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i);
                usernameValid = value.match(/^([\w.%+-]+)@us+\.edu+\.pl$/i);
                fieldValidationErrors.username = usernameValid ? '' : ' Nie prawidłowy adres E-mail';
                break;
            case 'password':
                passwordValid = true;
                fieldValidationErrors.password = passwordValid ? '' : ' Hasło za krótkie';
                break;
            default:
                break;
        }
        this.setState({
            formErrors: fieldValidationErrors,
            usernameValid: usernameValid,
            passwordValid: passwordValid,
        }, this.validateForm);
    }

    validateForm() {
        this.setState({formValid: this.state.usernameValid && this.state.passwordValid});
    }

    errorClass(error) {
        return (error.length === 0 ? '' : 'has-error' );
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
        let usernameValid = this.state.usernameValid;
        let fieldValidationErrors = this.state.formErrors;
        var myHeaders = new Headers({
            "Content-Type": "application/json",
            "username": this.state.username,
            "password": this.state.password
        });

        fetch(url + "/login", {

            method: 'POST',
            headers: myHeaders,
            body: JSON.stringify({
                username: this.state.username,
                password: this.state.password
            })
        })
            .then(function (response) {
                return response.json()
            }).then((findresponse) => {
            this.setState({
                stat: findresponse
            })
        }).then(() => {
            checkUser = this.state.stat.status;
            if (checkUser === true) {
                usernameValid = false;
                fieldValidationErrors.usernameExists = usernameValid ? '' : 'Adres E-mail nie poprawny';
                this.setState({
                    formErrors: fieldValidationErrors,
                    usernameValid: usernameValid
                }, this.validateForm);
            }
        });
    }

    handleRequestClose() {
        this.setState({
            open: false
        })
    }

    render() {
        return (
            <div>
                <link rel="stylesheet" type="text/css" href="/css/loginPage.css"/>

                <div className="container-fluid">
                    <div className="row">
                        <div className="col-md-2">
                        </div>
                        <div className="col-md-8">
                            <div id="octagonWrap">
                                <div id="octagon">
                                    <form role="form" method="post" className="jumbotron">
                                        <div
                                            className={`form-group has-feedback ${this.errorClass(this.state.formErrors.username)}`}>

                                            <label htmlFor="username">
                                                Adres Email(<a href="#"
                                                               title={"Adres email musi kończyć się @us.edu.pl"}>?</a>)
                                            </label>
                                            <input type="email" className="form-control" placeholder="E-mail@us.edu.pl"
                                                   id="username" name="username" value={this.state.username}
                                                   onChange={(event) => this.handleUserInput(event)} ref="username"/>
                                            <span className="errorSpan">{this.state.formErrors.username}</span>
                                            <span className="errorSpan"> {this.state.formErrors.usernameExists}</span>
                                        </div>
                                        <div
                                            className={`form-group has-feedback ${this.errorClass(this.state.formErrors.password)}`}>

                                            <label htmlFor="password">
                                                Hasło(<a href="#"
                                                         title={"Hasło musi składać się z minimum 8 znaków"}>?</a>)
                                            </label>

                                            <input type="password" className="form-control glyphicon glyphicon-lock"
                                                   id="password" name="password" placeholder="Hasło"
                                                   value={this.state.password}
                                                   onChange={(event) => this.handleUserInput(event)} ref="password"/>
                                            <i className="glyphicon glyphicon-lock form-control-feedback"/>
                                            <span className="errorSpan">{this.state.formErrors.password}</span>
                                        </div>
                                        <Link to={"/reg"}> Zarejestruj się</Link>
                                        <span className="toRight">
                                            <Link to={"/forgot"}> Zapomniałem hasła</Link>
                                        </span>


                                        <div className="text-center button">
                                            {/*<Link to={"/main/circle"}>*/}
                                            <button type="submit" className="btn btn-default">
                                                Zaloguj
                                            </button>
                                            {/*</Link>*/}
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-2">
                        </div>
                    </div>
                </div>
                <MuiThemeProvider>
                    <Snackbar
                        open={this.state.open}
                        message={this.state.intel}
                        autoHideDuration={4000}
                        onRequestClose={this.closeSnack}
                    />
                    <Snackbar
                        open={this.state.openError}
                        message="Błąd logowania"
                        autoHideDuration={4000}
                        bodyStyle={{ backgroundColor: 'red', color: 'black' }}
                        onRequestClose={this.closeSnack}
                    />
                </MuiThemeProvider>
            </div>
        );
    }

}

export default Home;