import * as axios from "axios";
import React, {Component} from "react";
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import Bootstrap from "react-bootstrap";
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col'

class HelloWorld extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            users: []
        };
    }


    handleSubmit = event => {
        console.log("start sending");

        const jwt = JSON.parse(localStorage.getItem('jwt'));

        axios.get(`http://localhost:8090/users/all`, {
            headers: {
                jwt
            }
        })
            .then(response => {
                this.setState(JSON.parse(response.data));
                console.log(JSON.stringify(this.state));
            });
    };

    render() {
        return <Form onSubmit={this.handleSubmit}>
                    <Button type="submit">
                        Send request with token
                    </Button>
                </Form>;
    }
}

export default HelloWorld;