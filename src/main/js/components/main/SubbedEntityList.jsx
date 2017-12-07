import React, {Component} from 'react';
import NavBar from './../obligatory/NavBar';
import {Breadcrumb, Col, Grid, Jumbotron, Panel, Row, Table} from "react-bootstrap";
import {Avatar, CircularProgress, List, ListItem, MuiThemeProvider, RaisedButton, Tab, Tabs} from "material-ui";
import {Link} from "react-router-dom";
import {url} from '../../Constants';

let header = {
    "Content-Type": "application/json"
};

// /*{this.state.circles.map((dynamicData,key)=>*/
// /*{dynamicData.name}:*/
// /*{dynamicData.lastName}*/

class SubbedEntityList extends React.Component {


    constructor() {
        super();
        this.state = {subbedCircle: [], isLoading: true};
    }

    componentDidMount() {
        fetch(url + "/react/main/subbedCircle", {
            method: 'POST',
            body: "",
            headers: header,
            credentials: 'same-origin'
        }).then((Response) => Response.json()).then((findresponse) => {
            this.setState({
                subbedCircle: findresponse,
                isLoading: false
            })
        })
    }
    // {this.state.subbedCircle.map((dynamicData, key) =>
// <th>{dynamicData.name}</th>
// <th>{dynamicData.description}</th>
// <th>{dynamicData.countSubbed}</th>
// <th>{dynamicData.countTopic}</th>
// <th>{dynamicData.publishDate}</th>
// )}
    render() {
        let content =
            <Jumbotron>
                <MuiThemeProvider>


                <Tabs
                    value={this.state.value}
                    onChange={this.handleChange}
                >
                    <Tab label="Subskrybowane Koła" value="a">
                        <div>
                            <h2 >Kołencja</h2>
                            <p>
                                Tabs are also controllable if you want to programmatically pass them their values.
                                This allows for more functionality in Tabs such as not
                                having any Tab selected or assigning them different values.
                            </p>
                        </div>
                    </Tab>
                    <Tab label="Subskrybowane Tematy" value="b">
                        <div>
                            <h2 >Tematensy</h2>
                            <p>
                                This is another example of a controllable tab. Remember, if you
                                use controllable Tabs, you need to give all of your tabs values or else
                                you wont be able to select them.
                            </p>
                        </div>
                    </Tab>
                </Tabs>

                    <RaisedButton label="Zapisz" fullWidth={true} />
                </MuiThemeProvider>
            </Jumbotron>;
        let loader = <MuiThemeProvider>
            <div align="center">
                <CircularProgress/>
            </div>
        </MuiThemeProvider>;

        return (

            <div>
                <NavBar/>
                <link rel="stylesheet" href="../../css/circle.css"/>


                <Row className="show-grid">
                    <Col xs={6} md={2}/>

                    <Col xs={6} md={8}>



                        <Breadcrumb>
                            <Breadcrumb.Item href="/main/circle">
                                Strona Główna
                            </Breadcrumb.Item>
                            <Breadcrumb.Item href="/main/circle" active>
                                Subskrybowane
                            </Breadcrumb.Item>
                        </Breadcrumb>
                        {this.state.isLoading ? loader : content}


                    </Col>

                    <Col xsHidden md={2}/>
                </Row>

            </div>

        )
    }
}

export default SubbedEntityList