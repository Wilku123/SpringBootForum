
import React from 'react';
import ReactDOM from 'react-dom';
import Routers from "./Routers";
import Header from "./Header";

let modals = (
    <div>
        <Header />
        <Routers />
    </div>
);


ReactDOM.render(modals,document.getElementById('app'));




