import * as React from "react";
import ReactDOM from "react-dom";
import "./app/component/header/index.css";
import "github-fork-ribbon-css/gh-fork-ribbon.css";
import 'bootstrap/dist/css/bootstrap.min.css';

import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';


import 'bootstrap/dist/js/bootstrap.bundle.min';
import ProjectsTasks from "./app/component/header/ProjectsTasks";
import ProjectInfo from "./app/component/header/ProjectInfo";
import AddTask from "./app/component/header/AddTask";
import ProjectsSettings from "./app/component/header/ProjectsSettings";
import CreateProject from "./app/component/header/CreateProject";
import Cancel from "./app/component/header/Cancel";
import PersonalArea from "./app/component/header/PersonalArea";
import CreatedProjects from "./app/component/header/CreatedProjects";

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
