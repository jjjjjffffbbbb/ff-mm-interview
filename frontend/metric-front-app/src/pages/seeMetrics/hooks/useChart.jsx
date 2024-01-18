import React, { useState } from 'react';

export default () => {
    const [data, setData] = useState({});

    const isDataEmtpy = () => Object.keys(data).length === 0

    const options = {
        responsive: true,
        plugins: {
            legend: {
                position: 'top',
            },
        },
    };

    const mapLabels = data => data.map(x => x.time);
    const mapValues = data => data.map(x => x.average);

    const loadData = data => {
        const calculatedData = {
            labels: mapLabels(data),
            datasets: [
                {
                    label: 'Metrics',
                    data: mapValues(data),
                    borderColor: 'rgb(255, 99, 132)',
                    backgroundColor: 'rgba(255, 99, 132, 0.5)',
                }
            ]
        }
        setData(calculatedData);
    };

    return {
        isDataEmtpy,
        options,
        loadData,
        data,
    };
};