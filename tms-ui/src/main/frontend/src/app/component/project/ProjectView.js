import React from 'react';
import * as axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css';
import { Container, Table, Button } from 'react-bootstrap';
import '../styles.css';
import TextEditor from '../TextEditor';
import { authorizationHeader, wordWrap } from '../../actions';
import { backurl } from '../../properties';
import TaskBoard from '../task/TaskBoard';

export default class ProjectView extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            id: this.props.match.params.id,
            project: { name: '' },
            team: [],
            tasks: []
        };
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

                axios.get(backurl + '/tasks/project/' + this.state.id, header)
                    .then(res => {
                        const tasks = res.data;
                        this.setState({ tasks: tasks });
                    })
            })
    };

    render() {
        var project = this.state.project;

        return <Container>
            <div className='float-right'>
                <Button variant='success'>&nbsp; Edit &nbsp;</Button>&nbsp;&nbsp;
                <Button variant='danger' onClick={this.handleShow}>&nbsp; Delete &nbsp;</Button>
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
                            <td> {user.name}</td>
                            <td> {user.email}</td>
                            <td> {role.name}</td>
                        </tr>)}
                </tbody>
            </Table>
            <hr />
            {this.state.tasks.length ? <div>
                <h3>Tasks</h3>
                <br />
                <TaskBoard tasks={this.state.tasks} />
            </div> : null}

        </Container>
    }
}