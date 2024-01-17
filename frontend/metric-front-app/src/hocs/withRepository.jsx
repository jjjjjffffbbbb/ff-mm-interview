import React from 'react';

export default Repository => Component => props => {
    const env = "http://localhost:8081";

    const httpClient = ({
        get: (e) => fetch(env + e),
        post: (e, b) => fetch(env + e, {
            method: 'POST',
            body: JSON.stringify(b),
            headers: {
                'Content-Type': 'application/json',
            }
        }),
    });

    return <Component {...props} repository={Repository(httpClient)} />;
};