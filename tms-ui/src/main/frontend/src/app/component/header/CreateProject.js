import React from "react";
import * as axios from "axios";
import 'bootstrap/dist/css/bootstrap.css';
import {Link} from "react-router-dom";

class CreateProject extends React.Component {
    constructor(props) {
        super(props);
        this.state = {name: ''};

        this.onNameChange = this.onNameChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    onSubmit(event) {
        alert(`New project with name "${this.state.name}" was created!`);
        event.preventDefault();
    }


    onNameChange(event) {
        this.setState({name: event.target.value});
    }

    render() {
        return (
            <div>

                <div className="mt-1 py-2  flex-grow-1  badge badge-primary text-wrap ">
                    New Project's settings
                </div>
                <hr/>

                <form onSubmit={this.onSubmit}>
                    <p><label> Название: <input type="text" name="name" value={this.state.name}
                                                onChange={this.onNameChange}/></label></p>
                    <p><input type="submit" value="Submit"/></p>
                </form>

                <div className="d-flex mr-4" style={{"fontSize": "30px"}}>
                    <button type="button" className="btn btn-outline-success   btn-sm"><Link to="/cancel"> Cancel</Link>
                    </button>
                </div>




            </div>
        );
    }
}

export default CreateProject;