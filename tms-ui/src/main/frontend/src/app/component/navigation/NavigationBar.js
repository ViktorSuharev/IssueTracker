import React, { Component } from 'react';
import { Navbar, Nav, NavDropdown, Form, FormControl, Button, InputGroup } from 'react-bootstrap';
import { AuthConsumer } from '../login/AuthContext';
import { Link, Redirect, withRouter } from 'react-router-dom';
import '../styles.css';

class NavigationBar extends Component {
    constructor(props) {
        super(props);

        this.state = {
            shouldRedirect: false
        };
    }

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

                {/* SEARCH BOX */}
                <Form inline><InputGroup>
                    <FormControl
                        placeholder="Search"
                        defaultValue={this.props.match.params.name}
                        onChange={(event) => this.setState({ searchText: event.target.value })}
                    />
                    <InputGroup.Append>
                        <Button variant="outline-info"
                            onClick={(e) => {
                                e.preventDefault();

                                let name = this.state.searchText;
                                if (!name)
                                    return;

                                const link = '/tasks/search/' + name;
                                // this.props.history.goBack();
                                this.props.history.replace(link);
                                // this.setState({shouldRedirect: true, redirectLink: link});      
                            }}>
                            Search</Button>
                    </InputGroup.Append>
                </InputGroup>
                </Form>

                {/* USER ACTIONS */}
                <NavDropdown title={signedAsLabel} id='collasible-nav-dropdown'>
                    <AuthConsumer>
                        {({ logout, user }) => <div>
                            <NavDropdown.Item><Link className='black-link' to={'/users/' + user.id}>About me</Link></NavDropdown.Item>
                            <NavDropdown.Item onClick={logout}>Logout</NavDropdown.Item>
                        </div>
                        }
                    </AuthConsumer>
                </NavDropdown>

                {/* { this.state.shouldRedirect ? <Redirect to={this.state.redirectLink} /> : null } */}
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

export default withRouter(NavigationBar);