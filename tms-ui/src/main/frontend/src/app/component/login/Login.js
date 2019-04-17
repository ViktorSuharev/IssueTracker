import React, { Component } from 'react';
import { Modal, Form, Button, Row, Col, Container } from 'react-bootstrap'

export default class Login extends Component {
    constructor(props) {
        super(props);

        let us = JSON.parse(localStorage.getItem('userCredentrials'));
        if (!us)
            us = { email: '', password: '' };

        this.state = {
            email: us.email,
            password: us.password,
            show: false,
            success: false,
            errorMessage: '',
            status: 0
        };

        this.handleLogin = this.handleLogin.bind(this);
        this.handleOk = this.handleOk.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
    }

    handleClose() {
        this.setState({ show: false });
    }

    handleShow() {
        this.setState({ show: true });
    }

    handleOk(event) {
        event.preventDefault();
        this.setState({ show: false });
    }

    async handleLogin(event) {
        event.preventDefault();

        let creds = {
            email: this.state.email,
            password: this.state.password
        }

        let status = await this.props.onLogin(creds);

        switch (status) {
            case 200:
                break;
            case 401:
                this.setState({ errorMessage: 'Wrong email or password' });
                this.handleShow();
                break;
            case 0:
                this.setState({ errorMessage: 'Server is unavailable. Please, try again later'});
                this.handleShow();
                break;                
            default:
                this.setState({ errorMessage: 'Contact administrator. Error code: ' + status });
                this.handleShow();
            
        }

    }

    validateForm() {
        return this.state.email.length > 0
            && this.state.password.length > 0
            && this.state.email.match(/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i);
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    }

    render() {
        var disabled = !this.validateForm();
        return (
            <div>
                <Modal show={this.state.show} onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>Error!</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>{this.state.errorMessage}</Modal.Body>
                    <Modal.Footer>
                        <Button variant='primary' onClick={this.handleOk}>
                            Ok
            </Button>
                    </Modal.Footer>
                </Modal>
                <Container className='mt-3'>
                    <Form>
                        <Form.Group as={Row} controlId='email'>
                            <Form.Label column sm={1}>Email:</Form.Label>
                            <Col sm={4}>
                                <Form.Control
                                    autoFocus
                                    type='email'
                                    placeholder='Email'
                                    value={this.state.email}
                                    onChange={this.handleChange}
                                />
                            </Col>
                        </Form.Group>

                        <Form.Group as={Row} controlId='password'>
                            <Form.Label column sm={1}>Password:</Form.Label>
                            <Col sm={4}>
                                <Form.Control
                                    value={this.state.password}
                                    onChange={this.handleChange}
                                    placeholder='Password'
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
                                    onClick={this.handleLogin}
                                >
                                    Login
                                </Button>
                            </Col>
                        </Form.Group>
                    </Form>
                </Container>
            </div>
        );
    }
}