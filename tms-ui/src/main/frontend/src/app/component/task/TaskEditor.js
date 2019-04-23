import React from 'react';
import { Modal, Button, Container, Form } from 'react-bootstrap';
import axios from 'axios';
import { backurl } from '../../properties';
import { authorizationHeader } from '../../actions';
import TextEditor from '../TextEditor';
import './styles.css';

import 'react-dates/initialize';
import 'react-dates/lib/css/_datepicker.css';
import { SingleDatePicker } from 'react-dates';
import 'bootstrap/dist/css/bootstrap.min.css';

export default class TaskEditor extends React.Component {
    constructor(props) {
        super(props);

        const date = new Date();

        this.state = {
            id: this.props.match.params.id,

            projects: [],
            users: [],
            focuser: false,
            show: false
            // date: '2019/04/23'
        }

        this.onUpdateTask = this.onUpdateTask.bind(this);
        this.handleComment = this.handleComment.bind(this);

        this.onDescriptionSave = this.onDescriptionSave.bind(this);
        this.onDueDateChanged = this.onDueDateChanged.bind(this);

        this.handleCancel = this.handleCancel.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);

        this.color = this.color.bind(this);

        this.taskEditor = this.taskEditor.bind(this);

        this.onSubmitTask = this.onSubmitTask.bind(this);
    }

    handleComment(event) {
        this.setState({comment: event.target.value})
    }

    updateTask(target, value) {
        var task = { ...this.state.task };
        task[target] = value;
        this.setState({ task: task });
    }

    onUpdateTask(event) {
        const prop = event.target.name;
        const val = event.target.value;

        this.updateTask(prop, val);
    }

    onDescriptionSave(value) {
        this.setState({ desc: value });
        this.updateTask(description, value);
    }

    onDueDateChanged(date) {
        this.updateTask('dueDate', date);
        this.setState({ date: date });
    }

    handleCancel(event) {
        event.preventDefault();
        this.props.history.goBack();
    }

    handleClose() {
        this.setState({ show: false });
    }

    handleShow(event) {
        event.preventDefault();
        this.setState({ show: true });
    }

    componentDidMount() {
        var reqHeader = authorizationHeader();

        axios.get(backurl + '/tasks/' + this.state.id, reqHeader)
            .then((response) => {
                const task = response.data;

                var t = {
                    name: task.name,
                    description: task.description,
                    assignee: task.assignee.id,
                    reporter: task.reporter.id,
                    priority: task.priority,
                    status: task.status,
                    dueDate: task.dueDate,
                    project: task.project.id
                }
                
                this.setState({task: t})
            });


        axios.get(backurl + '/users/', reqHeader)
            .then((response) => {
                const users = response.data;
                this.setState({ users: users });
            });

        axios.get(backurl + '/projects/', reqHeader)
            .then((response) => {
                const projects = response.data;
                this.setState({ projects: projects });
            });
    }

    onSubmitTask(event) {
        event.preventDefault();

        if (!this.state.task.name) {
            alert('Empty name');
            this.handleClose();
            return;
        }

        if (!this.state.task.dueDate) {
            alert('No due date');
            this.handleClose();
            return;
        }

        if (!this.state.task.description) {
            alert('Empty description');
            this.handleClose();
            return;
        }

        if (!this.state.comment) {
            alert('Empty comment');
            this.handleClose();
            return;
        }

        let header = authorizationHeader();

        const t = {
            id: this.state.id,
            name: this.state.task.name,
            description: this.state.task.description,
            assigneeId : this.state.task.assignee,
            reporterId: this.state.task.reporter,
            projectId: this.state.task.project,
            priority: this.state.task.priority,
            status: this.state.task.status,
            dueDate: this.state.task.dueDate
        }
        const c = this.state.comment;

        axios(backurl + '/tasks/' + this.state.id, {
            method: 'PUT',
            headers: header.headers,
            data: {
                task: t,
                comment: c
            }
        })
            .then(res => {
                console.log(res.status);
                console.log(res.data);
                this.handleClose();
                this.props.history.goBack();
            })
            .catch(error => {
                switch (error.response.status) {
                    case 406:
                        alert('Task for the project with current name already exists');
                        this.handleClose();
                        break;
                    default:
                        console.log('ERROR while creating task: ', JSON.stringify(error.response));
                        alert('Error\nPlease, contact administrators. Error type: ' + error.response.status)
                }
                this.handleClose();
            });

    }

    color() {
        return priorities.find(p => p.name === this.state.task.priority).color;
    }

    taskEditor(task) {
        console.log(JSON.stringify(task));

        return <Container>
            <div className='float-right'>
                <Button className='d-inline-flex p-2 main-control' variant='secondary' onClick={this.handleCancel}>Cancel</Button>&nbsp;
            <Button className='d-inline-flex p-2 main-control' variant='success' onClick={this.handleShow}>Update</Button>
            </div>
            <h2>Edit task</h2>
            <hr />

            <Form inline>
                <Form.Label>Task name: &emsp;</Form.Label>
                <Form.Control type='text' name={name} onChange={this.onUpdateTask} placeholder='Task name' defaultValue={task.name} />
            </Form>
            <br />

            <Form inline>
                <Form.Label>Assignee:&emsp;&emsp;</Form.Label>
                <Form.Control as='select' name={assignee} defaultValue={task.assignee} onChange={this.onUpdateTask}>
                    {this.state.users.map((user) => <option key={user.id} value={user.id}>{user.name}</option>)}
                </Form.Control>
            </Form>
            <br />

            { task.reporter ? <Form inline>
                <Form.Label>Reporter:&emsp;&emsp;</Form.Label>
                <Form.Control as='select' name={reporter} defaultValue={task.reporter}  onChange={this.onUpdateTask}>
                    {this.state.users.map((user) => <option value={user.id}>{user.name}</option>)}
                </Form.Control>
            </Form> : null}
            <br />

            {task.project ? <Form inline>
                <Form.Label>Project:&emsp;&emsp;&emsp;</Form.Label>
                <Form.Control as='select' name={project} defaultValue={task.project} onChange={this.onUpdateTask}>
                    {this.state.projects.map((project) => <option value={project.id}>{project.name}</option>)}
                </Form.Control>
            </Form> : null}
            <br />
 
            {task.priority ? <Form inline >
                 <Form.Label>Priority:&emsp;&emsp;&emsp;</Form.Label>
                 <Form.Control as='select' style={{ color: this.color() }} defaultValue={task.priority} name={priority} onChange={this.onUpdateTask}>
                     {priorities.map((priority) => <option value={priority.name} style={{ color: priority.color }}>{priority.name}</option>)}
                 </Form.Control>
             </Form> : null}

             <br/>

             {task.status ? <Form inline >
                 <Form.Label>Status:&emsp; &emsp;&emsp;</Form.Label>
                 <Form.Control as='select' defaultValue={task.status} name={status_} onChange={this.onUpdateTask}>
                     {statuses.map((status) => <option value={status.name}>{status.name}</option>)}
                 </Form.Control>
             </Form> : null}

            <br />
            <Form inline>
                Due date:&emsp;&emsp;
                <SingleDatePicker
                    small={true}
                    numberOfMonths={1}
                    date={this.state.date}
                    onDateChange={date => this.onDueDateChanged(date)}
                    focused={this.state.focused}
                    onFocusChange={({ focused }) =>
                        this.setState({ focused })
                    }
                    openDirection='up'
                    hideKeyboardShortcutsPanel={true}
                />
                {/* </Form.Control> */}
            </Form>
            <br />

            <Form>
                <Form.Label>Description</Form.Label>
                {task.description ? <TextEditor
                    placeholder='Enter description here...'
                    onSave={this.onDescriptionSave}
                    value={task.description}
                /> : null}
            </Form>            
            <br />
            <Form inline>
                <Form.Label>Comment modification: &emsp;</Form.Label>
                <Form.Control 
                    type='text' 
                    onChange={this.handleComment}
                    placeholder='Comment'/>
            </Form>
        </Container>
    }

    render() {
        const task = this.state.task;
        return <div>
            <Modal show={this.state.show} onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Update task</Modal.Title>
                </Modal.Header>
                <Modal.Body>Are you sure you want to update task?</Modal.Body>
                <Modal.Footer>
                    <Button variant='secondary' onClick={this.handleClose}>
                        Cancel
                        </Button>
                    <Button variant='primary' onClick={this.onSubmitTask}>
                        Update
                        </Button>
                </Modal.Footer>
            </Modal>
            {task ? this.taskEditor(task) : null}
        </div>
    }
}
const reporter = 'reporter';
const assignee = 'assignee';
const project = 'project';
const name = 'name';
const priority = 'priority';
const description = 'description';
const status_ = 'status';

const priorities = [
    { color: 'green', name: 'MINOR' },
    { color: 'blue', name: 'MAJOR' },
    { color: 'orange', name: 'CRITICAL' },
    { color: 'red', name: 'BLOCKER' }];

    const statuses = [
        { color: 'secondary', name: 'NOT_STARTED' },
        { color: 'danger', name: 'CANCELED' },
        { color: 'info', name: 'IN_PROGRESS' },
        { color: 'primary', name: 'RESOLVED' },
        { color: 'dark', name: 'CLOSED' }
    ];