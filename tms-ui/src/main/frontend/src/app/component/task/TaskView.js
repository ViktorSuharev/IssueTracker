import React from 'react';
import * as axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css';
import { Form, Badge, Container, Table, Button } from 'react-bootstrap';
import TextEditor from '../TextEditor';
import { authorizationHeader } from '../../actions';
import { backurl } from '../../properties';
import TaskBoard from './/TaskBoard';
import { Link } from 'react-router';

export default class TaskView extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            id: this.props.match.params.id,
            task: {
                assignee: { fullName: '', id: 0 },
                reporter: { fullName: '', id: 0 },
            }
        };
    }

    componentDidMount() {
        var header = authorizationHeader();

        axios.get(backurl + '/tasks/' + this.state.id, header)
            .then(res => {
                const task = res.data;
                this.setState({ task: task });
            })
    };

    render() {
        var task = this.state.task;

        return <Container>
            
            <div className='float-right'>
                <Button variant='success'>&nbsp; Edit &nbsp;</Button>&nbsp;&nbsp;
                <Button variant='danger'>&nbsp; Delete &nbsp;</Button>
            </div>
            <div className='flex-row'>
                {task.priority ? <Badge className='align-self-start' variant={priorities[task.priority].color}>{task.priority}</Badge> : null}
                &nbsp;
                {task.priority ? <Badge className='align-self-start' variant={statuses[task.status].color}>{statuses[task.status].name}</Badge> : null}

                <div className='py-2  flex-grow-1'>
                    <h1>{task.name}</h1>
                </div>
            </div>
            <hr />


            {task.description ? <TextEditor
                readOnly={true}
                value={task.description} /> : null
            }
            <hr />
            <h3>People</h3>
            <ul>
                {task.assignee ? <li>Assignee: <a href={'/users/' + task.assignee.id}>{task.assignee.fullName}</a></li> : null}
                {task.reporter ? <li>Reporter: <a href={'/users/' + task.reporter.id}>{task.reporter.fullName}</a></li> : null}

                {/* <li>Deadline: {this.state.task.dueDate}</li> */}
                {/* {task.modificationDate ? <li>Last modified: {this.state.task.modificationDate}</li> : null} */}
            </ul>
            <br />

            <h3>Dates</h3>
            <ul>
                <li>Created: {task.creationDate}</li>
                <li>Deadline: {task.dueDate}</li>
                {task.modificationDate ? <li>Last modified: {task.modificationDate}</li> : null}
            </ul>
            <br />
            <hr />

            <h3>History</h3>
            <br />

            {/* <Table striped bordered hover>
                <thead className='thead-dark'>
                    <tr>
                        <th>name</th>
                        <th>email</th>
                        <th>role</th>
                    </tr>
                </thead>
                <tbody>
                    {this.state.team.map(u => <tr>
                        <td>{u.name}</td>
                        <td>{u.email}</td>
                        <td>{u.role}</td>
                    </tr>)}
                </tbody>
            </Table> */}
            <hr />
        </Container>
    }
}

const priorities = {
    MINOR: { color: 'secondary', name: 'MINOR' },
    MAJOR: { color: 'primary', name: 'MAJOR' },
    CRITICAL: { color: 'warning', name: 'CRITICAL' },
    BLOCKER: { color: 'danger', name: 'BLOCKER' }
};

const statuses = {
    NOT_STARTED: { color: 'secondary', name: 'NOT STARTED' },
    CANCELED: { color: 'danger', name: 'CANCELED' },
    IN_PROGRESS: { color: 'info', name: 'IN_PROGRESS' },
    RESOLVED: { color: 'primary', name: 'RESOLVED' },
    CLOSED: { color: 'dark', name: 'CLOSED' }
};
