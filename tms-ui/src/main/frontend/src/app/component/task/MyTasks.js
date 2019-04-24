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

    showToDo() {
        return <TaskBoard tasks={this.state.toDo} />
    }

    showToReport() {
        return <TaskBoard tasks={this.state.toReport} />
    }



    render() {
        return <Container>
            <h3> To Do</h3>
            <hr />
            {this.state.toDo.length > 0 ? this.showToDo() : 'Nothing to do'}
            <hr />
            <h3> Report </h3>
            <hr />
            {this.state.toReport.length > 0 ? this.showToReport() : 'Nothing to report'}
        </Container>;
    }
}