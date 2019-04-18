import React, { Component } from 'react';
import { Badge, Container, Card, CardDeck, Button } from 'react-bootstrap'
// import TextEditor from '../TextEditor';
import { AuthConsumer } from '../login/AuthContext';

export default class TaskBoard extends Component {
    makeControlLinks(task) {
        if (task === stubTask)
            return;

        let edit = 'tasks/edit/' + task.id;
        let remove = 'tasks/delete/' + task.id;

        return <div>
            <Card.Link style={{ color: 'green' }} href={edit}>Edit</Card.Link>
            <Card.Link style={{ color: 'red' }} href={remove}>Delete</Card.Link>
        </div>
    }

    processTaskElement(task) {
        if (task === stubTask)
            return <Card>
                <Card.Header><h4>Add new</h4></Card.Header>
                <Card.Body align='center'>
                    <Button className='rounded' variant='outline-secondary' href='/tasks/new'>
                        <h1><br/> &nbsp; &nbsp; &nbsp; +  &nbsp; &nbsp; &nbsp;</h1><br/><br/>
                    </Button>
                </Card.Body>
            </Card>

        return <Card>
            <Card.Header>
                <Card.Link style={{ color: 'black' }} href={'/tasks/' + task.id}> <h4>{task.name}</h4> </Card.Link>
            </Card.Header>
            <Card.Body>
                {this.getTaskInfo(task)}
            </Card.Body>
            <Card.Body>
                <Card.Text>
                    Assignee &nbsp; <a href={'/users/' + task.assignee.id}>{task.assignee.fullName}</a>
                    <br />
                    Reporter &nbsp; <a href={'/users/' + task.reporter.id}>{task.reporter.fullName}</a>
                </Card.Text>
            </Card.Body>
            <Card.Body>{this.makeControlLinks(task)}</Card.Body>
        </Card>
    }

    getTaskInfo(task) {
        return <Card.Subtitle className='mb-2 text-muted'>
            <Badge variant={priorities[task.priority].color}>{task.priority}</Badge>
            &nbsp;
            <Badge variant={statuses[task.status].color}>{statuses[task.status].name}</Badge>
            <br/>
            Deadline: &nbsp; {task.dueDate}
        </Card.Subtitle>
    }

    render() {
        const colNum = 3;
        const tasks = this.props.tasks;
        // const rawTasks = this.props.tasks.map(({task, owner}) => {
        //     task.author = owner;
        //     //
        //     return task;
        // } );

        while (tasks.length % colNum)
            tasks.push(stubTask);

        var matrix = reshape(tasks, colNum);

        return <Container>
            {matrix.map((row) => <div><CardDeck>
                {row.map(task => this.processTaskElement(task))}
            </CardDeck>
                <br />
            </div>
            )}
        </Container>
    }
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

const stubTask = { name: '', description: '', owner: { name: null } };


const priorities = {
    MINOR: { color: 'secondary', name: 'MINOR' },
    MAJOR: { color: 'primary', name: 'MAJOR' },
    CRITICAL: { color: 'warning', name: 'CRITICAL' },
    BLOCKER: { color: 'danger', name: 'BLOCKER' }
};

const statuses = {
    NOT_STARTED: { color: 'secondary', name: 'NOT STARTED' },
    CANCELED: { color: 'danger', name: 'CANCELED' },
    IN_PROGRESS: { color: 'info', name: 'IN_PROGRESS' },
    RESOLVED: { color: 'primary', name: 'RESOLVED' },
    CLOSED: { color: 'dark', name: 'CLOSED' }
};
