import * as axios from "axios";
import React from 'react';

const AuthContext = React.createContext();

const AuthConsumer = AuthContext.Consumer

class AuthProvider extends React.Component {

    constructor(props) {
        super(props);

        let state = JSON.parse(localStorage.getItem('auth'));

        if(state == null){
            state = { isAuth: false, user: null };
            localStorage.setItem('auth', JSON.stringify(state));
        }

        this.state = state;
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
                    this.setState({ isAuth: true, user: u, status: response.status });
                    console.log('LOGGED_IN:\t', JSON.stringify(response.status));
                    console.log('\tas\t', user.email);
                });
                })
            .catch((error) => {
                this.setState({status: error.response.status});
                console.log('ERROR WHILE LOGIN: ', this.state.status);
            });
    }

    logout() {
        this.setState({ isAuth: false, user: null });
        localStorage.removeItem('token');
        localStorage.removeItem('auth');
    }

    render() {
        localStorage.setItem('auth', JSON.stringify(this.state));
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