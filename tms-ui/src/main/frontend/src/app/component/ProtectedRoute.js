import React, { Component } from 'react';
import { Route, Redirect } from 'react-router-dom';
import { AuthConsumer } from './login/AuthContext';

const ProtectedRoute = ({ component: Component, ...rest }) => (
    <AuthConsumer>
        {({ isAuth }) => (
            <Route
                render={
                    props =>
                        isAuth
                            ? <Component {...props} />
                            : <Redirect to="/auth" />
                }
            {...rest}
            />
        )}
    </AuthConsumer>
);

export default ProtectedRoute;