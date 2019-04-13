import * as axios from "axios";
import React from 'react';
// import bcrypt from 'bcryptjs';

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
        // const hash = bcrypt.hashSync(user.password, 10);
        // user.password = hash;

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

    // componentWillReceiveProps(props) {
        // localStorage.setItem('auth', JSON.stringify(this.state));
    // }

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