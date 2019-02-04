import React from "react";
import ReactDOM from "react-dom";
import App from "./app/component/App";
import "./index.css";
import "github-fork-ribbon-css/gh-fork-ribbon.css";
import 'bootstrap/dist/css/bootstrap.min.css';

import {BrowserRouter as Router, Route} from 'react-router-dom';

import 'bootstrap/dist/js/bootstrap.bundle.min';
import Team from "./app/component/header/Team";
import AddTask from "./app/component/header/AddTask";
import ProjectsSettings from "./app/component/header/ProjectsSettings";
import CreateProject from "./app/component/header/CreateProject";
import Cancel from "./app/component/header/Cancel";

ReactDOM.render((<Router>
    <div>
                     <Route path="/cancel" component={Cancel}/>
                     <Route path="/createproject" component={CreateProject}/>
                     <Route path="/project" component={App}/>
                     <Route path="/projectssettings" component={ProjectsSettings}/>
                     <Route path="/team" component={Team}/>
                     <Route path="/addTask" component={AddTask}/>
    </div>
                 </Router>),
    document.getElementById("root"));
