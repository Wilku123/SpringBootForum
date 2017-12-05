import React from 'react';
import {Dropdown, DropdownButton, MenuItem, Nav, Navbar, NavDropdown, NavItem, SplitButton} from "react-bootstrap";
import Avatar from 'material-ui/Avatar';
import List from 'material-ui/List/List';
import ListItem from 'material-ui/List/ListItem';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';


const API_ADDRESS = "http://localhost:8080/react/main/showUser"; //TODO Change it to normal URL
let header = {
    "Content-Type": "application/json"
};

class NavBar extends React.Component {

    constructor() {
        super();
        this.state = {user: []};
    }

    componentDidMount() {
        fetch(API_ADDRESS, {
            method: 'POST',
            body: "",
            headers: header,
            credentials: 'same-origin'
        }).then((Response) => Response.json()).then((findresponse) => {
            this.setState({
                user: findresponse,
            })
        })
    }

    render() {
        return (

            <Navbar>
                <MuiThemeProvider>
                    <List>
                        {/*<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/latest/css/bootstrap.min.css"/>*/}
                        {/*<link rel="stylesheet" href="../../css/navbar.css"/>*/}
                        <Navbar.Header>
                            <Navbar.Brand>
                                <a href="#">Forum romanum</a>
                            </Navbar.Brand>
                        </Navbar.Header>
                        <Nav>
                            <NavItem pullRight eventKey={1} href="#">Link</NavItem>
                            <NavItem href="#">Link</NavItem>
                        </Nav>
                        <Nav pullRight>


                            <ListItem disabled={true} leftAvatar={<Avatar
                                src={"https://www.codeproject.com/KB/GDI-plus/ImageProcessing2/img.jpg"} size={30}/>}>

                                <NavDropdown title={this.state.user.name + " " + this.state.user.lastName}
                                             id="basic-nav-dropdown">
                                    <MenuItem href={"#"}>Aplikacja Moblina</MenuItem>
                                    <MenuItem href={"#"}>Subskrybowane</MenuItem>
                                    <MenuItem href={"#"}>Ustawienia</MenuItem>
                                    <MenuItem divider/>
                                    <MenuItem href={"/logout"}>Wyloguj</MenuItem>
                                </NavDropdown>
                            </ListItem>

                        </Nav>
                    </List>
                </MuiThemeProvider>
            </Navbar>
        );
    }


}

export default NavBar;