import withRepository from "../../hocs/withRepository.jsx";
import AddMetric from "./AddMetric.jsx";
import AddMetricRepository from "./repository/addMetricRepository.js";

export default withRepository(AddMetricRepository)(AddMetric);