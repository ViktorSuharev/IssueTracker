import React, { Component } from 'react';
import { Container, Card, CardDeck } from 'react-bootstrap'
// import TextEditor from '../TextEditor';
import { AuthConsumer } from '../login/AuthContext';

export default class ProjectContainer extends Component {
    makeControlLinks(project) {
        let edit = 'projects/edit/' + project.id;
        let remove = 'projects/delete/' + project.id;

        return <div>
            <Card.Link style={{ color: 'green' }} href={edit}>Edit</Card.Link>
            <Card.Link style={{ color: 'red' }} href={remove}>Delete</Card.Link>
        </div>
    }

    controlIfCreator(project) {
        return <AuthConsumer>
            {({ user }) => user.id === project.creatorId ? this.makeControlLinks(project) : <br />}
        </AuthConsumer>
    }

    render() {
        const colNum = 2;
        const rawProjects = this.props.projects.map(({project, owner}) => {
            project.owner = owner;
            return project;
        } );
        console.log('RESULT', JSON.stringify(rawProjects));

        while(rawProjects.length % colNum)
            rawProjects.push({name: '', description: '', owner: {name: null}});

        var matrix = reshape(rawProjects, colNum);

        return <Container>
            {matrix.map((row) => <div><CardDeck>
                {row.map(
                    (project) => <Card>
                        <Card.Body>
                            <Card.Title><a href={'/projects/'+project.id}>{project.name}</a></Card.Title>
                            <Card.Subtitle className='mb-2 text-muted'>{project.owner.name}</Card.Subtitle>
                            {this.controlIfCreator(project)}
                            {/* <TextEditor value={project.description} readOnly={true} /> */}
                            <br />
                            <Card.Text>
                                {parseMdToText(project.description)}
                            </Card.Text>
                        </Card.Body>
                    </Card>
                )}
            </CardDeck>
            <br/>
            </div>
            )}
        </Container>;
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