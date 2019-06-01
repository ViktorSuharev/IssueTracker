import * as axios from 'axios';
import React, { Component } from 'react';
import { Container } from 'react-bootstrap'
import { backurl } from '../../properties';
import { authorizationHeader } from '../../actions';
import TaskBoard from './TaskBoard';

export default class SearchTasks extends Component {
    constructor(props) {
        super(props);

        this.state = {
            name: this.props.match.params.name,

            tasks: []
        }
    }

    componentDidMount() {
        axios.get(backurl + '/tasks/search/' + this.state.name, authorizationHeader())
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
            <h3> Search results for "{this.props.match.params.name}" </h3> 
            {/* <hr /> */}
            <hr />
            <TaskBoard tasks={this.state.tasks}/>            
        </Container>;
    }
}