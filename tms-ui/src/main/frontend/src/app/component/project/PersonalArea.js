import React from "react";
import * as axios from "axios";
import './index.css';
import 'bootstrap/dist/css/bootstrap.css';
import {Link} from "react-router-dom";

class ProjectsAndTasks extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            projects: [],
            user: [],
            tasks: [],
            userId:'1' //from session soon
        };
    }

    componentDidMount() {
        axios.get(`http://localhost:8090/users/`)
            .then(res => {
                const users = res.data;
                this.setState({users: users});
                this.setState({user: users[0]});//from session soon
            });
        axios.get(`http://localhost:8090/projects/1`) //from session soon
            .then(res => {
                const projects = res.data;
                this.setState({projects});
                console.log(this.state.projects);
            });
        axios.get(`http://localhost:8090/users/tasks/1`) //from session soon
            .then(res => {
                const tasks = res.data;
                this.setState({tasks: tasks});
                console.log(this.state.tasks);
            });
    };

    render() {
        return (

            <div id="wrapper">


                <div className="d-flex flex-row">

                    <div className="mt-1 py-2  flex-grow-1 ">
                        <h2>
                            Personal Area for {this.state.user.fullName}
                        </h2>
                    </div>

                    <div className="d-flex mr-4 justify-content-end align-self-end mt-2">
                        <button type="button" className="btn btn-outline-danger  btn-sm">
                            <Link to="/personalarea">
                                LogOut
                            </Link>
                        </button>
                    </div>
                </div>
                <hr/>

                <div className="d-flex flex-row  justify-content-start">
                    <div className="d-inline-flex">
                        <Link to="/personalarea">
                            <button type="button" className="btn btn-outline-dark mx-2  btn-sm">
                                Your projects and tasks
                            </button>
                        </Link>
                    </div>
                    <hr/>
                    <div className=" flex-grow-1 ">
                        <Link to={`createdprojects/${this.state.userId}`}>
                            <button type="button" className="btn btn-outline-dark mx-2  btn-sm">
                                Created projects
                            </button>
                        </Link>
                    </div>
                </div>


                <div className="table-responsive">
                    <table className="table table-light  table-bordered table-hover table-sm  ">
                        <thead className="thead-dark">
                        <tr>
                            <th style={{"width": "90%"}} scope="col">Name</th>
                            <th style={{"width": "10%"}} scope="col">Creator ID</th>
                        </tr>
                        </thead>
                        <tbody>

                        {this.state.projects.map(project =>
                            <React.Fragment>
                                <tr key={project.id} className="table-active">
                                    <td><Link to={`/projectstasks/${project.id}`}>{project.name}</Link></td>
                                    <td> {project.creator_id}</td>
                                </tr>

                                <table className="mx-lg-4 container-fluid">
                                    <tr>
                                        <th> Task name</th>
                                        <th> Description</th>
                                        <th> Deadline</th>
                                        <th> CreationDate</th>
                                        <th> ReportedId</th>
                                        <th> StatusId</th>
                                        <th> ModificationDate</th>
                                        <th> ProjectId</th>
                                        <th> PriorityId</th>
                                    </tr>

                                    {this.state.tasks.map(task => {

                                            if (task.projectId === project.id) {
                                                return (
                                                    <tr key={task.id}>
                                                        <td><Link to="/personalarea">{task.name}</Link></td>
                                                        <td>{task.description}</td>
                                                        <td>{new Intl.DateTimeFormat('en-GB', {
                                                            year: 'numeric',
                                                            month: 'long',
                                                            day: '2-digit'
                                                        }).format(new Date(task.deadline))}</td>
                                                        <td>{new Intl.DateTimeFormat('en-GB', {
                                                            year: 'numeric',
                                                            month: 'long',
                                                            day: '2-digit'
                                                        }).format(new Date(task.creationDate))}</td>
                                                        <td>{task.reportedId}</td>
                                                        <td>{task.statusId}</td>
                                                        <td>{new Intl.DateTimeFormat('en-GB', {
                                                            year: 'numeric',
                                                            month: 'long',
                                                            day: '2-digit'
                                                        }).format(new Date(task.modificationDate))}</td>
                                                        <td>{task.projectId}</td>
                                                        <td>{task.priorityId}</td>

                                                    </tr>
                                                )
                                            }

                                            else
                                                return null

                                        }
                                    )}
                                </table>
                            </React.Fragment>
                        )
                        }
                        </tbody>
                    </table>
                </div>
            </div>
        );
    };
}

export default ProjectsAndTasks;