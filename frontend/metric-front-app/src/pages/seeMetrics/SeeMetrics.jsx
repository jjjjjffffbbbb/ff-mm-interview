import React, {useEffect} from "react";
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
} from 'chart.js';
import { Line } from 'react-chartjs-2';
import useSelector from "./hooks/useSelector.jsx";
import useChart from "./hooks/useChart.jsx";

const SeeMetrics = props => {

    const { repository } = props;

    const selector = useSelector();
    const chart = useChart();

    ChartJS.register(
        CategoryScale,
        LinearScale,
        PointElement,
        LineElement,
        Title,
        Tooltip,
        Legend
    );

    useEffect(() => {
        if (selector.selectValue === '') return;

        repository.getMetrics(selector.selectValue)
            .then(chart.loadData);

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

                {!chart.isDataEmtpy() &&
                    <Line options={chart.options} data={chart.data} />
                }
            </div>
        </>
    )
}

export default SeeMetrics;