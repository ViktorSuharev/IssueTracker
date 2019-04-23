import React from 'react';
import * as axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css';
import { Container, Table, Button, Modal } from 'react-bootstrap';
import '../styles.css';
import TextEditor from '../TextEditor';
import { authorizationHeader } from '../../actions';
import { backurl } from '../../properties';
import TaskBoard from '../task/TaskBoard';
import { Link } from 'react-router-dom';

export default class ProjectView extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            id: this.props.match.params.id,
            project: { name: '' },
            team: [],
            tasks: [],
            show: false
        };

        this.deleteProject = this.deleteProject.bind(this);
    }

    deleteProject() {

        axios.delete(backurl + '/projects/' + this.state.id, authorizationHeader())
            .then(response => {
                alert(this.state.name + ' deleted');
                this.props.history.goBack();
            })
            .catch(error => {
                alert(error.response.status);
            })

        this.handleClose();
    }

    componentDidMount() {
        var header = authorizationHeader();

        axios.get(backurl + '/projects/' + this.state.id, header)
            .then(res => {
                const project = res.data;
                this.setState({ project: project });
                axios.get(backurl + '/projects/team/' + this.state.id, header)
                    .then(res => {
                        const team = res.data;
                        this.setState({ team: team });
                    })

                axios.get(backurl + '/tasks/active/project/' + this.state.id, header)
                    .then(res => {
                        const tasks = res.data;
                        this.setState({ tasks: tasks });
                    })
                
            })
    };

    render() {
        var project = this.state.project;

        return <div>
            <Modal show={this.state.show}>
                <Modal.Header >
                    <Modal.Title>Delete project</Modal.Title>
                </Modal.Header>
                <Modal.Body>Are you sure you want to delete project '{this.state.project.name}'?</Modal.Body>
                <Modal.Footer>
                    <Button variant='secondary' onClick={() => this.setState({ show: false })}>
                        Cancel
                    </Button>
                    <Button variant='danger' onClick={this.deleteProject}>
                        Delete
                    </Button>
                </Modal.Footer>
            </Modal>
            <Container>
                <div className='float-right'>
                    <Button variant='success'><Link className='link' to={'/projects/edit/' + this.state.id}>&nbsp; Edit &nbsp;</Link></Button>&nbsp;&nbsp;
                <Button variant='danger' onClick={() => this.setState({show: true})}>&nbsp; Delete &nbsp;</Button>
                </div>
                <h1 style={{ wordBreak: 'break-all' }}>{project.name}</h1>
                <hr />
                {project.description ? <TextEditor
                    readOnly={true}
                    value={project.description} /> : null
                }
                <hr />
                <h3>Team</h3>
                <br />

                <Table striped bordered hover>
                    <thead className='thead-dark'>
                        <tr>
                            <th>name</th>
                            <th>email</th>
                            <th>role</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.team.map(({ user, role }) =>
                            <tr key={user.email}>
                                <td> <Link className='black-link' to={'/users/' + user.id}>{user.name}</Link></td>
                                <td> <a href={'mailto:' + user.email}>{user.email}</a></td>
                                <td> {role.name}</td>
                            </tr>)}
                    </tbody>
                </Table>
                <hr />
                {this.state.tasks.length ? <div>
                    <h3>Tasks To Do</h3>
                    <br />
                    <TaskBoard tasks={this.state.tasks} />
                </div> : <div>
                    No tasks <Link to='/tasks/new'>yet</Link>
                </div>}

            </Container>
        </div >
    }
}