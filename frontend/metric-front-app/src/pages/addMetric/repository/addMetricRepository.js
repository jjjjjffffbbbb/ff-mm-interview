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

    return {
        addMetric,
    }
}