import React from "react";
import * as axios from "axios";
import 'bootstrap/dist/css/bootstrap.css';
import {Link} from "react-router-dom";

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
        this.onSubmitName = this.onSubmitName.bind(this);
        this.onSelectChange = this.onSelectChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.onSubmitProject = this.onSubmitProject.bind(this);
    }

    onSubmitName(event) {
        console.log("onSubmitName: ", this.state.name);
        if (!this.state.name) {
            alert("on Submit: Вы не ввели имя проекта!");
        } else {
            alert(`New project's name was updated with name: "${this.state.name}"`);
        }
        event.preventDefault();
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

        if(!this.state.name){
            alert("Create project: Вы не ввели имя проекта!");
        }else{
            axios(`http://localhost:8090/projects/`, {
                method: 'POST',
                headers: {
                    'content-type': 'application/json',
                },
                data: newProject,
            })
                .then(res => {
                    console.log(res.status);
                    console.log(res.data);
                    alert(`Новый проект с именем: "${this.state.name}" и id создателя: "4" создан!`);
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
                user: user
            }, function () {
                console.log("this.state.user:", JSON.stringify(this.state.user));
            })
        }
        if (sw == "role") {

            const newRole = event.target.value;
            console.log("newRole:", newRole);
            {
                this.state.user.role = newRole
            }

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

    onSubmit(event) {
        this.setState({addedUsers: [...this.state.addedUsers, this.state.user]});
        alert(`Вы выбрали сотрудника: ${JSON.stringify(this.state.user)} и его роль в проекте: ${this.state.user.role}`);
        console.log("OnSubmit:", JSON.stringify(this.state.user));
        event.preventDefault();
    };


    render() {
        const options = this.state.users.map(user =>
            <option value={user.id || ''} key={user.id || ''}>
                {JSON.stringify(user)}
            </option>);
        return (
            <div>
                <div className="d-flex flex-row">
                    <div className="mt-1 py-2  flex-grow-1  badge badge-primary text-wrap ">
                        New Project's settings
                    </div>
                    <div className="d-flex mr-4 justify-content-end align-self-end mt-2" style={{"fontSize": "30px"}}>
                        <button type="button" className="btn btn-outline-danger  btn-sm"
                                style={{"fontSize": "30px"}}>
                            <Link to="/cancel">
                                Cancel
                            </Link>
                        </button>
                    </div>

                    <div className="d-flex mr-4 justify-content-end align-self-end mt-2" style={{"fontSize": "30px"}}>
                        <form onSubmit={this.onSubmitProject}>
                            <button type="submit" className="btn btn-outline-success   btn-sm">
                                Create project
                            </button>
                        </form>
                    </div>
                </div>
                <hr/>

                <form onSubmit={this.onSubmitName}>
                    <div className="d-flex flex-row">
                        <div className="d-flex mr-4 justify-content-end align-self-end mt-2">
                            <label> Название: <input type="text" value={this.state.name}
                                                     onChange={this.onNameChange}/>
                            </label>
                        </div>
                        <div className="d-flex mr-4  mt-2">
                            <input type="submit" value="Submit"/>
                        </div>
                    </div>
                </form>


                <form onSubmit={this.onSubmit}>
                    <label> Выберете сотрудника в команду
                        <select defaultValue={""} name="userName" className="form-control"
                                onChange={this.onSelectChange}>
                            <option value="" disabled={true}>
                                Select user
                            </option>
                            {options}
                            )}
                        </select>
                    </label>
                    <label> Выберете должность
                        <select defaultValue={""} name="role" className="form-control"
                                onChange={this.onSelectChange}>
                            <option value="" disabled={true}>
                                Select role
                            </option>
                            <option value="1">1) Проект манеджер</option>
                            <option value="2">2) Разработчик</option>
                            <option value="3">3) Тестировщик</option>
                        </select>
                    </label>
                    <input type="submit" value="Submit"/>
                </form>

                <div className=" d-flex flex-row justify-content-center align-items-center">
                    Added Users
                </div>
                <div className="table-responsive">
                    <table className="table table-light table-striped table-bordered table-hover table-sm  ">
                        <thead className="thead-dark">
                        <tr>
                            <th scope="col">Delete</th>
                            <th scope="col">ID</th>
                            <th scope="col">Full Name</th>
                            <th scope="col">Password</th>
                            <th scope="col">EMail</th>
                            <th scope="col">Role</th>
                        </tr>
                        </thead>
                        <tbody>

                        {this.state.addedUsers.map(user =>
                            <tr key={user.id + user.role}>
                                <th scope="row">
                                    <button onClick={this.onDeleteClick.bind(this, user)}>
                                        Delete
                                    </button>
                                </th>
                                <th>{user.id}</th>
                                <td> {user.fullName}</td>
                                <td>{user.password}</td>
                                <td> {user.email}</td>
                                <td> {user.role}</td>
                            </tr>
                        )
                        }
                        </tbody>
                    </table>
                </div>


            </div>
        );
    }
}

export default CreateProject;