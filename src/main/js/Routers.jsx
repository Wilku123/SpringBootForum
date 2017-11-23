import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter, Router, Route, Link, Switch} from 'react-router-dom';
import Login from './components/login/Login';
import Register from './components/login/Register';
import About from './components/About';


class Routers extends React.Component{

    render() {
        return(

            <BrowserRouter>

                <div>
                    {/*<ul>*/}
                        {/*<li><Link to="/">Home</Link></li>*/}
                        {/*<li><Link to="/about">About</Link></li>*/}
                        {/*<li><Link to="/topics">Topics</Link></li>*/}
                    {/*</ul>*/}

                    {/*<hr />*/}
                    <Switch>
                        <Route exact path='/' component={Login} />
                        <Route path='/about' component={About} />
                        <Route path='/register' component={Register}/>
                        {/*<Route path='/contact' component={Contact} />*/}
                    </Switch>

                </div>
            </BrowserRouter>
        );
    }


}
export default Routers;