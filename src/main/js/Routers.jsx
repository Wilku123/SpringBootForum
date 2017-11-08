import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter, Router, Route, Link, Switch} from 'react-router-dom';
import Home from './components/Home';
import About from './components/About';


class Routers extends React.Component{

    render() {
        return(
            <BrowserRouter>
                <div>
                    <ul>
                        <li><Link to="/">Home</Link></li>
                        <li><Link to="/about">About</Link></li>
                        <li><Link to="/topics">Topics</Link></li>
                    </ul>

                    <hr />
                    <Switch>
                        <Route exact path='/' component={Home} />
                        <Route path='/about' component={About} />
                        {/*<Route path='/contact' component={Contact} />*/}
                    </Switch>

                </div>
            </BrowserRouter>
        );
    }


}
export default Routers;