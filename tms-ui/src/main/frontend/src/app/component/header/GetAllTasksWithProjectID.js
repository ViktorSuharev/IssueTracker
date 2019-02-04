import React from 'react';
import * as axios from 'axios';
import './tableWithScroll.css';
import {Link} from 'react-router-dom';


class GetAllTasksWithProjectID extends React.Component {
    state = {
        tasks: []
    }

    componentDidMount() {
        axios.get(`http://localhost:8090/tasks/`)
            .then(res => {
                const tasks = res.data;
                this.setState({tasks});
            })
    }

    render() {
        return (
            <div>

                <div className="mx-2">
                    <form>
                        <button type="submit"
                                className="btn btn-outline-warning  btn-sm">
                            <Link to="/AddTask">  Создать таск</Link>
                        </button>
                    </form>
                </div>


                <div className=" d-flex flex-row justify-content-center align-items-center">
                    Tasks list
                </div>

                <div className="table-responsive">
                    <table className="table table-light table-striped table-bordered table-hover table-sm  ">
                        <thead className="thead-dark">
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Name</th>
                            <th scope="col">Description</th>
                            <th scope="col">DeadLine</th>
                            <th scope="col">Creation date</th>
                            <th scope="col">Reported</th>
                            <th scope="col">Assignee</th>
                            <th scope="col">Status ID</th>
                            <th scope="col">Modification date</th>
                            <th scope="col">Project ID</th>
                            <th scope="col">Priority ID</th>

                        </tr>
                        </thead>
                        <tbody>

                        {this.state.tasks.map(task =>
                            <tr key={task.id}>
                                <th scope="row">{task.id}</th>
                                <td> {task.name}</td>
                                <td>{task.description}</td>
                                <td> {task.deadline}</td>
                                <td>{task.creationDate}</td>
                                <td>{task.reportedId}</td>
                                <td> {task.assigneeId}</td>
                                <td>{task.statusId}</td>
                                <td>{task.modificationDate}</td>
                                <td> {task.projectId}</td>
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