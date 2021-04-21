export function authHeader() {
    // return authorization header with jwt token
    let user = localStorage.getItem('user');

    if (user) {
        return { 
            headers:{
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + user 
            }
        };
    } else {
        return {};
    }
}