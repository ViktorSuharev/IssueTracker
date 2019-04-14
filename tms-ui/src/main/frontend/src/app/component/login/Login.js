import * as axios from "axios";
import React, { Component } from "react";
import Bootstrap, { Form, Button, Row, Col, Container } from 'react-bootstrap'
import styles from "../styles.css";
import { AuthConsumer, AuthProvider } from "./AuthContext";

export default class Login extends Component {
    constructor(props) {
        super(props);

        let us = JSON.parse(localStorage.getItem('userCredentrials'));
        if (us == null)
            us = { email: "", password: "" };

        this.state = { email: us.email, password: us.password };
    }

    validateForm() {
        return this.state.email.length > 0 && this.state.password.length > 0;
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    }

    render() {
        return (
            <Container className="mt-3">
                <Form>
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
                            <AuthConsumer>
                                {({ login }) => (
                                    <Button
                                        block
                                        variant="outline-primary"
                                        bsSize="large"
                                        disabled={!this.validateForm()}
                                        onClick={(e) => {e.preventDefault(); login(this.state)} }

                                        // onClick={this.disabled ? null : login(this.state) }
                                    >
                                        Login
                                    </Button>
                                )}
                            </AuthConsumer>
                        </Col>
                    </Form.Group>
                </Form>
            </Container>
        );
    }
}