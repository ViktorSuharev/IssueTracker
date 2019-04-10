import React from 'react';

const AuthContext = React.createContext();

const AuthConsumer = AuthContext.Consumer

class AuthProvider extends React.Component {

    constructor(props) {
        super(props);

        this.state = { isAuth: false, user: null };

        this.login = this.login.bind(this);
        this.logout = this.logout.bind(this);
        this.updateUser = this.updateUser.bind(this);
    }

    updateUser = (u) => {
        this.setState({user: u});
    }
    
    login() {


        this.setState({ isAuth: true })
    }

    logout() {
        this.setState({ isAuth: false })
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