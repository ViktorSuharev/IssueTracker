import React from "react";
import * as axios from "axios";
import 'bootstrap/dist/css/bootstrap.css';
import {Link} from "react-router-dom";
import { Tab, Tabs, Container } from 'react-bootstrap'

import './index.css';

class CreateProject extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: '',
            users: [],
            user: [],
            addedUsers: []
        };

        this.onNameChange = this.onNameChange.bind(this);
        this.onSelectChange = this.onSelectChange.bind(this);
        this.onAddUser = this.onAddUser.bind(this);
        this.onSubmitProject = this.onSubmitProject.bind(this);
    }


    onNameChange(event) {
        const newName = event.target.value;

        this.setState({name: newName}, function () {
            console.log("onNameChange: ", newName);
        });
        console.log("this.name: ", this.state.name);

    }

    componentDidMount() {
        axios.get(`http://localhost:8090/users/`)
            .then(res => {
                const users = res.data;
                this.setState({users: users});
            })
    };

    onSubmitProject(event) {
        event.preventDefault();
        console.log("onSubmitProject with creatorId: 34 and name: ", this.state.name);

        const newProject = {
            id: null,      //always
            creator_id: 4, //from Session soon
            name: this.state.name
        };

        if (!this.state.name) {
            alert("Create project: Here are no project's name!");
        } else {
            axios(`http://localhost:8090/projects/`, {
                method: 'POST',
                headers: {
                    'content-type': 'application/json',
                },
                data: {newProject: newProject,
                    addedUsers: this.state.addedUsers}
            })
                .then(res => {
                    console.log(res.status);
                    console.log(res.data);
                    alert(`New project with name: "${this.state.name}" and creator's id: "4" was created!`);
                })
                .catch(function (error) {
                    console.log(error);
                });
        }


    };

    onSelectChange(event) {
        const sw = event.target.name;
        if (sw == "userName") {
            const userId = event.target.value;
            console.log("userID:", userId);

            const user = this.state.users.find(user => {
                return user.id == userId;
            });
            console.log("user founded:", JSON.stringify(user));
            this.setState({
                user: Object.assign({}, user)
            }, function () {
                console.log("this.state.user:", JSON.stringify(this.state.user));
            })
        }
        if (sw == "role") {

            const newRole = event.target.value;
            console.log("newRole:", newRole);
            {
                this.state.user.role = newRole;
            }

        }

    };

    onAddUser(event) {

        if (!this.state.user.fullName) {
            alert(`Employee was not selected!`);

        } else {
            var curUser = new User(this.state.user.id, this.state.user.fullName, this.state.user.email, this.state.user.role);
            console.log("OnSubmit:", JSON.stringify(curUser));
            const len = this.state.addedUsers.length;

            var selectedYet = false;
            var i;
            for (i = 0; i < len; ++i) {
                if (this.state.addedUsers[i].id === curUser.id) {
                    selectedYet = true;
                }
            }
            if (selectedYet === true) {
                alert(`The user is selected yet: ${JSON.stringify(curUser)}`);
            } else {
                alert(`You chose employee: ${JSON.stringify(curUser)} and his role in project: ${curUser.role}`);
                this.setState({addedUsers: [...this.state.addedUsers, curUser]});
            }
            event.preventDefault();
        }
    };

    onDeleteClick = (userToDelete) => {

        const addedUsersNew = this.state.addedUsers.filter((user) => {
            return user.id !== userToDelete.id;
        });
        console.log("addedUsersNew: ", JSON.stringify(addedUsersNew));
        this.setState({addedUsers: addedUsersNew}, function () {
            console.log("delete user with id:", userToDelete.id);
        });

    };


    render() {
        const options = this.state.users.map(user =>
            <option value={user.id || ''} key={user.id || ''}>
                {user.fullName}
            </option>);
        return (
            <Container>
            <div id="wrapper">
                <div className="d-flex flex-row">
                    <div className="mt-5 py-2  flex-grow-1">
                        <h2>
                            New Project's settings
                        </h2>
                    </div>
                    <div className="d-flex mr-4 justify-content-end align-self-end mt-2">
                        <button type="button" className="btn btn-outline-danger  btn-sm">
                            <Link to="/cancel">
                                Cancel
                            </Link>
                        </button>
                    </div>

                    <div className="d-flex mr-4 justify-content-end align-self-end mt-2">
                        <form onSubmit={this.onSubmitProject}>
                            <button type="submit" className="btn btn-outline-success   btn-sm">
                                Create project
                            </button>
                        </form>
                    </div>
                </div>
                <hr/>

                <form>
                    <div className="d-flex flex-row mx-1">
                        <div className=" d-flex mr-4 justify-content-end align-self-end mt-2">
                            <label> Name: <input type="text" value={this.state.name} className="form-control"
                                                 onChange={this.onNameChange}/>
                            </label>
                        </div>
                    </div>
                </form>


                <form onSubmit={this.onAddUser}>
                    <label className="mx-1"> Select employees to team
                        <select defaultValue={""} name="userName" className="form-control"
                                onChange={this.onSelectChange}>
                            <option value="" disabled={true}>
                                Select user
                            </option>
                            {options}
                            )}
                        </select>
                    </label>
                    <label className="mx-2"> Select role in project
                        <select defaultValue={""} name="role" className="form-control"
                                onChange={this.onSelectChange}>
                            <option value="" disabled={true}>
                                Select role
                            </option>
                            <option value="1">Project Manager</option>
                            <option value="2">Developer</option>
                            <option value="3">QA</option>
                        </select>
                    </label>
                    <input className="mx-2 btn btn-dark" type="submit" value="Add"/>
                </form>

                <div className=" d-flex flex-row justify-content-center align-items-center">
                    Added Users
                </div>
                <div className="table-responsive">
                    <table className="table table-light table-striped table-bordered table-hover table-sm  ">
                        <thead className="thead-dark">
                        <tr>
                            <th style={{"width": "7%"}} scope="col">Delete</th>
                            <th style={{"width": "43%"}} scope="col">Full Name</th>
                            <th style={{"width": "43%"}} scope="col">EMail</th>
                            <th style={{"width": "7%"}} scope="col">Role</th>
                        </tr>
                        </thead>
                        <tbody>

                        {this.state.addedUsers.map(user =>
                            <tr key={user.id + user.role}>
                                <th scope="row" className="text-center">
                                    <button onClick={this.onDeleteClick.bind(this, user)}>
                                        Delete
                                    </button>
                                </th>
                                <td> {user.fullName}</td>
                                <td> {user.email}</td>
                                <td> {user.role}</td>
                            </tr>
                        )
                        }
                        </tbody>
                    </table>
                </div>
            </div>
            </Container>
        );
    }
}

export default CreateProject;

class User {
    constructor(id, fullName, email, role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }
}