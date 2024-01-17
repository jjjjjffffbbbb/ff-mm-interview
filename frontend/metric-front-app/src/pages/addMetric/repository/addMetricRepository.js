export default (http) => {

    const addMetric = (name, timestamp, value) => {
        const mapToRequest = () => ({
           name,
           timestamp,
           value
        });

        return http.post("/metrics", mapToRequest())
            .then(response => response.json())
    }

    const addDefaults = () => {
        return http.post("/testing/add-default-metrics")
    }

    return {
        addMetric,
        addDefaults,
    }
}