import * as axios from "axios";
import React, { Component } from "react";
import Bootstrap, { Form, Button, Row, Col, Container } from 'react-bootstrap'
import styles from "../styles.css";

export default class Login extends Component {
    constructor(props) {
        super(props);

        this.state = {
            email: "",
            password: ""
        };
    }

    validateForm() {
        return this.state.email.length > 0 && this.state.password.length > 0;
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    }

    handleSubmit = event => {
        event.preventDefault();

        axios.post(`http://localhost:8090/api/auth/login`, this.state)
            .then(response => {
                const jwt = response.data;
                var tokenType = jwt.tokenType;
                var token = jwt.accessToken
                localStorage.setItem('token', tokenType + ' ' + token);
                //showSuccessPage
                // this.setState({users: users});
                // this.setState({user: users[0]}); //userId from session soon
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    render() {
        return (
            <Container>
                <Form onSubmit={this.handleSubmit}>
                    <Form.Group as={Row} controlId="email">
                        <Form.Label column sm={1}>Email:</Form.Label>
                        <Col sm={4}>
                            <Form.Control
                                autoFocus
                                type="email"
                                placeholder="Email"
                                value={this.state.email}
                                onChange={this.handleChange}
                            />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} controlId="password" bsSize="large">
                        <Form.Label column sm={1}>Password:</Form.Label>
                        <Col sm={4}>
                            <Form.Control
                                value={this.state.password}
                                onChange={this.handleChange}
                                placeholder="Password"
                                type="password"
                            />
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row}>
                        <Col sm={5}>
                            <Button
                                block
                                variant="outline-primary"
                                bsSize="large"
                                disabled={!this.validateForm()}
                                type="submit"
                            >
                                Login
                            </Button>
                        </Col>
                    </Form.Group>
                </Form>
            </Container>
        );
    }
}