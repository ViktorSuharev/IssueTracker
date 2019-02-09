import React from 'react';
import * as axios from 'axios';
import './index.css';


class Info extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            projects: [],
            project: []
        };

    }


    componentDidMount() {
        axios.get(`http://localhost:8090/users/`)
            .then(res => {
                const users = res.data;
                this.setState({users: users});
            });
        axios.get(`http://localhost:8090/projects/`)
            .then(res => {
                const projects = res.data;
                console.log(projects[0]);
                const project = projects[0];
                this.setState({projects});
                this.setState({project});
            })
    };


    render() {

        return (
            <div id="wrapper">

                <div className=" d-flex flex-row justify-content-center align-items-center">
                  <h1>
                      Team
                  </h1>
                </div>
                <div className="pl-sm-2 mr-4 bd-highlights align-self-end" style={{"fontSize": "30px"}}>
                  Project's creator: {this.state.project.creator_id}
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
        )
    }
}

export default Info;