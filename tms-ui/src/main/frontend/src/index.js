import * as React from "react";
import ReactDOM from "react-dom";
import "./app/component/project/index.css";
import "github-fork-ribbon-css/gh-fork-ribbon.css";
import 'bootstrap/dist/css/bootstrap.min.css';

import 'bootstrap/dist/js/bootstrap.bundle.min';
import App from "./app/App";
import { AuthProvider } from "./app/component/login/AuthContext";


ReactDOM.render((
    <AuthProvider><App/></AuthProvider>
    ), document.getElementById("root")
);
