import * as axios from 'axios'
import React, { Component } from 'react'
import Bootstrap, { Form, Button, Row, Col, Container } from 'react-bootstrap'
// import bcrypt from 'bcryptjs';


export default class Register extends Component {
    constructor(props) {
        super(props);

        this.state = {
            firstName: "",
            lastName: "",
            email: "",
            password: "",
            secondPassword: ""
        };
    }

    validateForm() {
        return this.state.email.length > 0
            && this.state.password.length > 0
            && this.state.secondPassword.length > 0
            && this.state.password === this.state.secondPassword
            && this.state.firstName.length > 0
            && this.state.lastName.length > 0;
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    }

    handleSubmit = event => {
        // let hash = bcrypt.hashSync(this.state.password, 10);

        let credential = {
            fullName: this.state.firstName + ' ' + this.state.lastName,
            email: this.state.email,
            password: this.state.password //hash
        };

        axios.post(`http://localhost:8090/api/auth/register`, credential)
            .then(response => {
                alert('Success\Login using your email and password');
                console.log(response);
            });

        event.preventDefault();
    }

    render() {
        return (
            <Container className="mt-3">
                <Form
                    onSubmit={this.handleSubmit}>
                    <Form.Group as={Row} controlId="firstName" bsSize="large">
                        <Form.Label column sm="2">First name:</Form.Label>
                        <Col sm="3">
                            <Form.Control
                                autoFocus
                                type="text"
                                placeholder="First name"
                                value={this.state.firstName}
                                onChange={this.handleChange}
                            />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} controlId="lastName" bsSize="large">
                        <Form.Label column sm="2">Last name:</Form.Label>
                        <Col sm="3">
                            <Form.Control
                                type="text"
                                placeholder="Last name"
                                value={this.state.lastName}
                                onChange={this.handleChange}
                            />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} controlId="email" bsSize="large">
                        <Form.Label column sm="2">Email:</Form.Label>
                        <Col sm="3">
                            <Form.Control
                                type="email"
                                placeholder="Email"
                                value={this.state.email}
                                onChange={this.handleChange}
                            />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} controlId="password" bsSize="large">
                        <Form.Label column sm="2">Password:</Form.Label>
                        <Col sm="3">
                            <Form.Control
                                value={this.state.password}
                                onChange={this.handleChange}
                                placeholder="Password"
                                type="password"
                            />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} controlId="secondPassword" bsSize="large">
                        <Form.Label column sm="2">Confirm password:</Form.Label>
                        <Col sm="3">
                            <Form.Control
                                value={this.state.secondPassword}
                                placeholder="Confirm passsword"
                                onChange={this.handleChange}
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
                                Register
                                </Button>
                        </Col>
                    </Form.Group>
                </Form>
            </Container>
        );
    }
}