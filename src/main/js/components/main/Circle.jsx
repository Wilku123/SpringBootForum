import React, {Component} from 'react';
const API = "http://localhost:8080/react/main/showUser"; //TODO ZMIEN TO NA URL INNE CNIE
let header = {
    "Content-Type": "application/json",
    // "Authorization": "Basic YzgwNzlkNTAtMTViNy00NTkxLWI3MDktZjhhYTQzYjAxMmE4OjEyMzQ="

};
import NavBar from './../obligatory/NavBar';
const div = document.getElementById('circles');


class Circle extends Component {


    constructor() {
        super();
        this.state = {circles: []};
    }

    componentDidMount() {
        // fetch(API,{
        //     method: 'POST',
        //     body: "",
        //     headers: header,
        //     credentials:'same-origin'}).
        //     then((Response) =>Response.json()).
        //     then((findresponse)=>{
        //     this.setState({
        //         circles:findresponse,
        //     })
        //
        //
        // {/*{this.state.circles.map((dynamicData,key)=>*/}
        //{/*{dynamicData.name}:*/}
        //{/*{dynamicData.lastName}*/}
        // })
    }

    render() {
        return(

            <div>
                <NavBar/>



            </div>

        )
    }
}

export default Circle