import "./component/project/index.css";
import "github-fork-ribbon-css/gh-fork-ribbon.css";
import 'bootstrap/dist/css/bootstrap.min.css';

import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

import 'bootstrap/dist/js/bootstrap.bundle.min';
import ProjectsTasks from "./component/project/ProjectsTasks";
import ProjectInfo from "./component/project/ProjectInfo";
import AddTask from "./component/project/AddTask";
import ProjectsSettings from "./component/project/ProjectsSettings";
import CreateProject from "./component/project/CreateProject";
import PersonalArea from "./component/project/PersonalArea";
import CreatedProjects from "./component/project/CreatedProjects";
import HelloWorld from "./component/HelloWorld";
import AboutUser from "./component/user/AboutUser";

import React, { Component } from 'react'

import NavigationBar from './component/navigation/NavigationBar.js';
import { AuthProvider } from "./component/login/AuthContext";
import SignTabs from "./component/login/SignTabs";
import ProtectedRoute from "./component/ProtectedRoute";

class App extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return <Router>
                <AuthProvider>
                    <NavigationBar/>
                    <Switch>
                        <ProtectedRoute path="/hello" component={HelloWorld} />

                        <ProtectedRoute exact path="/" component={Component}/>

                        <ProtectedRoute exact path="/user/:id" component={AboutUser} />
                        <ProtectedRoute path="/createproject" component={CreateProject} />
                        <Route path="/projectstasks/:id" component={ProjectsTasks} />
                        <Route path="/projectssettings/:id" component={ProjectsSettings} />
                        <Route path="/projectinfo/:id" component={ProjectInfo} />
                        <ProtectedRoute path="/addTask" component={AddTask} />
                        <Route path="/personalarea" component={PersonalArea} />
                        <Route path="/createdprojects/:id" component={CreatedProjects} />
                        <Route path="/auth" component={SignTabs}/>
                    </Switch>
                </AuthProvider>
        </Router>;
    }
}

export default App;