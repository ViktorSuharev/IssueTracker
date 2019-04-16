import React, { Component } from 'react'
import { Tab, Tabs, Container } from 'react-bootstrap'
import Login from './Login'
import Register from './Register'
import { AuthConsumer } from './AuthContext';
import '../styles.css';

export default class SignTabs extends Component {
    loggedIn() {
        return null;
    }

    auth(login) {
        return <Container>
        <Tabs defaultActiveKey='login'>
            <Tab eventKey='login' title='Login'>
                <Login onLogin={login}/>
            </Tab>
            <Tab eventKey='register' title='Register'>
                <Register/>
            </Tab>
        </Tabs>
    </Container>;
    }

    render() {
        return <AuthConsumer>
            {({isAuth, login}) => isAuth? this.loggedIn() : this.auth(login) }
        </AuthConsumer>
    }
}