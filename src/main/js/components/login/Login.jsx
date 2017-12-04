import React from 'react';
import {BrowserRouter as Router, Route, Link} from 'react-router-dom';

const API_ADDRESS = "http://localhost:8080/"; //TODO Change it to normal URL

class Home extends React.Component {
    constructor(props) {
        super(props);
        this.onSubmit = this.handleSubmit.bind(this);
        this.state = {
            stat: {status: ""},
            username: '',
            password: '',
            formErrors: {username: '', password: ''},
            usernameValid: false,
            passwordValid: false,
            formValid: false
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
        var myHeaders = new Headers({"Content-Type": "application/json",
        "username":this.state.username,
        "password":this.state.password});

        fetch(API_ADDRESS + "login", {

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

    render() {
        return (
            <div>
                <link rel="stylesheet" type="text/css" href="/../../css/loginPage.css"/>

                <div className="container-fluid">
                    <div className="row">
                        <div className="col-md-2">
                        </div>
                        <div className="col-md-8">
                            <div id="octagonWrap">
                                <div id="octagon">
                                    <form role="form" method="post" className="jumbotron" >
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
                                            <i class="glyphicon glyphicon-lock form-control-feedback"></i>
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

            </div>
        );
    }

}

export default Home;