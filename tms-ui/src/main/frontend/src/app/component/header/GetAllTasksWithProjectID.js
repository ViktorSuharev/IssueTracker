import *  as React from 'react';
import * as axios from 'axios';
import {Link} from 'react-router-dom';


class GetAllTasksWithProjectID extends React.Component {
    state = {
        tasks: []
    };

    componentDidMount() {
        axios.get(`http://localhost:8090/tasks/`)
            .then(res => {
                const tasks = res.data;
                this.setState({tasks});
            })
    };

    render() {
        return (
            <div>

                <div className="mx-2">
                    <form>
                        <button type="submit"
                                className="btn btn-outline-warning  btn-sm my-2">
                            <Link to="/AddTask"> Создать таск</Link>
                        </button>
                    </form>
                </div>


                <div className="table-responsive">
                    <table className="table table-light table-striped table-bordered table-hover table-sm  ">
                        <thead className="thead-dark">
                        <tr>
                            <th scope="col">Name</th>
                            <th scope="col">Description</th>
                            <th scope="col">Due date</th>
                            <th scope="col">Created</th>
                            <th scope="col">Reported</th>
                            <th scope="col">Assignee</th>
                            <th scope="col">Status ID</th>
                            <th scope="col">Last update</th>
                            <th scope="col">Priority ID</th>

                        </tr>
                        </thead>
                        <tbody>

                        {this.state.tasks.map(task =>
                            <tr key={task.id}>
                                <td> {task.name}</td>
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
                                <td> {task.assigneeId}</td>
                                <td>{task.statusId}</td>
                                <td>{new Intl.DateTimeFormat('en-GB', {
                                    year: 'numeric',
                                    month: 'long',
                                    day: '2-digit'
                                }).format(new Date(task.modificationDate))}</td>
                                <td> {task.priorityId}</td>
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

export default GetAllTasksWithProjectID;