export default (http) => {

    const getMetrics = (groupBy) => {
        return http.get(`/metrics?groupBy=${groupBy}`)
            .then(response => response.json())
            .then(response => response.metrics);
    }

    return {
        getMetrics,
    }
}