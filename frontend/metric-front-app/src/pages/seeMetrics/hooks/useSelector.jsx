import React, { useState } from 'react';

export default () => {
    const [selectValue, setSelectValue] = useState('');

    const onChange = e => {
        setSelectValue(e.target.value);
    }

    return {
        selectValue, onChange
    };
};