import axios from 'axios';
import { backurl } from './properties';

//STORAGE
export function authorizationHeader() {
    let token = localStorage.getItem('token');
    return { Authorization: token };
}

export function getUser() {
    let user = JSON.parse(localStorage.getItem('auth')).user;
    return user;
}

// NETWORK
export async function getAllUsers() {
    let users;
    await axios
        .get(backurl + '/users/all', { headers: authorizationHeader })
        .then(response =>
            users = response.data)
        .catch(error => {

            switch (error.response.status) {
                case 401:
                    console.log('Relogin...');
                    relogin();
                    axios
                        .get(backurl + '/users/all', { headers: authorizationHeader })
                        .then(response =>
                            users = response.data)
                        .catch(error => {
                            console.log('ERROR while login: ', JSON.stringify(error.response));
                            alert('Your session was closed. Login again to continue', error.response.status);
                        });
                    break;
                default:
                    console.log('ERROR while login: ', JSON.stringify(error.response));
                    alert('An error occured. Please, contact administrators. Error status: ', error.response.status)
            }
        })

    return users;
}

export async function login(user) {
    let status;
    await Promise.resolve(axios.post(`http://localhost:8090/api/auth/login`, user)
        .then(response => {
            //save token
            var tokenType = response.data.tokenType;
            var token = response.data.accessToken
            status = response.status;
            localStorage.setItem('token', tokenType + ' ' + token);
        })
        .catch((error) => {
            status = error.response.status;
            console.log('ERROR WHILE LOGIN: ', status);
        }));
    return status;
}

export function relogin() {
    let user = getUser();
    return login(user);
}