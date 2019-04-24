import axios from 'axios';
import { backurl } from './properties';

export function wordWrap(toWrap, length) {
    const len = length ? length : 30;
    const word = toWrap;

    var res = [];
    let i = 0;
    for(i = 0; i < word.length; i+= len) {
        res.push(word.substr(i, len));
        console.log(JSON.stringify(res));
    }
    return res;
}


//USEFUL
export function shortenIfLong(title, length) {
    const len = length ? length : 20;

    return title.length > len ? title.substring(0, len - 4) + '...' : title;
}


//STORAGE
export function authorizationHeader() {
    let token = localStorage.getItem('token');
    return { headers: { Authorization: token } };
}

export function getUser() {
    let user = JSON.parse(localStorage.getItem('auth')).user;
    return user;
}

// NETWORK
// export async function getAllUsers() {
//     var users;
//     let token = authorizationHeader();
//     console.log(token);

//     let resp = await axios.get(backurl + '/users/all', {
//             headers: { Authorization: token }})
//         .then(response => {
//             console.log('HERE ', JSON.parse(response));
//             users = response.data
//         })
//         .catch(error => {
//             console.log(JSON.stringify(error));
//             console.log('BUT NOT HERE ');

//             // switch (error.response.status) {
//             //     case 401:
//             //         console.log('Relogin...');
//             //         relogin();
//             //         axios.get(backurl + '/users/all', { headers: auth })
//             //             .then(response =>
//             //                 users = response.data)
//             //             .catch(error => {
//             //                 console.log('ERROR while login: ', JSON.stringify(error.response));
//             //                 alert('Your session was closed. Login again to continue', error.response.status);
//             //             });
//             //         break;
//             //     default:
//             //         console.log('ERROR while login: ', JSON.stringify(error.response));
//             //         alert('An error occured. Please, contact administrators. Error status: ', error.response.status)
//             // }
//         })
//     console.log('HERE ', JSON.stringify(resp));
//     console.log('HERE ', JSON.stringify(users));

//     return users;
// }

// export async function sendRequest(url, type, headers, body) {
//     let str = JSON.stringify(authorizationHeader());
//     let request = {
//         method: type,
//         headers: {
//             str,
//             'content-type': 'application/json',
//         },
//     }

// }

export async function login(user) {
    let status;
    await axios.post(backurl + '/auth/login', user)
        .then(response => {
            //save token
            var tokenType = response.data.tokenType;
            var token = response.data.accessToken
            status = response.status;
            localStorage.setItem('token', tokenType + ' ' + token);
        })
        .catch((error) => {
            status = error.response ? error.response.status : 0;
            console.log('ERROR WHILE LOGIN: ', status);
        });
    return status;
}

export function relogin() {
    let user = JSON.parse(localStorage.getItem('auth')).creds;
    return login(user);
}
