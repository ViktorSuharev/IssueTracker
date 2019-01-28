import React from "react";

import articles from "./header/articles";
import 'bootstrap/dist/css/bootstrap.css';
import ProjectHeader from "./header/ProjectHeader";
import TaskListHeader from "./header/TaskListHeader";


class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            total: null,
            next: null,
            operation: null,
        };
    }

    render() {
        return (
            <div>

                <ProjectHeader articles={articles}/>
                <hr/>

                <TaskListHeader articles={articles}/>

            </div>
        );
    }
}

export default App;
