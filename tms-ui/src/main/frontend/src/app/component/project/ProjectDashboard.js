import * as axios from 'axios';
import React, { Component } from 'react';
import { Container } from 'react-bootstrap'
import ProjectContainer from './ProjectContainer';

export default class ProjectDashboard extends Component {
    constructor(props) {
        super(props);

        this.state = {
            projects: []
        }
    }

    componentDidMount() {
        let token = localStorage.getItem('token');

        axios.get('http://localhost:8090/api/projects/', {
            headers: {
                Authorization: token
            }
        })
            .then(response => {
                const projects = response.data;
                this.setState({ projects: projects });
            })
            .catch((error) => {
                alert('error');
                this.setState({ status: error.response.status });
            });
    }

    render() {
        return <Container>
            <ProjectContainer projects={this.state.projects}/>
        </Container>;
    }
}