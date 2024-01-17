import React from 'react';

export default Repository => Component => props => {
    const env = "http://localhost:8081";

    const propagate = res => (
        new Promise((resolve, reject) => {
            if (!res.ok) reject(res);
            resolve(res);
        })
    );

    const httpClient = ({
        get: (e) => fetch(env + e).then(propagate),
        post: (e, b) => fetch(env + e, {
            method: 'POST',
            body: JSON.stringify(b),
            headers: {
                'Content-Type': 'application/json',
            }
        }).then(propagate),
    });

    return <Component {...props} repository={Repository(httpClient)} />;
};