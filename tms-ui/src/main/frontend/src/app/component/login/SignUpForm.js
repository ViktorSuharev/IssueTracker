import React from "react";
import * as axios from "axios";
import 'bootstrap/dist/css/bootstrap.css';
import {Link} from "react-router-dom";
import './index.css';

class SignUpForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            fullName: '',
            email: '',
            password: '',
        };

        this.onSubmit = this.onSubmit.bind(this);
    }

    onSubmit(event) {
        event.preventDefault();

        const user = {
            fullName : this.state.fullName,
            email : this.state.email,
            password : this.state.password
        };

            axios(`http://localhost:8090/register/`, {
                method: 'POST',
                headers: {
                    'content-type': 'application/json',
                },
                data: {user: user}
            })
                .then(res => {
                })
                .catch(function (error) {
                    console.log(error);
                });


    };

    render() {
        return (
            <div id="wrapper">
                <div className="d-flex flex-row">
                    <div className="mt-1 py-2  flex-grow-1">
                        <h2>
                            Sign Up Form
                        </h2>
                    </div>
                    <div className="d-flex mr-4 justify-content-end align-self-end mt-2">
                        <button type="button" className="btn btn-outline-danger  btn-sm">
                            <Link to="/cancel">
                                Cancel
                            </Link>
                        </button>
                    </div>

                    <div className="d-flex mr-4 justify-content-end align-self-end mt-2">
                        <form onSubmit={this.onSubmit}>
                            <button type="submit" className="btn btn-outline-success   btn-sm">
                                Create project
                            </button>
                        </form>
                    </div>
                </div>
                <hr/>

                <form>
                    <div className="d-flex flex-row mx-1">
                        <div className=" d-flex mr-4 justify-content-end align-self-end mt-2">
                            <label> Name: <input type="text" value={this.state.name} className="form-control"
                                                 onChange={this.onNameChange}/>
                            </label>
                        </div>
                    </div>
                </form>
        );
    }
}

export default SignUpForm;