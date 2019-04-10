import React, { Component } from 'react'
import { Navbar, Nav, NavDropdown } from 'react-bootstrap';
import { AuthConsumer } from '../login/AuthContext';
import SignTabs from '../login/SignTabs';

export default class NavigationBar extends Component {
    constructor(props) {
        super(props);

        this.state = props;
    }

    loggedIn(){
        return <Navbar fixed="top" expand="lg" bg="dark" variant="dark">
        <Navbar.Brand>
            <img
                alt=""
                src="nc.ico"
                className="d-inline-block align-top"
                width="30"
                height="30"
            />
            {' '}Issue Tracker
        </Navbar.Brand>

        <Navbar.Toggle aria-controls="responsive-navbar-nav" />

        <Navbar.Collapse className="justify-content-end">
            <Nav className="mr-auto">
                <NavDropdown title="Project" id="collasible-nav-dropdown">
                    <NavDropdown.Item href="/createproject">Create</NavDropdown.Item>
                </NavDropdown>
                <NavDropdown title="Task" id="collasible-nav-dropdown">
                    <NavDropdown.Item href="/addTask">Add</NavDropdown.Item>
                </NavDropdown>
            </Nav>

            <Navbar.Text>
                Signed in as: {this.state.user.user.fullName}
            </Navbar.Text>
        </Navbar.Collapse>
    </Navbar>;
    }

    guest(){
        return <SignTabs/>;
    }

    render() {
        return <AuthConsumer>
            {
                ({isAuth}) => isAuth? this.loggedIn() : this.guest()
            }
        </AuthConsumer>        
    }
}