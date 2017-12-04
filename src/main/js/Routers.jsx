import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter, Router, Route, Link, Switch} from 'react-router-dom';
import Login from './components/login/Login';
import Register from './components/login/Register';
import Circle from './components/main/Circle';
import ForgotPassword from './components/login/ForgotPassword';
import ResetPass from './components/login/ResetPass';



class Routers extends React.Component{

    render() {
        return(

            <BrowserRouter forceRefresh={true}>

                <div>
                    {/*<ul>*/}
                        {/*<li><Link to="/">Home</Link></li>*/}
                        {/*<li><Link to="/about">About</Link></li>*/}
                        {/*<li><Link to="/topics">Topics</Link></li>*/}
                    {/*</ul>*/}

                    {/*<hr />*/}
                    <Switch>

                        <Route exact path='/login' component={Login} />
                        <Route path='/main/circle' component={Circle}/>
                        <Route path='/reg' component={Register}/>
                        <Route path='/forgot' component={ForgotPassword}/>
                        <Route path="/resetPass" component={ResetPass}/>
                        {/*<Route path='/contact' component={Contact} />*/}
                    </Switch>

                </div>
            </BrowserRouter>
        );
    }


}
export default Routers;