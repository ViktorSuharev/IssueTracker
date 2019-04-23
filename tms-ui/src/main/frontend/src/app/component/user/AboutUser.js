import * as axios from 'axios';
import React from 'react';
import { Table, Container } from 'react-bootstrap';
import { backurl } from '../../properties';
import { authorizationHeader } from '../../actions';
import TaskBoard from '../task/TaskBoard';
import { Link } from 'react-router-dom';

export default class AboutUser extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            id: props.match.params.id,
        };
    }

    componentDidMount() {
        var id = this.state.id;

        axios.get(backurl + '/users/' + id, authorizationHeader())
            .then(response => {
                var u = response.data;
                this.setState({ user: u });
            });

        axios.get(backurl + '/projects/member/' + id, authorizationHeader())
            .then(response => {
                var projectsAsMember = response.data;
                this.setState({ projectsAsMember: projectsAsMember });
            });

        axios.get(backurl + '/projects/creator/' + id, authorizationHeader())
            .then(response => {
                var projectsAsCreator = response.data;
                this.setState({ projectsAsCreator: projectsAsCreator });
            });

        axios.get(backurl + '/tasks/active/assignee/' + id, authorizationHeader())
            .then(response => {
                var activeTasks = response.data;
                this.setState({ activeTasks: activeTasks });
            });

        axios.get(backurl + '/tasks/resolved/assignee/' + id, authorizationHeader())
            .then(response => {
                var resolvedTasks = response.data;
                this.setState({ resolvedTasks: resolvedTasks });
            });
    };

    aboutUser() {
        var u = this.state.user;

        return <div>
            <h4>About user:</h4>
            <p>&nbsp; {u.name}
                <br />
                &nbsp; {u.email}
            </p>
            <hr />
        </div>
    }

    memberOf() {
        return <div>
            <h4>Member of projects: </h4>

            <Table striped bordered hover>
                <thead className='thead-dark'>
                    <th>Name</th>
                    <th>Role</th>
                </thead>
                <tbody>
                    {
                        this.state.projectsAsMember.map(el => <tr>
                            <td><Link className='black-link' to={'/projects/' + el.project.id}>{el.project.name}</Link></td>
                            <td>{el.role.name}</td>
                        </tr>
                        )}
                </tbody>
            </Table>
            <hr />

        </div>
    }

    creatorOf() {
        return <div>
            <h4>Creator of projects: </h4>

            <Table striped bordered hover>
                <thead className='thead-dark'>
                    <th>Name</th>
                </thead>
                <tbody>
                    {this.state.projectsAsCreator.map(
                        el => <tr>
                            <td><Link className='black-link' to={'/projects/' + el.id}>{el.name}</Link></td>
                        </tr>
                    )}
                </tbody>
            </Table>
            <hr />
        </div>
    }

    showActive() {
        return <div>
            <h4>Active tasks: </h4>
            {this.state.activeTasks.length === 0 ? 'No active tasks' :
                <TaskBoard tasks={this.state.activeTasks} />}
            <hr />
        </div>
    }

    showresolved() {
        return <div>
            <h4>Resolved tasks: </h4>
            {this.state.resolvedTasks.length === 0 ? 'No resolved tasks' :
                <TaskBoard tasks={this.state.resolvedTasks} />}
        </div>
    }


    render() {
        console.log(JSON.stringify(this.state))

        return <Container>
            {this.state.user ? this.aboutUser() : null}
            {this.state.projectsAsMember ? this.memberOf() : null}
            {this.state.projectsAsCreator ? this.creatorOf() : null}
            {this.state.activeTasks ? this.showActive() : null}
            {this.state.resolvedTasks ? this.showresolved() : null}
        </Container>;
    }
}