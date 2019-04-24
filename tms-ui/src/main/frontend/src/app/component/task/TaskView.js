import React from 'react';
import * as axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css';
import { Modal, Badge, Container, Table, Button } from 'react-bootstrap';
import TextEditor from '../TextEditor';
import { authorizationHeader } from '../../actions';
import { backurl } from '../../properties';
import { Link } from 'react-router-dom';

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

        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
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

                axios.get(backurl + '/tasks/history/' + this.state.id, header)
                    .then(res => {
                        var history = res.data;
                        this.setState({ history: history });
                    })
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
                    <Button variant='success'><Link className='link' to={'/tasks/edit/' + this.state.id}>&nbsp; Edit &nbsp;</Link></Button>&nbsp;&nbsp;
                    <Button variant='danger' onClick={this.handleShow}>&nbsp; Delete &nbsp;</Button>
                </div>
                <div className='flex-row'>
                    {task.priority ? <Badge className='align-self-start' variant={priorities[task.priority].color}>{task.priority}</Badge> : null}
                    &nbsp;
                {task.priority ? <Badge className='align-self-start' variant={statuses[task.status].color}>{statuses[task.status].name}</Badge> : null}

                    <h1 style={{ wordBreak: 'break-all' }}>{task.name}</h1>
                </div>
                <hr />
                {task.project ? <div>Project: <Link className='black-link' to={'/projects/' + task.project.id}>{task.project.name}</Link> </div> : null}
                <br/>


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

                {this.state.history && this.state.history.length ? <div>
                <br />
                <hr /><h3>History</h3>
                <br />

                 <Table striped bordered hover>
                    <thead className='thead-dark'>
                        <tr>
                            <th>Date</th>
                            <th>Author</th>
                            <th>Comment</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.history.map(h => <tr>
                            <td style={{ 'width': '20%' }}>{h.created.substring(0, 10) + ' at ' + h.created.substring(11, 16)}</td>
                            <td style={{ 'width': '20%' }}>{h.author.name}</td>
                            <td >{h.comment}</td>
                        </tr>)}
                    </tbody>
                </Table></div> : null}
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
