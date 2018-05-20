import React, {Component} from 'react';
import NavBar from './../obligatory/NavBar';
import {Breadcrumb, Col, Grid, Image, Jumbotron, Panel, Row} from "react-bootstrap";
import {
    Avatar, CircularProgress, Dialog, Divider, FlatButton, FloatingActionButton, IconButton, List, ListItem,
    MuiThemeProvider, TextField
} from "material-ui";
import {
    Table,
    TableBody,
    TableHeader,
    TableHeaderColumn,
    TableRow,
    TableRowColumn,
} from 'material-ui/Table';
import {RadioButton, RadioButtonGroup} from 'material-ui/RadioButton';
import RaisedButton from 'material-ui/RaisedButton';

import Checkbox from 'material-ui/Checkbox';
import ActionFavorite from 'material-ui/svg-icons/action/favorite';
import ActionFavoriteBorder from 'material-ui/svg-icons/action/favorite-border';
import ContentAdd from 'material-ui/svg-icons/content/add';
import {Link} from "react-router-dom";
import {url} from '../../Constants';
import ActionInfo from "material-ui/svg-icons/action/info";
// import "./../../../resources/static/css/circle";
// import "./../../../resources/static/css/circle.css";
// import styles from "./../../"
let header = {
    "Content-Type": "application/json"
};

// /*{this.state.circles.map((dynamicData,key)=>*/
// /*{dynamicData.name}:*/
// /*{dynamicData.lastName}*/


class HashTable extends React.Component {


    constructor() {
        super();
        this.lookFor = this.handleLookFor.bind(this);
        this.saveChanges = this.handleSaveChanges.bind(this);
        this.closeModal = this.handleClose.bind(this);
        this.openDialog = this.handleOpen.bind(this);
        this.state = {
            resultOfSearching:"",
            toSearch:'',
            toSearchValid:"",
            hashArray:[],
            hashTable: "",
            hashTableValid: false,
            formErrors: {hashTable: '',},
            formValid: false,
            isLoading: true,
            open: false,
            hashType: "",
            primeNumber:""
        };
    }
    isPrime(num) {
        var prime = num != 1;
        for(var i=2; i<num; i++) {
            if(num % i == 0) {
                prime = false;
                break;
            }
        }
        return prime;
    }
    handleSaveChanges() {

        let words =this.state.hashTable;
        let countWords = (words.split(" ").length +1);
        // let countWords=100;
        if(this.state.hashType==="hashMultiply")
        {
            fetch(url + "/react/main/hashTable/hashMultiply", {
                method: 'POST',
                body: JSON.stringify({
                    data: this.state.hashTable,
                    countW: countWords
                }),
                headers: header,
                credentials: 'same-origin'
            }).then((Response) => Response.json()).then((findresponse) => {
                this.setState({
                    hashArray: findresponse,
                })
            });
        }
        else if(this.state.hashType==="hashMod")
        {
            fetch(url + "/react/main/hashTable/hashMod", {
                method: 'POST',
                body: JSON.stringify({
                    data: this.state.hashTable,
                    countW: countWords
                }),
                headers: header,
                credentials: 'same-origin'
            }).then((Response) => Response.json()).then((findresponse) => {
                this.setState({
                    hashArray: findresponse,
                })
            })
        }
        else if(this.state.hashType==="hashUni")
        {
            let r =4;
            while(!this.isPrime(r))
            {
                r = Math.floor(Math.random() * (10000 - 1 + 1)) + 1;
            }
            this.setState({
                primeNumber:r
            });
            fetch(url + "/react/main/hashTable/hashUni", {
                method: 'POST',
                body: JSON.stringify({
                    data: this.state.hashTable,
                    countW: countWords,
                    k:r
                }),
                headers: header,
                credentials: 'same-origin'
            }).then((Response) => Response.json()).then((findresponse) => {
                this.setState({
                    hashArray: findresponse,
                })
            })
        }
        else
        {
            console.log("Tak wim ni zabezpieczon #JSna100%");
        }
        this.setState({
            open: false,
            hashTable: "",
            formValid: false,
        });


    }
    handleLookFor() {

        let word =this.state.toSearch;
        let array = this.state.hashArray;
        console.log(this.state.hashType);
        if(this.state.hashType==="hashMultiply")
        {
            fetch(url + "/react/main/hashTable/searchMultiply", {
                method: 'POST',
                body: JSON.stringify({
                    search:word,
                    tab:array
                }),
                headers: header,
                credentials: 'same-origin'
            }).then((Response) => Response.json()).then((findresponse) => {
                this.setState({
                    resultOfSearching: findresponse,
                })
            });
        }
        else if(this.state.hashType==="hashMod")
        {
            fetch(url + "/react/main/hashTable/searchMod", {
                method: 'POST',
                body: JSON.stringify({
                    search:word,
                    tab:array
                }),
                headers: header,
                credentials: 'same-origin'
            }).then((Response) => Response.json()).then((findresponse) => {
                this.setState({
                    resultOfSearching: findresponse,
                })
            })
        }
        else if(this.state.hashType==="hashUni")
        {
            let random = this.state.primeNumber;
            fetch(url + "/react/main/hashTable/searchUni", {
                method: 'POST',
                body: JSON.stringify({
                    search:word,
                    tab:array,
                    k:random

                }),
                headers: header,
                credentials: 'same-origin'
            }).then((Response) => Response.json()).then((findresponse) => {
                this.setState({
                    resultOfSearching: findresponse,
                })
            })
        }
        else
        {
            console.log("Tak wim ni zabezpieczon #JJSna100%");
        }
        this.setState({
            open: false,
            hashTable: "",
            formValid: false,
        });


    }

    handleUserInput(e) {
        const name = e.target.name;
        const value = e.target.value;
        this.setState({[name]: value},
            () => {
                this.validateField(name, value)
            });
    }

