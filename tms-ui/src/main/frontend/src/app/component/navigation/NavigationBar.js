import React, { Component } from 'react';
import { Navbar, Nav, NavDropdown } from 'react-bootstrap';
import { AuthConsumer } from '../login/AuthContext';
import { Link } from 'react-router-dom';
import '../styles.css';

export default class NavigationBar extends Component {
    loggedIn(user) {
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
                        <NavDropdown.Item>
                            <Link className='navbar-link' to='/projects/new'> Create</Link>
                        </NavDropdown.Item>
                        <NavDropdown.Item>
                            <Link className='navbar-link' to='/projects'> All projects</Link>
                        </NavDropdown.Item>
                    </NavDropdown>

                    <NavDropdown title='Task' id='collasible-nav-dropdown'>
                        <NavDropdown.Item>
                            <Link className='navbar-link' to='/tasks/new'> Add task</Link>
                        </NavDropdown.Item>
                        <NavDropdown.Item>
                            <Link className='navbar-link' to='/tasks/my'> My tasks</Link>
                        </NavDropdown.Item>

                        <NavDropdown.Item>
                            <Link className='navbar-link' to='/tasks/active/all'>Active tasks</Link>
                        </NavDropdown.Item>
                        <NavDropdown.Item>
                            <Link className='navbar-link' to='/tasks'>All tasks</Link>
                        </NavDropdown.Item>
                    </NavDropdown>
                </Nav>

                <NavDropdown title={signedAsLabel} id='collasible-nav-dropdown'>
                    <AuthConsumer>
                        {({ logout, user }) => <div>
                            <NavDropdown.Item><Link className='black-link' to={'/users/'+user.id}>About me</Link></NavDropdown.Item>
                            <NavDropdown.Item onClick={logout}>Logout</NavDropdown.Item>
                            </div>
                        }
                    </AuthConsumer>
                </NavDropdown>

            </Navbar.Collapse>
        </Navbar>;
    }

    guest() {
        return null;
    }

    render() {
        return <AuthConsumer>
            {
                ({ isAuth, user }) => isAuth ? this.loggedIn(user) : this.guest()
            }
        </AuthConsumer>
    }
}