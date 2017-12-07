import React, {Component} from 'react';
import NavBar from './../obligatory/NavBar';
import {Breadcrumb, Col, Grid, Jumbotron, Panel, Row} from "react-bootstrap";
import {Avatar, CircularProgress, List, ListItem, MuiThemeProvider} from "material-ui";
import {Link} from "react-router-dom";
import {url} from '../../Constants';

let header = {
    "Content-Type": "application/json"
};

// /*{this.state.circles.map((dynamicData,key)=>*/
// /*{dynamicData.name}:*/
// /*{dynamicData.lastName}*/

class Circle extends React.Component {


    constructor() {
        super();
        this.state = {circles: [], isLoading: true};
    }

    componentDidMount() {
        fetch(url + "/react/main/getCircle", {
            method: 'POST',
            body: "",
            headers: header,
            credentials: 'same-origin'
        }).then((Response) => Response.json()).then((findresponse) => {
            this.setState({
                circles: findresponse,
                isLoading: false
            })
        })
    }

    render() {
        let content =
            <Jumbotron>
                    {this.state.circles.map((dynamicData, key) =>
                        <Panel key={key}>
                            <Grid>
                                <Row className="show-grid">
                                    <Col xs={12} md={8}>
                                        <Link to={"/main/circle/"+dynamicData.idCircle} >
                                            <h4>
                                                {dynamicData.name}
                                                </h4>
                                        </Link>

                                        {dynamicData.description}
                                        </Col>
                                    <Col xs={6} md={2}>
                                        <h4>{dynamicData.countTopic}</h4> Tematów

                                    </Col>
                                    <Col xs={6} md={2}>
                                        <MuiThemeProvider>
                                            <List>
                                                <ListItem disabled={true}
                                                          leftAvatar={<Avatar src={dynamicData.author.avatar}/>}
                                                          primaryText={<Link to={"/main/user/"+dynamicData.author.idUser}>{dynamicData.author.name+ " "+ dynamicData.author.lastName}</Link>}>
                                                </ListItem>


                                            </List>

                                        </MuiThemeProvider>
                                    </Col>
                                </Row>
                            </Grid>

                        </Panel>
                    )}
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
                            <Breadcrumb.Item href="/" active>
                                Strona Główna
                            </Breadcrumb.Item>
                        </Breadcrumb>
                        <Panel id="mainPanel">
                            <h4>
                            Koła zainteresowań
                            </h4>
                        </Panel>
                        {this.state.isLoading ? loader : content}


                    </Col>

                    <Col xsHidden md={2}/>
                </Row>

            </div>

        )
    }
}

export default Circle