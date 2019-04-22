import '../index.css';
import 'github-fork-ribbon-css/gh-fork-ribbon.css';
import 'bootstrap/dist/css/bootstrap.min.css';

import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';

import 'bootstrap/dist/js/bootstrap.bundle.min';
import ProjectInfo from './component/project/ProjectInfo';
import ProjectsSettings from './component/project/ProjectsSettings';
import CreateProject from './component/project/CreateProject';
import HelloWorld from './component/HelloWorld';
import AboutUser from './component/user/AboutUser';

import React, { Component } from 'react'
import { Container } from 'react-bootstrap';

import NavigationBar from './component/navigation/NavigationBar.js';
import { AuthProvider, AuthConsumer } from './component/login/AuthContext';
import SignTabs from './component/login/SignTabs';
import ProtectedRoute from './component/ProtectedRoute';
import CreateTask from './component/task/CreateTask';
import AllTasks from './component/task/AllTasks';
import MyTasks from './component/task/MyTasks';
import ProjectView from './component/project/ProjectView';
import TaskView from './component/task/TaskView';
import NotFound from './component/NotFound';
import TaskEditor from './component/task/TaskEditor';
import ProjectEditor from './component/project/ProjectEditor';
import AllProjects from './component/project/AllProjects';

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
                        {/* <ProtectedRoute path='/projects/tasks/:id' component={ProjectsTasks} /> */}
                        <ProtectedRoute exact path='/projects/edit/:id' component={ProjectEditor} />
                        <ProtectedRoute exact path='/projects/info/:id' component={ProjectInfo} />
                        <ProtectedRoute exact path='/projects/:id' component={ProjectView} />
                        <ProtectedRoute exact path='/projects' component={AllProjects} />
                        
                        <ProtectedRoute exact path='/tasks' component={AllTasks} />
                        <ProtectedRoute exact path='/tasks/new' component={CreateTask} />
                        <ProtectedRoute exact path='/tasks/edit/:id' component={TaskEditor} />
                        <ProtectedRoute exact path='/tasks/my' component={MyTasks} />
                        <ProtectedRoute exact path='/tasks/:id' component={TaskView} />

                        <Route path='/auth' component={SignTabs}/>
                        <Route path="*" component={NotFound} />

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