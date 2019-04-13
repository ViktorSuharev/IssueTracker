import * as axios from "axios";
import React, { Component } from "react";
import Bootstrap, { Form, Button, Table, Container } from 'react-bootstrap';

class HelloWorld extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            user: undefined
        };
    }


    handleSubmit = event => {
        event.preventDefault();

        let token = localStorage.getItem('token');

        axios.get(`http://localhost:8090/api/users/me`, {
            headers: {
                Authorization: token
            }
        })
            .then(response => {
                let u = response.data;
                this.setState({ user: u });
            });
    };

    getUserTable() {
        let u = this.state.user;
        let aboutUser =
            <Table striped bordered hover>
                <thead>
                    <th>id</th>
                    <th>name</th>
                    <th>email</th>
                </thead>
                <tbody>
                    <tr>
                        <td>{u.id}</td>
                        <td>{u.fullName}</td>
                        <td>{u.email}</td>
                    </tr>
                </tbody>
            </Table>

        return aboutUser;
    }

    render() {
        return <Container className="justify-content-md-center">
            <Form onSubmit={this.handleSubmit}>
                {this.state.user === undefined ? null : this.getUserTable()}
                <Button type="submit">
                    About me
                    </Button>
            </Form>
        </Container>;
    }
}

export default HelloWorld;