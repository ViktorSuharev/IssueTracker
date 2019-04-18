import * as axios from 'axios';
import React, { Component } from 'react';
import { Container } from 'react-bootstrap'
import ProjectContainer from './ProjectContainer';
import { backurl } from '../../properties';
import { authorizationHeader } from '../../actions';

export default class ProjectDashboard extends Component {
    constructor(props) {
        super(props);

        this.state = {
            projects: []
        }
    }

    componentDidMount() {

        axios.get(backurl + '/projects/', authorizationHeader())
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
            <h3> All projects</h3>
            <hr />
            <ProjectContainer projects={this.state.projects} />
        </Container>;
    }
}