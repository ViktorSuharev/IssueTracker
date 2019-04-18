import * as axios from 'axios';
import React from 'react';
import {Table, Container} from 'react-bootstrap';
import { backurl } from '../../properties';

export default class AboutUser extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            id: props.match.params.id,
            user: []
        };
    }

    componentDidMount() {
        let id = this.state.id;
        
        let token = localStorage.getItem('token');

        axios.get(backurl + '/users/' + id, {
            headers: {
                Authorization: token
            }
        })
            .then(response => {
                let u = response.data;
                this.setState({user: u});
            });
    };

    render() {
        let u = this.state.user;
        let aboutUser = <Table striped bordered hover>
                <thead>
                    <th>name</th>
                    <th>email</th>
                </thead>
                <tbody>
                    <tr>
                        <td>{u.name}</td>
                        <td>{u.email}</td>
                    </tr>
                </tbody>
            </Table>
            
        return <Container>{aboutUser}</Container>;
    }
}