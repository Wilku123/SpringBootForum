import React from 'react';
import {BrowserRouter as Router, Route, Link} from 'react-router-dom';
import {url} from '../../Constants'

class ForgotPassword extends React.Component {

    constructor(props) {
        super(props);
        this.onSubmit = this.handleSubmit.bind(this);
        this.state = {
            stat: {status:""},
            email: '',
            formErrors: {email: ''},
            emailValid: false
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
    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;
        let emailValid = this.state.emailValid;
        switch (fieldName) {
            case 'email':
                // emailValid = value.match(/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i);
                emailValid = value.match(/^([\w.%+-]+)@us+\.edu+\.pl$/i);
                fieldValidationErrors.email = emailValid ? '' : ' Nie prawidłowy adres E-mail';
                break;
            default:
                break;
        }
        this.setState({

            formErrors: fieldValidationErrors,
            emailValid: emailValid,
        }, this.validateForm);
    }
    validateForm() {
        this.setState({formValid: this.state.emailValid});
    }
    handleSubmit(e) {
        e.preventDefault();
        console.log(JSON.stringify({
            email: this.state.email
        }));

        let checkUser;
        var myHeaders = new Headers({"Content-Type": "application/json"});

        fetch(url + '/react/forgotPassword', {
            method: 'POST',
            headers: myHeaders,
            body: JSON.stringify({
                email: this.state.email
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
                            <div  id="octagonWrap">
                                <div id="octagon">
                                    <form role="form" className="jumbotron" onSubmit={this.onSubmit}>
                                        <div className="form-group">

                                            <label htmlFor="email">
                                                Adres Email podany przy rejestracji
                                            </label>
                                            <input type="email" className="form-control" placeholder="E-mail@us.edu.pl"
                                                   id="email" name="email" value={this.state.email}
                                                   autoComplete="new-email"
                                                   onChange={(event) => this.handleUserInput(event)} ref="email"/>
                                        </div>
                                        <div className="text-center button">
                                            <button type="submit" className="btn btn-default">
                                                Zresetuj
                                            </button>

                                        </div>
                                        <Link to={"/"}><span className="text-left btn btn-link">Powrót</span></Link>
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

export default ForgotPassword;