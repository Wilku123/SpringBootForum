
import React from 'react';
import ReactDOM from 'react-dom';
import Routers from "./Routers";
import Header from "./Header";
import 'bootstrap/dist/css/bootstrap.min.css';
let modals = (
    <div>
        <Header />
        <Routers />
    </div>
);


ReactDOM.render(modals,document.getElementById('app'));




