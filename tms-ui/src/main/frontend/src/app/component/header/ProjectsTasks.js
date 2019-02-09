import React from "react";

import 'bootstrap/dist/css/bootstrap.css';
import ProjectHeader from "./ProjectHeader";
import GetAllTasksWithProjectID from "./GetAllTasksWithProjectID";
import * as axios from "axios";
import './index.css';



class ProjectsTasks extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            total: null,
            next: null,
            operation: null,
            projects: [],
            project: []
        };
    }


    componentDidMount() {
        axios.get(`http://localhost:8090/projects/`)
            .then(res => {
                const projects = res.data;
                console.log(projects[0]);
                const project = projects[0];
                this.setState({projects});
                this.setState({project});
            })
    }

    render() {
        return (
            <div id="wrapper">

                <ProjectHeader project_name={this.state.project.name}
                               creator_id={this.state.project.creator_id}/>
                <hr/>
                <GetAllTasksWithProjectID/>
            </div>
        );
    }
}

export default ProjectsTasks;
