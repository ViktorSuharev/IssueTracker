import React from 'react';
import * as axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css';
import { Container, Modal, Button } from 'react-bootstrap';
import {backurl} from '../../properties';

export default class ProjectEditor extends React.Component {
    constructor(props) {
        super(props);

        var project = props.project ? project : null;

        this.state = {
            project: project,
            users: null
        };
    }

    componentDidMount() {
        axios.get(backurl + '/users/all')
    }

    render() {
        return
    }
}
