import * as axios from 'axios';
import React, { Component } from 'react';
import { Form, Modal, Button, Row, Col, Container } from 'react-bootstrap';
import { backurl } from '../../properties';

export default class Register extends Component {
    constructor(props) {
        super(props);

        this.state = {
            firstName: '',
            lastName: '',
            email: '',
            password: '',
            secondPassword: '',
            show: false,
            modal: { header: '', content: '', action: null }
        };

        this.handleOk = this.handleOk.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
    }

    handleClose() {
        this.setState({ show: false });
    }

    handleShow(type, message, action) {
        var modal = {
            header: type,
            content: message,
            action: action
        };

        this.setState({ show: true, modal: modal });
    }

    handleOk(event) {
        event.preventDefault();
        this.handleClose();
    }

    handleReload(event) {
        event.preventDefault();
        window.location.reload();
    }

    validateForm() {
        return this.state.email.length > 0
            && this.state.password.length > 0
            && this.state.secondPassword.length > 0
            && this.state.password === this.state.secondPassword
            && this.state.firstName.length > 0
            && this.state.lastName.length > 0
            && this.state.email.match(/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i);
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    }

    handleSubmit = event => {
        event.preventDefault();

        let credential = {
            fullName: this.state.firstName + ' ' + this.state.lastName,
            email: this.state.email,
            password: this.state.password
        };

        axios.post(backurl + 'auth/register', credential)
            .then(response => {
                this.handleShow('Congratulations!', 'Registration was successful. Please, login', this.handleReload);
                console.log(response);
            })
            .catch(error => {
                switch (error.response.status) {
                    case 406:
                        this.handleShow('Error', 'User with email ' + credential.email + ' is already registered', this.handleOk);
                        break;
                    default:
                        console.log('ERROR while registration: ', JSON.stringify(error.response));
                        this.handleShow('Error', 'Please, contact administrators. Error type: ' + error.response.status, this.handleOk);
                }
            });
    }

    render() {
        let disabled = !this.validateForm();
        return <div>
            <Modal show={this.state.show} onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>{this.state.modal.header}</Modal.Title>
                </Modal.Header>
                <Modal.Body>{this.state.modal.content}</Modal.Body>
                <Modal.Footer>
                    <Button variant='primary' onClick={this.state.modal.action}>
                        Ok
                        </Button>
                </Modal.Footer>
            </Modal>

            <Container className='mt-3'>
                <Form
                    onSubmit={this.handleSubmit}>
                    <Form.Group as={Row} controlId='firstName' >
                        <Form.Label column sm='2'>First name:</Form.Label>
                        <Col sm='3'>
                            <Form.Control
                                autoFocus
                                type='text'
                                placeholder='First name'
                                value={this.state.firstName}
                                onChange={this.handleChange}
                            />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} controlId='lastName' >
                        <Form.Label column sm='2'>Last name:</Form.Label>
                        <Col sm='3'>
                            <Form.Control
                                type='text'
                                placeholder='Last name'
                                value={this.state.lastName}
                                onChange={this.handleChange}
                            />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} controlId='email' >
                        <Form.Label column sm='2'>Email:</Form.Label>
                        <Col sm='3'>
                            <Form.Control
                                type='email'
                                placeholder='Email'
                                value={this.state.email}
                                onChange={this.handleChange}
                            />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} controlId='password' >
                        <Form.Label column sm='2'>Password:</Form.Label>
                        <Col sm='3'>
                            <Form.Control
                                value={this.state.password}
                                onChange={this.handleChange}
                                placeholder='Password'
                                type='password'
                            />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} controlId='secondPassword' >
                        <Form.Label column sm='2'>Confirm password:</Form.Label>
                        <Col sm='3'>
                            <Form.Control
                                value={this.state.secondPassword}
                                placeholder='Confirm passsword'
                                onChange={this.handleChange}
                                type='password'
                            />
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row}>
                        <Col sm={5}>
                            <Button
                                block
                                variant={disabled ? 'outline-primary' : 'primary'}
                                disabled={disabled}
                                type='submit'
                            >
                                Register
                                </Button>
                        </Col>
                    </Form.Group>
                </Form>
            </Container>
        </div>
    }
}