import React, { Component } from 'react';
import { Modal, Badge, Container, Card, CardDeck, Button } from 'react-bootstrap'
import { shortenIfLong } from '../../actions';
import axios from 'axios';
import { backurl } from '../../properties';
import { authorizationHeader } from '../../actions';

export default class TaskBoard extends Component {
    constructor(props) {
        super(props);

        this.state = {
            deleteTask: -1,
            show: false
        }

        this.onDelete = this.onDelete.bind(this);
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

    onDelete(event) {
        const taskId = event.target.value;

        const task = this.props.tasks
            .find((p) => p.id == taskId);

        this.setState({ deleteTask: task });
        this.handleShow(event);
    }

    deleteTask(event) {
        event.preventDefault();

        const task = this.state.deleteTask;
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


    makeControlLinks(task) {
        if (task === stubTask)
            return;

        let edit = '/tasks/edit/' + task.id;

        return <div className='float-right'>
            <Button size='sm' variant='outline-success' value={task.id} href={edit}>&nbsp; Edit &nbsp;</Button>
            &nbsp; &nbsp;
            <Button size='sm' variant='outline-danger' value={task.id} onClick={this.onDelete}>Delete</Button>
        </div>
    }

    processTaskElement(task) {
        if (task === stubTask)
            return <Card>
                <Card.Header><h4>Add new</h4></Card.Header>
                <Card.Body className='d-flex' align='center'>
                    <Button className='p-5 w-100 rounded' variant='outline-secondary' href='/tasks/new'>
                        <h1 className='display-3'>+</h1>
                    </Button>
                </Card.Body>
            </Card>

        return <Card>
            <Card.Header>
                <Card.Link style={{ color: 'black' }} href={'/tasks/' + task.id}> <h4>{shortenIfLong(task.name, 25)}</h4> </Card.Link>
            </Card.Header>
            <Card.Body>
                {this.getTaskInfo(task)}
            </Card.Body>
            <Card.Body>
                <Card.Text>
                    Assignee &nbsp; <a href={'/users/' + task.assignee.id}>{task.assignee.name}</a>
                    <br />
                    Reporter &nbsp; <a href={'/users/' + task.reporter.id}>{task.reporter.name}</a>
                </Card.Text>
                {this.makeControlLinks(task)}
            </Card.Body>
        </Card>
    }

    getTaskInfo(task) {
        return <Card.Subtitle className='mb-2 text-muted'>
            <Badge variant={priorities[task.priority].color}>{task.priority}</Badge>
            &nbsp;
            <Badge variant={statuses[task.status].color}>{statuses[task.status].name}</Badge>
            <br />
            Deadline: &nbsp;{task.dueDate}
            <br/>
            Created: &nbsp; &nbsp;{task.creationDate}

        </Card.Subtitle>
    }

    modalDeleteTask = () => <Modal show={this.state.show} onHide={this.handleClose}>
        <Modal.Header closeButton>
            <Modal.Title>Delete task</Modal.Title>
        </Modal.Header>
        <Modal.Body>Are you sure you want to delete task '{this.state.deleteTask.name}'?</Modal.Body>
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
        const colNum = 3;
        const tasks = this.props.tasks;

        while (tasks.length % colNum)
            tasks.push(stubTask);

        var matrix = reshape(tasks, colNum);

        return <div>
            {this.modalDeleteTask()}
            <Container>
                {matrix.map((row) => <div><CardDeck>
                    {row.map(task => this.processTaskElement(task))}
                </CardDeck>
                    <br />
                </div>
                )}
            </Container>
        </div>
    }
}

function reshape(list, elementsPerSubArray) {
    var matrix = [], i, k;
    for (i = 0, k = -1; i < list.length; i++) {
        if (i % elementsPerSubArray === 0) {
            k++;
            matrix[k] = [];
        }
        matrix[k].push(list[i]);
    }
    return matrix;
}

const stubTask = { name: '', description: '', owner: { name: null } };


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
