import React from 'react';
import * as axios from 'axios';
//import './index.css';


class TaskInfo extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            tasks: [],
            task: [],
            taskId: []//this.props.match.params.id,
        }

    }


    componentDidMount() {
        const taskId = null;

        // axios.get(`http://localhost:8090/tasks/5`)
        //     .then(res => {
        //         const task = res.data;
        //         this.setState({task: task});
        //         console.log(this.state.task);
        //         console.log(this.state.task.taskName);
        //     });
        axios.get(`http://localhost:8090/tasks/`+ taskId)
            .then(res => {
                const task = res.data;
                console.log("Current task: ", task[this.state.taskId]);
               // const task = tasks[this.state.taskId];

              //  this.setState({tasks});
                this.setState({task});

                // this.setState({creatorId: task.creatorId});
                // console.log("creatorId: ", this.state.creatorId);
            })
            // .then(res => {
            //     const url = `http://localhost:8090/users/${this.state.creatorId}`;
            //     axios.get(url)
            //         .then(res => {
            //             console.log("url: ", url);
            //             const creator = res.data;
            //             this.setState({creator});
            //             console.log("creator's full name: ", this.state.creator.fullName);
            //         });
            // });

    };


    render(){

        return (
            <div>
                <h1>
                    {this.state.task.taskName}
                </h1>
                <form id="text"></form>
                <button value="Edit">Edit</button>
                <button type="submit" value="Submit">Submit</button>
                <div
              //       style="
              //
              // display: flex;
              // justify-content: space-around;
              // align-items: flex-start;
              // font: menu;
              // font-size:medium;"
                >
                    <ul>
                        <h4>Details:</h4>
                        <li>
                            Priority:
                            {this.state.task.priority}
                            {/*<select>*/}
                                {/*<option>Minor</option>*/}
                                {/*<option>Major</option>*/}
                            {/*</select>*/}
                        </li>
                        <li>
                            Status:
                            <select>
                                <option>{this.state.task.status}</option>
                                <option>In progres</option>
                            </select>
                        </li>
                    </ul>
                    <ul>
                        <h4>People:</h4>
                        <li>
                            Assignee:
                            <select>
                                <option>User1</option>
                                <option>User2</option>
                            </select>
                        </li>
                        <li>
                            Reporter:
                            <select>
                                <option>User1</option>
                                <option>User2</option>
                            </select>
                        </li>
                    </ul>
                    <ul>
                        <h4>Dates:</h4>
                        <li>Creation date: {this.state.task.creationDate}</li>
                        <li>Due date: {this.state.task.dueDate}</li>
                        <li>Last modification date: {this.state.task.modificationDate}</li>
                    </ul>
                </div>
                <div>
                    Description:
                    <p>{this.state.task.taskDescription}</p>
                </div>
            </div>

        );
    }
}
export default TaskInfo;