import React, {useEffect} from "react";
import useSelector from "./hooks/useSelector.jsx";

const SeeMetrics = props => {

    const { repository } = props;

    const selector = useSelector();

    useEffect(() => {
        if (selector.selectValue === '') return;

        repository.getMetrics(selector.selectValue)
            .then(response => console.log(response));
    }, [selector.selectValue]);

    return(
        <>
            <div className="container">
                <div className="title-container">
                    <h1>See Metrics</h1>
                </div>
                <select defaultValue="" onChange={selector.onChange}>
                    <option disabled value=""> -- select an option -- </option>
                    <option value="MINUTES">By minutes</option>
                    <option value="HOURS">By hours</option>
                    <option value="DAYS">By days</option>
                </select>
            </div>
        </>
    )
}

export default SeeMetrics;