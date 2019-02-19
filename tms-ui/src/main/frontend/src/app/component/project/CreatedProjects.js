import React from "react";
import * as axios from "axios";
import './index.css';
import 'bootstrap/dist/css/bootstrap.css';
import {Link} from "react-router-dom";


class CreatedProjects extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            projects: [],
            user: [],
            userId: this.props.match.params.id,
        };
    };

    componentDidMount() {
        axios.get(`http://localhost:8090/users/`)
            .then(res => {
                const users = res.data;
                this.setState({users: users});
                this.setState({user: users[0]}); //userId from session soon
            });
        axios.get(`http://localhost:8090/users/projects/4`) //userId from session soon
            .then(res => {
                const projects = res.data;
                this.setState({projects});
                console.log(this.state.projects);
            })
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
                        <Link to="/createdprojects">
                            <button type="button" className="btn btn-outline-dark mx-2  btn-sm">
                                Created projects
                            </button>
                        </Link>
                    </div>
                </div>


                <div className="table-responsive">
                    <table className="table table-light table-striped table-bordered table-hover table-sm  ">
                        <thead className="thead-dark">
                        <tr>
                            <th style={{"width": "70%"}} scope="col">Name</th>
                        </tr>
                        </thead>
                        <tbody>

                        {this.state.projects.map(project =>
                            <tr key={project.id}>
                                <td> <Link to="/createdprojects">{project.name}</Link></td>
                            </tr>
                        )
                        }
                        </tbody>
                    </table>
                </div>
            </div>
        );
    };
}

export default CreatedProjects;