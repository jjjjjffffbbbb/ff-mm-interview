import React, { useState } from 'react';

export default () => {
    const [errors, setErrors] = useState({});
    const [name, setName] = useState('');
    const [value, setValue] = useState('');
    const [feedBack, setFeedBack] = useState('');

    const hasErrors = () => Object.keys(errors).length !== 0;
    const clear = () => {
        setName('');
        setValue('');
        setErrors({});
    }

    return {
        errors, setErrors, name, setName, value, setValue, hasErrors, clear, feedBack, setFeedBack
    };
};