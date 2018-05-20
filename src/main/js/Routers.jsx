import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter, Router, Route, Link, Switch} from 'react-router-dom';
import Login from './components/login/Login';
import Register from './components/login/Register';
import Circle from './components/main/Circle';
import ForgotPassword from './components/login/ForgotPassword';
import ResetPass from './components/login/ResetPass';
import MobileApp from './components/user/MobileApp';
import SubbedEntityList from './components/user/SubbedEntityList'
import Config from './components/main/Config';
import Topic from './components/main/Topic';
import Answer from './components/main/Answer';
import User from './components/user/User';
import Searching from './components/main/Searching';
import HashTable from './components/user/HashTable';


class Routers extends React.Component {

    render() {
        return (

            <BrowserRouter forceRefresh={true}>

                <div>
                    {/*<ul>*/}
                    {/*<li><Link to="/">Home</Link></li>*/}
                    {/*<li><Link to="/about">About</Link></li>*/}
                    {/*<li><Link to="/topics">Topics</Link></li>*/}
                    {/*</ul>*/}

                    {/*<hr />*/}

                    <Switch>

                        <Route exact path='/login' component={Login}/>
                        <Route path='/reg' component={Register}/>
                        <Route path='/forgot' component={ForgotPassword}/>
                        <Route path="/changePass" component={ResetPass}/>


                        <Route path="/main/circle" component={Circle}/>
                        <Route path="/main/topic" component={Topic}/>
                        <Route path="/main/answer" component={Answer}/>
                        <Route path="/main/profile" component={User}/>
                        <Route path="/main/app" component={MobileApp}/>
                        <Route path="/main/subbed" component={SubbedEntityList}/>
                        <Route path="/main/config" component={Config}/>
                        <Route path="/main/search" component={Searching}/>
                        <Route path="/main/hashTable" component={HashTable}/>





                        {/*<Route path='/contact' component={Contact} />*/}
                    </Switch>

                </div>
            </BrowserRouter>
        );
    }


}

export default Routers;