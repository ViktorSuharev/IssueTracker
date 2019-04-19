import * as axios from 'axios';
import React, { Component } from 'react';
import { Container } from 'react-bootstrap'
import { backurl } from '../../properties';
import { authorizationHeader } from '../../actions';
import TaskBoard from './TaskBoard';

export default class MyTasks extends Component {
    constructor(props) {
        super(props);

        this.state = {
            toDo: [],
            toReport: []
        }
    }

    componentDidMount() {
        axios.get(backurl + '/tasks/my', authorizationHeader())
            .then(response => {
                const tasks = response.data;

                this.setState({ toDo: tasks.toDo, toReport: tasks.toReport });
            })
            .catch((error) => {
                alert('error');
                this.setState({ status: error.response.status });
            });
    }

    render() {
        return <Container>
            <h3> To Do</h3>
            <hr />
            <TaskBoard tasks={this.state.toDo} />
            <hr /><br/>

            <h3> Report </h3>
            <hr />
            <TaskBoard tasks={this.state.toReport} />
        </Container>;
    }
}