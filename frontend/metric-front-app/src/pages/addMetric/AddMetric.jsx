import React, {Fragment} from "react";
import useAddMetricForm from "./hooks/useAddMetricForm.jsx";

const AddMetric = props => {
    const {
        repository,
    } = props;

    const form = useAddMetricForm();

    const handleSubmit = () => {
        const date = new Date().toISOString();
        repository.addMetric(form.name, date, form.value)
            .then(_ => form.setFeedBack(`Last metric added: ${form.name} -> ${form.value} @ ${date}`))
            .then(_ => form.clear())
            .catch(handleError);
    };

    const handleError = async res => {
        if (res.status !== 400) {
            form.setFeedBack('There was a critical error on the server');
            return;
        }

        const body = await res.json();

        if (!Array.isArray(body) || body.length === 0) {
            form.setFeedBack('There was an error with one of the inputs');
            return;
        }

        const errors = body.reduce((acc, error) => (Object.assign(acc, { [error.field]: error.error })), {});
        form.setErrors(errors);
    };

    const addDefaults = () => {
        repository.addDefaults();
    }

    return (
        <>
            <div className="container">
                <div className="title-container">
                    <h1>Add Metric</h1>
                </div>
                <div className="form-metric">
                    <label>Name:</label>
                    <input type="text" value={form.name} onChange={ e => form.setName(e.target.value)}/>
                    {form.errors.name && <p className="error">{form.errors.name}</p>}

                    <label>Value:</label>
                    <input type="text" value={form.value} onChange={ e => form.setValue(e.target.value)}/>
                    {form.errors.value && <p className="error">{form.errors.value}</p>}

                    <button onClick={() => handleSubmit()}>Add</button>

                    {form.feedBack !== '' && <p>{form.feedBack}</p>}
                </div>
                <hr/>
                <button onClick={() => addDefaults()}>Add defaults values</button>
            </div>
        </>
    );
}

export default AddMetric;