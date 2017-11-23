import React, {Component} from 'react';
const API = "http://localhost:8080/api/circle/all";
let data = {'id': '35'};
let header = {
    "Content-Type": "application/json",
    "Authorization": "Basic YzgwNzlkNTAtMTViNy00NTkxLWI3MDktZjhhYTQzYjAxMmE4OjEyMzQ="
};
let fetchData = {method: 'POST', body: data, headers: header};
const div = document.getElementById('circles');

function createNode(element) {
    return document.createElement(element);
}

function dodej(parent, el) {
    return parent.appendChild(el);
}

class About extends Component {


    constructor() {
        super();
        this.state = {circles: []};
    }

    componentDidMount() {
        fetch(API,fetchData).
            then((Response) =>Response.json()).
            then((findresponse)=>{
            this.setState({
                circles:findresponse,
            })
        })
    }

    render() {
        return(
            <div>
                {this.state.circles.map((dynamicData,key)=>
                <div>
                    <span>
                        {dynamicData.name}:
                    </span>
                    <span>{dynamicData.description}</span>
                </div>
                    )
                    }
            </div>
        )
    }
}

export default About