import './component/project/index.css';
import 'github-fork-ribbon-css/gh-fork-ribbon.css';
import 'bootstrap/dist/css/bootstrap.min.css';

import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';

import 'bootstrap/dist/js/bootstrap.bundle.min';
import ProjectsTasks from './component/project/ProjectsTasks';
import ProjectInfo from './component/project/ProjectInfo';
import AddTask from './component/project/AddTask';
import ProjectsSettings from './component/project/ProjectsSettings';
import CreateProject from './component/project/CreateProject';
import PersonalArea from './component/project/PersonalArea';
import CreatedProjects from './component/project/CreatedProjects';
import HelloWorld from './component/HelloWorld';
import AboutUser from './component/user/AboutUser';

import React, { Component } from 'react'

import NavigationBar from './component/navigation/NavigationBar.js';
import { AuthProvider, AuthConsumer } from './component/login/AuthContext';
import SignTabs from './component/login/SignTabs';
import ProtectedRoute from './component/ProtectedRoute';
import ProjectDashboard from './component/project/ProjectDashboard';
import { Container } from 'react-bootstrap';
import CreateTask from './component/task/CreateTask';
import AllTasks from './component/task/AllTasks';

class App extends Component {
    render() {
        return <Router>
                <AuthProvider>
                    <NavigationBar/>
                    <Switch>
                        <ProtectedRoute path='/hello' component={HelloWorld} />

                        <ProtectedRoute exact path='/' component={Container}/>

                        <ProtectedRoute exact path='/user/:id' component={AboutUser} />

                        <ProtectedRoute path='/projects/new' component={Wrapper}/>
                        <ProtectedRoute path='/projects/tasks/:id' component={ProjectsTasks} />
                        <ProtectedRoute path='/projects/edit/:id' component={ProjectsSettings} />
                        <ProtectedRoute path='/projects/info/:id' component={ProjectInfo} />
                        <ProtectedRoute path='/projects/:id' component={CreatedProjects} />
                        <ProtectedRoute exact path='/projects' component={ProjectDashboard} />
                        
                        <ProtectedRoute path='/tasks/new' component={CreateTask} />
                        <ProtectedRoute path='/tasks' component={AllTasks} />
                        {/* <ProtectedRoute path='/personalarea' component={PersonalArea} /> */}

                        <Route path='/auth' component={SignTabs}/>
                    </Switch>
                </AuthProvider>
        </Router>;
    }
}

const Wrapper = ({ component: Component, ...rest }) => (
    <AuthConsumer>
        {({user}) => <ProtectedRoute path='/projects/new' component={() => <CreateProject creator={user}/>}/>}
    </AuthConsumer>
);


export default App;