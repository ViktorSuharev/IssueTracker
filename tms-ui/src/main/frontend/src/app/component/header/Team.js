import React from 'react';
import * as axios from 'axios';


class Team extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users: []
        };

    }


    componentDidMount() {
        axios.get(`http://localhost:8090/users/`)
            .then(res => {
                const users = res.data;
                this.setState({users: users});
            })
    };


    render() {

        return (
            <div>

                <div className=" d-flex flex-row justify-content-center align-items-center">
                    Team
                </div>

                <div className="table-responsive">
                    <table className="table table-light table-striped table-bordered table-hover table-sm  ">
                        <thead className="thead-dark">
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Full Name</th>
                            <th scope="col">Password</th>
                            <th scope="col">EMail</th>
                        </tr>
                        </thead>
                        <tbody>

                        {this.state.users.map(user =>
                            <tr key={user.id}>
                                <th scope="row">{user.id}</th>
                                <td> {user.fullName}</td>
                                <td>{user.password}</td>
                                <td> {user.email}</td>
                            </tr>
                        )
                        }
                        </tbody>
                    </table>
                </div>


            </div>
        )
    }
}

export default Team;