import React, { Component } from 'react'
import { Tab, Tabs, Container } from 'react-bootstrap'
import Login from './Login'
import Register from './Register'
import { AuthConsumer } from './AuthContext';
import '../styles.css';
import { Link } from 'react-router-dom';

export default class SignTabs extends Component {
    loggedIn(user) {
        this.props.history.push("/users/" + user.id);
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
            {({isAuth, login, user}) => isAuth? this.loggedIn(user) : this.auth(login) }
        </AuthConsumer>
    }
}