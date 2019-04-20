import React from 'react';
import { Modal, Button, Container, Form } from 'react-bootstrap';
import axios from 'axios';
import { backurl } from '../../properties';
import { authorizationHeader } from '../../actions';
import TextEditor from '../TextEditor';
import './styles.css';

// import { DateTime } from 'react-datetime-bootstrap';

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
            task: {
                name: null,
                description: null,
                dueDate: null,
                project: null,
                priority: priorities[0].name,
                reporter: null,
                assignee: null
            },

            projects: [],
            users: [],
            focuser: false,
            show: false
            // date: '2019/04/23'
        }

        this.onUpdateTask = this.onUpdateTask.bind(this);
        this.onDescriptionSave = this.onDescriptionSave.bind(this);
        this.onDueDateChanged = this.onDueDateChanged.bind(this);

        this.handleCancel = this.handleCancel.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);

        this.color = this.color.bind(this);

        this.onSubmitTask = this.onSubmitTask.bind(this);
    }

    updateTask(target, value) {
        var task = { ...this.state.task };
        task[target] = value;
        this.setState({ task: task });

        console.log(JSON.stringify(task));
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

        axios.get(backurl + '/users/', reqHeader)
            .then((response) => {
                const users = response.data;
                this.updateTask(assignee, users[0].id);
                this.updateTask(reporter, users[0].id);
                this.setState({ users: users });
            });

        axios.get(backurl + '/projects/', reqHeader)
            .then((response) => {
                const projects = response.data;
                this.updateTask(project, projects[0].id);
                this.setState({ projects: projects });
            });
    }

    onSubmitTask(event) {
        event.preventDefault();

        const task = this.state.task;

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

        let header = authorizationHeader();

        axios(backurl + '/tasks/', {
            method: 'POST',
            headers: header.headers,
            data: {
                name: task.name,
                description: task.description,
                dueDate: task.dueDate,
                projectId: task.project,
                priority: task.priority,
                reporterId: task.reporter,
                assigneeId: task.assignee
            }
        })
            .then(res => {
                console.log(res.status);
                console.log(res.data);
                alert('Success!');
                this.handleClose();
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

    render() {
        return <div>
            <Modal show={this.state.show} onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Save new task</Modal.Title>
                </Modal.Header>
                <Modal.Body>Are you sure you want to save new task?</Modal.Body>
                <Modal.Footer>
                    <Button variant='secondary' onClick={this.handleClose}>
                        Cancel
                        </Button>
                    <Button variant='primary' onClick={this.onSubmitTask}>
                        Save
                        </Button>
                </Modal.Footer>
            </Modal>
            <Container>
                <div className='float-right'>
                    <Button className='d-inline-flex p-2 main-control' variant='secondary' href='/tasks/my'>Cancel</Button>&nbsp;
                    <Button className='d-inline-flex p-2 main-control' variant='success' onClick={this.handleShow}>Save</Button>
                </div>
                <h2>Edit task</h2>
                <hr />

                <Form inline>
                    <Form.Label>Task name: &emsp;</Form.Label>
                    <Form.Control type='text' name={name} onChange={this.onUpdateTask} placeholder='Task name' />
                </Form>
                <br />

                <Form inline>
                    <Form.Label>Assignee:&emsp;&emsp;</Form.Label>
                    <Form.Control as='select' name={assignee} onChange={this.onUpdateTask}>
                        {this.state.users.map((user) => <option value={user.id}>{user.name}</option>)}
                    </Form.Control>
                </Form>
                <br />

                <Form inline>
                    <Form.Label>Reporter:&emsp;&emsp;</Form.Label>
                    <Form.Control as='select' name={reporter} onChange={this.onUpdateTask}>
                        {this.state.users.map((user) => <option value={user.id}>{user.name}</option>)}
                    </Form.Control>
                </Form>
                <br />

                <Form inline>
                    <Form.Label>Project:&emsp;&emsp;&emsp;</Form.Label>
                    <Form.Control as='select' name={project} onChange={this.onUpdateTask}>
                        {this.state.projects.map((project) => <option value={project.id}>{project.name}</option>)}
                    </Form.Control>
                </Form>
                <br />

                <Form inline >
                    <Form.Label>Priority:&emsp;&emsp;&emsp;</Form.Label>
                    <Form.Control as='select' style={{ color: this.color() }} name={priority} onChange={this.onUpdateTask}>
                        {priorities.map((priority) => <option value={priority.name} style={{ color: priority.color }}>{priority.name}</option>)}
                    </Form.Control>
                </Form>
                <br />
                <Form inline>
                    Due date:&emsp;&emsp;
                        {/* <DateTime /> */}
                    {/* <Form.Control> */}
                    <SingleDatePicker
                        //   inputIconPosition='after'
                        small={true}
                        // block={true}
                        numberOfMonths={1}
                        date={this.state.date}
                        onDateChange={date => this.onDueDateChanged(date)}
                        focused={this.state.focused}
                        // focused={true}
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
                    <TextEditor
                        placeholder='Enter description here...'
                        onSave={this.onDescriptionSave}
                        maxLength={300}
                    />
                </Form>
                <br />
            </Container>
        </div>
    }
}
const reporter = 'reporter';
const assignee = 'assignee';
const project = 'project';
const name = 'name';
const priority = 'priority';
const description = 'description';

const priorities = [
    { color: 'green', name: 'MINOR' },
    { color: 'blue', name: 'MAJOR' },
    { color: 'orange', name: 'CRITICAL' },
    { color: 'red', name: 'BLOCKER' }];
