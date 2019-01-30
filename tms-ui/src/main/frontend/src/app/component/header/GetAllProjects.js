import React from 'react';
import * as axios from 'axios';


 class GetAllProjects extends React.Component {
    state = {
        projects: []
    }

    componentDidMount() {
        axios.get(`http://localhost:8090/employees`)
            .then(res => {
                const projects = res.data;
                this.setState({projects});
            })
    }

    render() {
        return (
            <div>
                <div className=" d-flex flex-row justify-content-center align-items-center">
                    Projects list
                </div>
                <div className="table-responsive">
                    <table className="table table-light table-striped table-bordered table-hover table-sm  ">
                        <thead className="thead-dark">
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Creator ID</th>
                            <th scope="col">Name</th>

                        </tr>
                        </thead>
                        <tbody>

                        {this.state.projects.map(project =>


                            <tr>
                                <th scope="row">{project.id}</th>
                                <td> {project.creator_id}</td>
                                <td>{project.name}</td>
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
export default GetAllProjects;