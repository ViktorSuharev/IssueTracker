import * as axios from "axios";
import React from "react";
import {Table} from 'react-bootstrap';

export default class AboutUser extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            id: -1,
            user: undefined
        };
    }

    componentDidMount() {
        if (this.state.id === -1) return;
        
        let id = this.state.id;
        
        let token = localStorage.getItem('token');

        axios.get(`http://localhost:8090/api/users/` + id, {
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
        let aboutUser = 
            <Table striped bordered hover>
                <thead>
                    <th>name</th>
                    <th>email</th>
                </thead>
                <tbody>
                    <tr>
                        <td>{u.fullName}</td>
                        <td>{u.email}</td>
                    </tr>
                </tbody>
            </Table>
            
        return aboutUser;
    }
}