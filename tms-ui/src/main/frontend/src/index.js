import * as React from "react";
import ReactDOM from "react-dom";
import "./app/component/project/index.css";
import "github-fork-ribbon-css/gh-fork-ribbon.css";
import 'bootstrap/dist/css/bootstrap.min.css';

import {BrowserRouter as Router, Route} from 'react-router-dom';


import 'bootstrap/dist/js/bootstrap.bundle.min';
import ProjectsTasks from "./app/component/project/ProjectsTasks";
import ProjectInfo from "./app/component/project/ProjectInfo";
import AddTask from "./app/component/project/AddTask";
import ProjectsSettings from "./app/component/project/ProjectsSettings";
import CreateProject from "./app/component/project/CreateProject";
import Cancel from "./app/component/project/Cancel";
import PersonalArea from "./app/component/project/PersonalArea";
import CreatedProjects from "./app/component/project/CreatedProjects";
import Register from "./app/component/login/Register";
import Login from "./app/component/login/Login";
import HelloWorld from "./app/component/HelloWorld";


ReactDOM.render((
        <Router>
            <div>
                <Route path="/hello" component={HelloWorld}/>
                <Route path="/login" component={Login}/>
                <Route path="/register" component={Register}/>
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
