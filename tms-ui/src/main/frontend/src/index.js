import * as React from "react";
import ReactDOM from "react-dom";
import "./app/component/header/project/index.css";
import "github-fork-ribbon-css/gh-fork-ribbon.css";
import 'bootstrap/dist/css/bootstrap.min.css';

import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';


import 'bootstrap/dist/js/bootstrap.bundle.min';
import ProjectsTasks from "./app/component/header/project/ProjectsTasks";
import ProjectInfo from "./app/component/header/project/ProjectInfo";
import AddTask from "./app/component/header/project/AddTask";
import ProjectsSettings from "./app/component/header/project/ProjectsSettings";
import CreateProject from "./app/component/header/project/CreateProject";
import Cancel from "./app/component/header/project/Cancel";
import PersonalArea from "./app/component/header/project/PersonalArea";
import CreatedProjects from "./app/component/header/project/CreatedProjects";

ReactDOM.render((
        <Router>
            <div>
                <Route path="/cancel" component={Cancel}/>
                <Route path="/createproject" component={CreateProject}/>
                <Route path="/projectstasks/:id" component={ProjectsTasks}/>
                <Route path="/projectssettings/:id" component={ProjectsSettings}/>
                <Route path="/projectinfo/:id" component={ProjectInfo}/>
                <Route path="/addTask" component={AddTask}/>
                <Route path="/personalarea" component={PersonalArea}/>
                <Route path="/createdprojects/:id" component={CreatedProjects}/>
            </div>
        </Router>),
    document.getElementById("root")
);
