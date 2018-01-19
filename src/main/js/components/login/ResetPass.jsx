import React from 'react';
import ReactQueryParams from 'react-query-params';
import {BrowserRouter as Router, Route, Link,Redirect} from 'react-router-dom';
import {url} from '../../Constants'

class ResetPass extends ReactQueryParams {

    constructor(props) {
        super(props);
        this.onSubmit = this.handleSubmit.bind(this);
        this.state = {
            stat: {status:""},
            password: '',
            formErrors: {password: ''},
            passwordValid: false,
            formValid: false,

        }
    }

    handleUserInput(e) {
        const name = e.target.name;
        const value = e.target.value;
        this.setState({[name]: value},
            () => {
                this.validateField(name, value)
            });
    }
    validateForm() {
        this.setState({formValid: this.state.passwordValid});
    }

    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;

        let passwordValid = this.state.passwordValid;



        switch (fieldName) {
            case 'password':
                passwordValid = value.length >= 8;
                fieldValidationErrors.password = passwordValid ? '' : ' Hasło za krótkie';

                break;
            default:
                break;
        }
        this.setState({
            formErrors: fieldValidationErrors,
            passwordValid: passwordValid,
        }, this.validateForm);
    }
    errorClass(error) {
        return (error.length === 0 ? '' : 'has-error' );
    }

    handleSubmit(e) {
        e.preventDefault();



        let checkUser;
        var myHeaders = new Headers({"Content-Type": "application/json"});

        fetch(url + "/react/changePass", {
            method: 'POST',
            headers: myHeaders,
            body: JSON.stringify({
                password: this.state.password,
                activeToken : this.queryParams.token
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
                window.location.assign("/");
            }
        });
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
                            <div  id="octagonWrap">
                                <div id="octagon">
                                    <form role="form" className="jumbotron" onSubmit={this.onSubmit}>
                                        <div
                                            className={`form-group has-feedback ${this.errorClass(this.state.formErrors.password)}`}>

                                            <label htmlFor="password">
                                                Podaj nowe hasło(<a href="#"
                                                         title={"Hasło musi składać się z minimum 8 znaków"}>?</a>)
                                            </label>

                                            <input type="password" className="form-control glyphicon glyphicon-lock"
                                                   id="password" name="password" placeholder="Hasło"
                                                   value={this.state.password} autoComplete="new-password"
                                                   onChange={(event) => this.handleUserInput(event)} ref="password"/>
                                            <i class="glyphicon glyphicon-lock form-control-feedback"></i>
                                            <span className="errorSpan">{this.state.formErrors.password}</span>
                                        </div>
                                        <div className="text-center button">

                                            <button type="submit" className="btn btn-default" disabled={!this.state.formValid} >
                                                Zresetuj

                                        </button>
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
export default ResetPass;