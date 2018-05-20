import React from 'react';
import {MenuItem, Nav, Navbar, NavDropdown, NavItem} from "react-bootstrap";
import {url} from '../../Constants';
import {AutoComplete, Avatar, IconButton, List, ListItem, Menu, MuiThemeProvider, TextField} from "material-ui";
import TextFieldIcon from 'material-ui-textfield-icon';
import {Link} from "react-router-dom";
import Search from 'material-ui/svg-icons/action/search'

let header = {
    "Content-Type": "application/json"
};

class NavBar extends React.Component {

    constructor() {
        super();
        this.state = {
            user: {name: "", lastName: ""},
            looker:""

        };
    }

    componentDidMount() {
        fetch(url + "/react/main/showUser", {
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

    handleUserInput(e) {
        const name = e.target.name;
        const value = e.target.value;
        this.setState({[name]: value});

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
                                <Link to={"/main/circle"}>DejMiTo</Link>
                            </Navbar.Brand>
                        </Navbar.Header>
                        <Nav>
                            {/*<NavItem href="#">Link</NavItem>*/}
                            {/*<NavItem href="#">Link</NavItem>*/}
                        </Nav>


                        <TextField
                            hintText="Wyszukaj"
                            name={"looker"}
                            value={this.state.looker}
                            onChange={(event) => this.handleUserInput(event)}

                        />
                        <IconButton>
                            <Link to={"/main/search?lookFor=" + this.state.looker}><Search/></Link>
                        </IconButton>

                        <Nav pullRight>





                            <ListItem disabled={true} leftAvatar={<Avatar
                                src={this.state.user.avatar}/>}>


                                <NavDropdown title={this.state.user.name + " " + this.state.user.lastName}
                                             id="basic-nav-dropdown">
                                    <MenuItem href={"/main/app"}>Aplikacja Moblina</MenuItem>
                                    <MenuItem href={"/main/subbed"}>Subskrybowane</MenuItem>
                                    <MenuItem href={"/main/config"}>Ustawienia</MenuItem>
                                    <MenuItem href={"/main/hashTable"}>Ukryta opcja niemiecka</MenuItem>

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