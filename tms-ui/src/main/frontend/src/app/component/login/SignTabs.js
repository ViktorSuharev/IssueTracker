import React, { Component } from 'react'
import { Tab, Tabs, Container } from 'react-bootstrap'
import Login from './Login'
import Register from './Register'
import { AuthConsumer } from './AuthContext';

export default class SignTabs extends Component {
    loggedIn() {
        return null;
    }

    auth() {
        return <Container>
        <Tabs defaultActiveKey="login">
            <Tab eventKey="login" title="Login">
                <Login/>
            </Tab>
            <Tab eventKey="register" title="Register">
                <Register />
            </Tab>
        </Tabs>
    </Container>;
    }

    render() {
        return <AuthConsumer>
            {({isAuth}) => isAuth? this.loggedIn() : this.auth() }
        </AuthConsumer>
        
    }
}