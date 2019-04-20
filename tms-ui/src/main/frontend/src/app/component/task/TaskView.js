import React from 'react';
import * as axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css';
import { Form, Modal, Badge, Container, Table, Button } from 'react-bootstrap';
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

        this.deleteTask = this.deleteTask.bind(this);

        this.handleCancel = this.handleCancel.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
    }

    handleCancel(event) {
        event.preventDefault();
    }

    handleClose() {
        this.setState({ show: false });
    }

    handleShow(event) {
        event.preventDefault();

        this.setState({ show: true });
    }

    deleteTask(event) {
        event.preventDefault();

        const task = this.state.task;
        axios.delete(backurl + '/tasks/' + task.id, authorizationHeader())
            .then(response => {
                alert(task.name + ' deleted');
                window.location.reload(false);
            })
            .catch(error => {
                alert(error.response.status);
            })

        this.handleClose();
    }


    componentDidMount() {
        var header = authorizationHeader();

        axios.get(backurl + '/tasks/' + this.state.id, header)
            .then(res => {
                const task = res.data;
                this.setState({ task: task });
            })
    };

    modalDeleteTask = () => <Modal show={this.state.show} onHide={this.handleClose}>
        <Modal.Header closeButton>
            <Modal.Title>Delete task</Modal.Title>
        </Modal.Header>
        <Modal.Body>Are you sure you want to delete task '{this.state.task.name}'?</Modal.Body>
        <Modal.Footer>
            <Button variant='secondary' onClick={this.handleClose}>
                Cancel
        </Button>
            <Button variant='danger' onClick={this.deleteTask}>
                Delete
        </Button>
        </Modal.Footer>
    </Modal>

    render() {
        var task = this.state.task;

        return <div>
            {this.modalDeleteTask()}
            <Container>
                <div className='float-right'>
                    <Button variant='success'>&nbsp; Edit &nbsp;</Button>&nbsp;&nbsp;
                    <Button variant='danger' onClick={this.handleShow}>&nbsp; Delete &nbsp;</Button>
                </div>
                <div className='flex-row'>
                    {task.priority ? <Badge className='align-self-start' variant={priorities[task.priority].color}>{task.priority}</Badge> : null}
                    &nbsp;
                {task.priority ? <Badge className='align-self-start' variant={statuses[task.status].color}>{statuses[task.status].name}</Badge> : null}

                    <h1 style={{ wordBreak: 'break-all' }}>{task.name}</h1>
                </div>
                <hr />


                {task.description ? <TextEditor
                    readOnly={true}
                    value={task.description} /> : null
                }
                <hr />
                <h3>People</h3>
                <ul>
                    {task.assignee ? <li>Assignee: <a href={'/users/' + task.assignee.id}>{task.assignee.name}</a></li> : null}
                    {task.reporter ? <li>Reporter: <a href={'/users/' + task.reporter.id}>{task.reporter.name}</a></li> : null}

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
        </div>

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
