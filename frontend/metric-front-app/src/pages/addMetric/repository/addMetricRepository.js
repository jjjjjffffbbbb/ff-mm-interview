export default (http) => {

    const addMetric = (name, timestamp, value) => {
        const mapToRequest = () => ({
           name,
           timestamp,
           value
        });

        http.post("/metrics", mapToRequest())
            .then(response => response.json)
            .then(data => console.log(data));

    }

    return {
        addMetric,
    }
}