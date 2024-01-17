import React, {Fragment, useEffect} from "react";

const AddMetric = props => {
    const {
        repository,
    } = props;

    useEffect(() => {
        repository.addMetric("metric", "2024-01-16T00:39:18.371+01:00", 1.0)
    }, []);

    return (
        <>
            <div className="container">
                <div className="title-container">
                    <h1>Add Metric</h1>
                </div>
            </div>
        </>
    );
}

export default AddMetric;