import React from 'react';
import {BrowserRouter as Router, Route, Link} from 'react-router-dom';


class Home extends React.Component {

    render() {
        return (
            <div>
                <link rel="stylesheet" type="text/css" href="../../css/loginPage.css"/>

                <div className="container-fluid">
                    <div className="row">
                        <div className="col-md-2">
                        </div>
                        <div className="col-md-8">
                            <div  id="octagonWrap">
                                <div id="octagon">
                                    <form role="form" className="jumbotron">
                                        <div className="form-group has-feedback">

                                            <label htmlFor="email">
                                                Adres Email
                                            </label>
                                            <input type="email" className="form-control" placeholder="E-mail"
                                                   id="email"/>
                                            <i class="glyphicon glyphicon-user form-control-feedback"></i>
                                        </div>
                                        <div className="form-group has-feedback">

                                            <label htmlFor="password">
                                                Hasło
                                            </label>
                                            <br/>
                                            <input type="password" className="form-control glyphicon glyphicon-lock" id="password" placeholder="Hasło"/>
                                            <i class="glyphicon glyphicon-lock form-control-feedback"></i>
                                        </div>
                                        <Link to={"/register"}> Zarejestruj się</Link>
                                        <span className="toRight">
                                            <Link to={"/forgot"}> Zapomniałem hasła</Link>
                                        </span>


                                        <div className="text-center button">
                                            <button type="submit" className="btn btn-default">
                                                Zaloguj
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

export default Home;