import * as axios from "axios";
import React, { Component } from "react";
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import Bootstrap from "react-bootstrap";

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
        let credential = {
            fullName: this.state.firstName + ' ' + this.state.lastName,
            email: this.state.email,
            password: this.state.password
        }
        axios.post(`http://localhost:8090/api/register`, credential)
            .then(response => {
                //showSuccessPage
                console.log("success");
                console.log(response);
            });

        event.preventDefault();
    }

    render() {
        return (
            <div className="Register">
                <Form onSubmit={this.handleSubmit}>
                    <Form.Group controlId="firstName" bsSize="large">
                        <Form.Control
                            autoFocus
                            type="text"
                            value={this.state.firstName}
                            onChange={this.handleChange}
                        />
                    </Form.Group>
                    <Form.Group controlId="lastName" bsSize="large">
                        <Form.Control
                            type="text"
                            value={this.state.lastName}
                            onChange={this.handleChange}
                        />
                    </Form.Group>
                    <Form.Group controlId="email" bsSize="large">
                        <Form.Control
                            type="email"
                            value={this.state.email}
                            onChange={this.handleChange}
                        />
                    </Form.Group>
                    <Form.Group controlId="password" bsSize="large">
                        <Form.Control
                            value={this.state.password}
                            onChange={this.handleChange}
                            type="password"
                        />
                    </Form.Group>
                    <Form.Group controlId="secondPassword" bsSize="large">
                        <Form.Control
                            value={this.state.secondPassword}
                            onChange={this.handleChange}
                            type="password"
                        />
                    </Form.Group>
                    <Button
                        block
                        bsSize="large"
                        disabled={!this.validateForm()}
                        type="submit"
                    >
                        Register
                    </Button>
                </Form>
            </div>
        );
    }
}