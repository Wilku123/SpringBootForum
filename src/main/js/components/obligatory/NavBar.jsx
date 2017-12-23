import React from 'react';
import {MenuItem, Nav, Navbar, NavDropdown, NavItem} from "react-bootstrap";
import {url} from '../../Constants';
import {Avatar, List, ListItem, MuiThemeProvider} from "material-ui";
import {Link} from "react-router-dom";

let header = {
    "Content-Type": "application/json"
};

class NavBar extends React.Component {

    constructor() {
        super();
        this.state = {user: [name=""]};
    }

    componentDidMount() {
        fetch(url+"/react/main/showUser", {
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
// {/*<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/latest/css/bootstrap.min.css"/>*/}
// {/*<link rel="stylesheet" href="../../css/navbar.css"/>*/}
    render() {
        return (

            <Navbar>
                <MuiThemeProvider>
                    <List>

                        <Navbar.Header>
                            <Navbar.Brand>
                                <a href="#">Forum romanum</a>
                            </Navbar.Brand>
                        </Navbar.Header>
                        <Nav>
                            <NavItem href="#">Link</NavItem>
                            <NavItem href="#">Link</NavItem>
                        </Nav>
                        <Nav pullRight>


                            <ListItem disabled={true} leftAvatar={<Avatar
                                src={this.state.user.avatar}/>}>

                                <NavDropdown title={this.state.user.name + " " + this.state.user.lastName}
                                             id="basic-nav-dropdown">
                                    <MenuItem href={"/main/app"}>Aplikacja Moblina</MenuItem>
                                    <MenuItem href={"/main/subbed"}>Subskrybowane</MenuItem>
                                    <MenuItem href={"/main/config"}>Ustawienia</MenuItem>
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