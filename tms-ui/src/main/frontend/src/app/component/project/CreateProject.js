import React from 'react';
import * as axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css';
// import { Link } from 'react-router-dom';
import { Container, Modal, Button } from 'react-bootstrap';
import '../styles.css';
import './index.css';
import TextEditor from '../TextEditor';

class CreateProject extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            name: null,
            description: null,
            users: [],
            team: [],
            user: null,
            role: null,
            show: false,

            editor: {
                placeholder: 'Enter description...'
            }
        };

        this.handleDescriptionBoxChange = this.handleDescriptionBoxChange.bind(this);

        this.onNameChange = this.onNameChange.bind(this);
        this.onSelectChange = this.onSelectChange.bind(this);
        this.onAddUser = this.onAddUser.bind(this);
        this.onSubmitProject = this.onSubmitProject.bind(this);

        this.handleCancel = this.handleCancel.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
    }

    handleDescriptionBoxChange(description) {
        this.setState( {description: description });
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


    onNameChange(event) {
        const newName = event.target.value;

        this.setState({ name: newName });
    }

    componentDidMount() {
        let token = localStorage.getItem('token');

        axios.get('http://localhost:8090/api/users/all', {
            headers: {
                Authorization: token
            }
        })
            .then(res => {
                const users = res.data;
                this.setState({ users: users });
            })
    };

    onSubmitProject(event) {
        event.preventDefault();

        const project = {
            name: this.state.name,
            description: this.state.description
        };

        console.log('DESCRIPTION: ', JSON.stringify(project.description));

        if (!this.state.name) {
            alert('Create project: name your project!');
            this.handleClose();
            return;
        }
        let token = localStorage.getItem('token');

        axios('http://localhost:8090/api/projects/', {
            method: 'POST',
            headers: {
                Authorization: token,
                'content-type': 'application/json',
                },
            data: {
                project: project,
                team: this.state.team
            }
        })
            .then(res => {
                console.log(res.status);
                console.log(res.data);
                alert('Success!');
            })
            .catch(error => {
                console.log(error.response);
                alert(JSON.stringify(error.response.status))
            });

        this.handleClose();
    }

    onSelectChange(event) {
        const eventName = event.target.name;

        switch(eventName) {
            case 'userName':
                var email = event.target.value;
                var user = this.state.users.find(user => user.email === email);

                console.log('Found user:', JSON.stringify(user));

                this.setState({ user: user },  () => console.log('this.state.user:', JSON.stringify(this.state.user)) )
                break;

            case 'role':
                const role = event.target.value;
                this.setState({ role: role })
                break;
        }
    }

    onAddUser(event) {
        event.preventDefault();

        if (!this.state.user || !this.state.role) {
            alert('Employee was not selected!');
            return;
        }
        
        var curUser = new User(this.state.user.fullName, this.state.user.email, this.state.role);
        console.log('OnSubmit:', JSON.stringify(curUser));
            
        var alreadySelected = this.state.team.some(e => e.email === curUser.email);

        alreadySelected ? alert(JSON.stringify(curUser.fullName) + ' has been already added') 
                        : this.setState({ team: [...this.state.team, curUser] });
    }

    onDeleteClick = (userToDelete) => {
        const teamNew = this.state.team.filter(u => u.email !== userToDelete.email );
        
        console.log('Team: ', JSON.stringify(teamNew));
        
        this.setState({ team: teamNew }, () =>
            console.log('delete user: ', userToDelete.email));
    };


    render() {
        const options = this.state.users.map(user =>
            <option value={user.email || ''} key={user.email || ''}>
                {user.fullName}
            </option>);
        return (
            <div>
                {/* SAVE PROJECT DIALOG */}
                <Modal show={this.state.show} onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>Save new project</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>Are you sure you want to save new project?</Modal.Body>
                    <Modal.Footer>
                        <Button variant='secondary' onClick={this.handleClose}>
                            Cancel
                        </Button>
                        <Button variant='primary' onClick={this.onSubmitProject}>
                            Save
                        </Button>
                    </Modal.Footer>
                </Modal>

                <Container>
                    <div id='wrapper'>

                        <div className='d-flex flex-row'>
                            <div className='mt-5 py-2  flex-grow-1'>
                                <br/>
                                <h2>Create project</h2>
                            </div>
                            <Button className='d-flex mr-4 justify-content-end align-self-end mt-2' variant='danger' onClick={this.handleCancel}>
                                Cancel
                            </Button>
                            <Button 
                                className='d-flex mr-4 justify-content-end align-self-end mt-2'
                                variant='success' 
                                onClick={this.handleShow}>
                                    Create project
                            </Button>

                        </div>
                        <hr />

                        <form>
                            <div className='d-flex flex-row mx-1'>
                                <div className=' d-flex mr-4 justify-content-end align-self-end mt-2'>
                                    <h4> Name: <input type='text' value={this.state.name} className='form-control'
                                        onChange={this.onNameChange} />
                                    </h4>
                                </div>
                            </div>
                        </form>


                        <br/>
                        <h4>Description:</h4>
                        <TextEditor 
                            placeholder={this.state.editor.placeholder}
                            onSave={this.handleDescriptionBoxChange}
                        />
                        <br/>

                        <br/>                        
                        <div className=' d-flex flex-row align-items-center'>
                            <h4>Project team:</h4>
                        </div>
                        <br/>
                        <form onSubmit={this.onAddUser}>
                            <h6>Add memebers</h6>
                            <label className='mx-1'>
                                <select defaultValue={''} name='userName' className='form-control'
                                    onChange={this.onSelectChange}>
                                    <option value='' disabled={true}>
                                        Select user
                                    </option>
                                    {options}
                                    )}
                                </select>
                            </label>
                            <label className='mx-2'>
                                <select defaultValue={''} name='role' className='form-control'
                                    onChange={this.onSelectChange}>
                                    <option value='' disabled={true}>
                                        Select role
                                    </option>
                                    <option value='Project Manager'>Project Manager</option>
                                    <option value='Developer'>Developer</option>
                                    <option value='QA'>QA</option>
                                </select>
                            </label>
                            <input className='mx-2 btn btn-dark' type='submit' value='Add' />
                        </form>

                        <div className='table-responsive'>
                            <table className='table table-light table-striped table-bordered table-hover table-sm  '>
                                <thead className='thead-dark'>
                                    <tr>
                                        <th style={{ 'width': '20%' }} scope='col'>Full Name</th>
                                        <th style={{ 'width': '10%' }} scope='col'>Email</th>
                                        <th style={{ 'width': '10%' }} scope='col'>Role</th>
                                        <th style={{ 'width': '4%' }} scope='col'>Delete</th>
                                    </tr>
                                </thead>
                                <tbody>

                                    {this.state.team.map(user =>
                                        <tr key={user.email + user.role}>
                                            <td> {user.fullName}</td>
                                            <td> {user.email}</td>
                                            <td> {user.role}</td>
                                            <th scope='row'>
                                                <Button variant='danger' onClick={this.onDeleteClick.bind(this, user)}>
                                                    X
                                                </Button>
                                            </th>
                                        </tr>
                                    )
                                    }
                                </tbody>
                            </table>
                        </div>
                    </div>
                </Container >
            </div>
        );
    }
}

export default CreateProject;

class User {
    constructor(fullName, email, role) {
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }
}