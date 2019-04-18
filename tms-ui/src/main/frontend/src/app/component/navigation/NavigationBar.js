import React, { Component } from 'react'
import { Navbar, Nav, NavDropdown } from 'react-bootstrap';
import { AuthConsumer } from '../login/AuthContext';
import '../styles.css';

export default class NavigationBar extends Component {
    loggedIn(user){
        let signedAsLabel = 'Signed in as: ' + user.name;

        return <Navbar fixed='top' expand='lg' bg='dark' variant='dark'>
        <Navbar.Brand>
            <img
                src={require('./nc.ico')}
                alt=''
                className='d-inline-block align-top'
                width='30'
                height='30'
            />
            {' '}Issue Tracker
        </Navbar.Brand>

        <Navbar.Toggle aria-controls='responsive-navbar-nav' />

        <Navbar.Collapse className='justify-content-end'>
            <Nav className='mr-auto'>
                <NavDropdown title='Project' id='collasible-nav-dropdown'>
                    <NavDropdown.Item href='/projects/new'>Create</NavDropdown.Item>
                    <NavDropdown.Item href='/projects'>Projects</NavDropdown.Item>
                </NavDropdown>
                <NavDropdown title='Task' id='collasible-nav-dropdown'>
                    <NavDropdown.Item href='/tasks/new'>Add</NavDropdown.Item>
                    <NavDropdown.Item href='/tasks'>Tasks</NavDropdown.Item>
                </NavDropdown>
            </Nav>

            <NavDropdown title={signedAsLabel} id='collasible-nav-dropdown'>
                    <AuthConsumer>
                        {({logout}) => <NavDropdown.Item onClick={logout}>Logout</NavDropdown.Item>}
                    </AuthConsumer>
                </NavDropdown>
        </Navbar.Collapse>
    </Navbar>;
    }

    guest(){
        return null;
    }

    render() {
        return <AuthConsumer>
            {
                ({isAuth, user}) => isAuth? this.loggedIn(user) : this.guest()
            }
        </AuthConsumer>        
    }
}