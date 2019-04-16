import React from 'react';
import * as axios from 'axios';
import './index.css';
import { Container } from 'react-bootstrap';


class ProjectInfo extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            projects: [],
            project: [],
            projectId: this.props.match.params.id,
            creatorId: '',
            creator: []
        };

    }


    componentDidMount() {

        axios.get(`http://localhost:8090/api/users/`)
            .then(res => {
                const users = res.data;
                this.setState({ users: users });
            });
        axios.get(`http://localhost:8090/api/projects/`)
            .then(res => {
                const projects = res.data;
                console.log("Current project: ", projects[this.state.projectId]);
                const project = projects[this.state.projectId];

                this.setState({ projects });
                this.setState({ project });

                this.setState({ creatorId: project.creatorId });
                console.log("creatorId: ", this.state.creatorId);
            })
            .then(res => {
                const url = `http://localhost:8090/users/${this.state.creatorId}`;
                axios.get(url)
                    .then(res => {
                        console.log("url: ", url);
                        const creator = res.data;
                        this.setState({ creator });
                        console.log("creator's full name: ", this.state.creator.fullName);
                    });
            });

    };


    render() {

        return (
            <Container>
                <div id="wrapper">

                    <div className=" d-flex flex-row justify-content-center align-items-center">
                        <h1>
                            Team
                    </h1>
                    </div>
                    <div className="pl-sm-2 mr-4 bd-highlights align-self-end" style={{ "fontSize": "30px" }}>
                        Project's creator: {this.state.creator.fullName}
                    </div>

                    <div className="table-responsive">
                        <table className="table table-light table-striped table-bordered table-hover table-sm  ">
                            <thead className="thead-dark">
                                <tr>
                                    <th scope="col">Full Name</th>
                                    <th scope="col">EMail</th>
                                </tr>
                            </thead>
                            <tbody>

                                {this.state.users.map(user =>
                                    <tr key={user.id}>
                                        <td> {user.fullName}</td>
                                        <td> {user.email}</td>
                                    </tr>
                                )
                                }
                            </tbody>
                        </table>
                    </div>
                </div>
            </Container>
        )
    }
}

export default ProjectInfo;