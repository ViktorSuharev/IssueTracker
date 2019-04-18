import * as axios from 'axios';
import React, { Component } from 'react';
import { Container } from 'react-bootstrap'
import { backurl } from '../../properties';
import { authorizationHeader } from '../../actions';
import TaskBoard from './TaskBoard';
// import ProjectContainer from './ProjectContainer';

export default class AllTasks extends Component {
    constructor(props) {
        super(props);

        this.state = {
            tasks: []
        }
    }

    componentDidMount() {
        axios.get(backurl + '/tasks/', authorizationHeader())
            .then(response => {
                const tasks = response.data;
                this.setState({ tasks: tasks });
            })
            .catch((error) => {
                alert('error');
                this.setState({ status: error.response.status });
            });
    }

    render() {
        return <Container>
            <h4> All Tasks </h4> 
            <hr />
            <TaskBoard tasks={this.state.tasks}/>            
        </Container>;
    }
}