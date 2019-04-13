import React, { Component } from 'react'
import { Tab, Tabs, Container } from 'react-bootstrap'
import Login from './Login'
import Register from './Register'

export default class SignTabs extends Component {
    render() {
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
}