import withRepository from "../../hocs/withRepository.jsx";
import SeeMetricsRepository from "./repository/seeMetricRepository.js";
import SeeMetrics from "./SeeMetrics.jsx";

export default withRepository(SeeMetricsRepository)(SeeMetrics);