    handleRadioInput(e, value) {

        this.setState({
            hashType: value

        });
    }

    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;
        let hashTableValid = this.state.hashTableValid;
        let toSearchValid = this.state.toSearchValid;


        switch (fieldName) {
            case 'hashTable':
                hashTableValid = value.length >= 3 && value.length <= 500;
                fieldValidationErrors.hashTable = hashTableValid ? '' : 'Nie poprawna długość';
                break;
            case 'toSearch':
                toSearchValid = value.length >= 3 && value.length <= 60;
                fieldValidationErrors.hashTable = toSearchValid ? '' : 'Nie poprawna długość';
                break;

            default:
                break;
        }
        this.setState({

            formErrors: fieldValidationErrors,
            hashTableValid: hashTableValid,
            toSearchValid:toSearchValid,
        }, this.validateForm);
    }

    validateForm() {
        this.setState({formValid: this.state.hashTableValid });
    }

    handleOpen() {
        this.setState({open: true});
    };


    handleClose() {
        this.setState({open: false});
    };

    componentDidMount() {

    }


    render() {
        // const style = {
        //     marginTop: 20,
        //     marginRight:20,
        //     marginBottom:10,
        //     marginLeft:20,
        //     top: 'auto',
        //     right: 0,
        //     bottom: 20,
        //     left: 'auto',
        //     position: 'fixed',
        // };
        const styles = {
            block: {
                maxWidth: 250,
            },
            radioButton: {
                marginBottom: 16,
            },
        };

        const actions = [
            <FlatButton
                label="Potwierdź"
                primary={true}
                keyboardFocused={true}
                onClick={this.saveChanges}
                disabled={!this.state.formValid}
            />
        ];
        let content =
            <MuiThemeProvider>
                <Jumbotron>
                    <Panel>
                        <Grid>
                            <Row className="show-grid circle">
                                <Col md={2}></Col>
                                <Col md={8}>
                                    <Table>
                                        <TableHeader
                                            displaySelectAll={false}
                                            adjustForCheckbox={false}>
                                            <TableRow>
                                                <TableHeaderColumn>Index</TableHeaderColumn>
                                                <TableHeaderColumn>Wartość</TableHeaderColumn>
                                            </TableRow>
                                        </TableHeader>
                                        <TableBody
                                            displayRowCheckbox={false}>
                                            {this.state.hashArray.map((dynamicData, key) => (

                                            <TableRow>
                                                <TableRowColumn>{key}</TableRowColumn>
                                                <TableRowColumn>{dynamicData}</TableRowColumn>
                                            </TableRow>
                                            ))}
                                        </TableBody>
                                    </Table>

                                    <TextField
                                        floatingLabelText="Fraza do wyszukania"

                                        name="toSearch"
                                        id="toSearch" value={this.state.toSearch}
                                        onChange={(event) => this.handleUserInput(event)}
                                    />
                                    <RaisedButton label="Primary" primary={true}
                                                  onClick={this.lookFor}
                                    /><br/>
                                    <div>{this.state.resultOfSearching}</div>


                                </Col>
                                <Col md={2}></Col>
                            </Row>
                        </Grid>

                    </Panel>
                </Jumbotron>
            </MuiThemeProvider>;

        let loader = <MuiThemeProvider>
            <div align="center">
                <CircularProgress/>
            </div>
        </MuiThemeProvider>;

        return (



            <div>
                <NavBar/>
                <link rel="stylesheet" href={"/css/circle.css"}/>
                {/*<link href="../../../resources/static/css/circle.css"/>*/}


                <Row className="show-grid">
                    <Col md={1}/>

                    <Col md={10}>

                        <Breadcrumb>
                            <Breadcrumb.Item href="/" active>
                                Strona Główna
                            </Breadcrumb.Item>
                        </Breadcrumb>
                        <Panel id="mainPanel">
                            <h4>
                                Skitrana opcja
                            </h4>
                        </Panel>
                        <div id={"circles"}>
                        </div>
                        {content}


                    </Col>

                    <Col md={1}/>
                </Row>
                <MuiThemeProvider>

                    <div className={"fab"} align={"center"}>

                        <FloatingActionButton onClick={this.openDialog}>
                            <ContentAdd/>

                        </FloatingActionButton>
                        <div className={"buttonUnderFab"}>
                            <FlatButton hoverColor={"transparent"} disableTouchRipple={true}
                                        label={<strong>Dodaj</strong>} primary={true} onClick={this.openDialog}/>
                        </div>

                    </div>


                    <Dialog
                        title="Wybierz ukryte opcje"
                        actions={actions}
                        modal={true}
                        open={this.state.open}
                        onRequestClose={this.handleClose}
                    >
                        <div className={"dialog"}>
                            <TextField
                                floatingLabelText="Przykładowy tekst"

                                name="hashTable"
                                id="hashTable" value={this.state.hashTable}

                                errorText={this.state.formErrors.hashTable}
                                onChange={(event) => this.handleUserInput(event)}
                            /><br/><br/>

                            <RadioButtonGroup name="hashType"
                                              onChange={(event, value) => this.handleRadioInput(event, value)}>
                                <RadioButton
                                    value="hashMod"
                                    label="Hashowanie Modulo"
                                    style={styles.radioButton}
                                />
                                <RadioButton
                                    value="hashMultiply"
                                    label="Hashowanie przez mnożenie"
                                    style={styles.radioButton}

                                />
                                <RadioButton
                                    value="hashUni"
                                    label="Hashowanie uniwersalne"
                                    style={styles.radioButton}

                                />
                            </RadioButtonGroup>

                        </div>
                    </Dialog>
                </MuiThemeProvider>
            </div>

        )
    }
}

export default HashTable