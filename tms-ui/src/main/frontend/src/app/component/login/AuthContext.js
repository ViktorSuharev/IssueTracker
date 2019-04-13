import * as axios from "axios";
import React from 'react';

const AuthContext = React.createContext();

const AuthConsumer = AuthContext.Consumer

class AuthProvider extends React.Component {

    constructor(props) {
        super(props);

        this.state = { isAuth: false, user: null };

        this.login = this.login.bind(this);
        this.logout = this.logout.bind(this);
    }

    login(user) {
        axios.post(`http://localhost:8090/api/auth/login`, user)
            .then(response => {
                var tokenType = response.data.tokenType;
                var token = response.data.accessToken
                localStorage.setItem('token', tokenType + ' ' + token);

                axios.get(`http://localhost:8090/api/users/me`, {
                    headers: { Authorization: tokenType + ' ' + token }
                })
                .then(res => {
                    let u = res.data;
                    this.setState({ isAuth: true, user: u });
                });
                })
            .catch(function (error) {
                if (error.response.status === 401)
                    alert('Wrong login or password');
                else
                    console.log(error);
            });

    }

    logout() {
        this.setState({ isAuth: false, user: null });
        localStorage.removeItem('token');
    }

    render() {
        return (
            <AuthContext.Provider
                value={{
                    isAuth: this.state.isAuth,
                    user: this.state.user,
                    login: this.login,
                    logout: this.logout
                }}
            >
                {this.props.children}
            </AuthContext.Provider>
        )
    }
}

export { AuthProvider, AuthConsumer }