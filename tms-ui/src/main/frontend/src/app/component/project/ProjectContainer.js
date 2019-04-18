import React, { Component } from 'react';
import { Modal, Container, Button, Card, CardDeck } from 'react-bootstrap'
// import TextEditor from '../TextEditor';
import { AuthConsumer } from '../login/AuthContext';
import axios from 'axios';
import { backurl } from '../../properties';
import { authorizationHeader } from '../../actions';

export default class ProjectContainer extends Component {
    constructor(props) {
        super(props);

        this.state = {
            deleteProject: -1,
            show: false
        }

        this.onDelete = this.onDelete.bind(this);
        this.deleteProject = this.deleteProject.bind(this);

        this.handleCancel = this.handleCancel.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
    }

    handleCancel(event) {
        event.preventDefault();
    }

    handleClose() {
        this.setState({ show: false });
    }

    handleShow(event) {
        event.preventDefault();

        this.setState({ show: true });
    }

    makeControlLinks(project) {
        let edit = 'projects/edit/' + project.id;

        return <div>
            <Button size='sm' variant='outline-success' value={project.id} href={edit}>&nbsp; Edit &nbsp;</Button>
            &nbsp; &nbsp;
            <Button size='sm' variant='outline-danger' value={project.id} onClick={this.onDelete}>Delete</Button>
        </div>
    }

    controlIfCreator(project) {
        return <AuthConsumer>
            {({ user }) => user.id === project.creatorId ? this.makeControlLinks(project) : <br />}
        </AuthConsumer>
    }

    onDelete(event) {
        const projectId = event.target.value;

        const project = this.props.projects
            .map(({ project, owner }) => project)
            .find((p) => p.id == projectId);

        this.setState({ deleteProject: project });
        this.handleShow(event);
    }

    deleteProject(event) {
        event.preventDefault();

        const project = this.state.deleteProject;
        axios.delete(backurl + '/projects/' + project.id, authorizationHeader())
            .then(response => {
                alert(project.name + ' deleted');
                window.location.reload(false);
            })
            .catch(error => {
                alert(error.response.status);
            })

        this.handleClose();
    }

    processProjectCard(project) {
        if (project === stub)
            return <Card>
                <Card.Header><h4>Create new</h4></Card.Header>
                <Card.Body align='center'>
                    <Button className='rounded' variant='outline-secondary' href='/projects/new'>
                        <h1><br /> &nbsp; &nbsp; &nbsp; +  &nbsp; &nbsp; &nbsp;</h1><br /><br />
                    </Button>
                </Card.Body>
            </Card>

        return <Card>
            <Card.Header>
                <Card.Link href={'/projects/' + project.id}>
                    <h4>{project.name}</h4>
                </Card.Link>
            </Card.Header>
            <Card.Body>
                <Card.Subtitle className='mb-2 text-muted'>{project.owner.name}</Card.Subtitle>
                {this.controlIfCreator(project)}
                {/* <TextEditor value={project.description} readOnly={true} /> */}
                <br />
                <Card.Text>
                    {parseMdToText(project.description)}
                </Card.Text>
            </Card.Body>
        </Card>
    }

    render() {
        const colNum = 2;
        const rawProjects = this.props.projects.map(({ project, owner }) => {
            project.owner = owner;
            return project;
        });

        while (rawProjects.length % colNum)
            rawProjects.push(stub);

        var matrix = reshape(rawProjects, colNum);

        return <div>
            <Modal show={this.state.show} onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Delete project</Modal.Title>
                </Modal.Header>
                <Modal.Body>Are you sure you want to delete project '{this.state.deleteProject.name}'?</Modal.Body>
                <Modal.Footer>
                    <Button variant='secondary' onClick={this.handleClose}>
                        Cancel
                        </Button>
                    <Button variant='danger' onClick={this.deleteProject}>
                        Delete
                        </Button>
                </Modal.Footer>
            </Modal>
            <Container>
                {matrix.map((row) => <div><CardDeck>
                    {row.map(
                        (project) => this.processProjectCard(project)
                    )}
                </CardDeck>
                    <br />
                </div>
                )}
            </Container>
        </div>
    }
}

const removeMd = require('remove-markdown');
const DESCRIPTION_LENGTH = 140;
function parseMdToText(markdown) {
    let text = removeMd(markdown, {
        stripListLeaders: true, // strip list leaders (default: true)
        listUnicodeChar: '',     // char to insert instead of stripped list leaders (default: '')
        gfm: true,                // support GitHub-Flavored Markdown (default: true)
        useImgAltText: true      // replace images with alt-text, if present (default: true)
    });

    return text.length > DESCRIPTION_LENGTH ? text.substring(0, DESCRIPTION_LENGTH - 3) + '...' : text;
}

function reshape(list, elementsPerSubArray) {
    var matrix = [], i, k;
    for (i = 0, k = -1; i < list.length; i++) {
        if (i % elementsPerSubArray === 0) {
            k++;
            matrix[k] = [];
        }
        matrix[k].push(list[i]);
    }
    return matrix;
}

let stub = { name: '', description: '', owner: { name: null } }