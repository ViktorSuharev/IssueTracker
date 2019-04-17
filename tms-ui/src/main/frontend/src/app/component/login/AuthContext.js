import * as axios from 'axios';
import React from 'react';
import { login, authorizationHeader } from '../../actions';
import { backurl } from '../../properties';

const AuthContext = React.createContext();

const AuthConsumer = AuthContext.Consumer

class AuthProvider extends React.Component {

    constructor(props) {
        super(props);

        let state = JSON.parse(localStorage.getItem('auth'));

        if (state == null) {
            state = { isAuth: false, user: null, status: 0, creds: null};
            localStorage.setItem('auth', JSON.stringify(state));
        }

        this.state = state;
        this.login = this.login.bind(this);
        this.logout = this.logout.bind(this);
    }

    async login(user) {
        let status = await login(user);
        this.setState({ status: status });
        console.log(JSON.stringify(this.state));

        if (status === 200)
            axios.get(backurl + 'users/me', { headers: authorizationHeader() })
                .then(response => {
                    var u = response.data;
                    this.setState({ isAuth: true, user: u, creds: user });
                    localStorage.setItem('auth', JSON.stringify(this.state));
                });
                
        return status;
    }

    logout() {
        this.setState({ isAuth: false, user: null, status: 0 });
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