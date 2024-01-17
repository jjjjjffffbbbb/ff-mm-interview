import {Route, Routes} from 'react-router-dom';
import Sidebar from "./sidebar/Sidebar.jsx";
import AddMetric from "./pages/addMetric/index.js";
import SeeMetrics from "./pages/seeMetrics/index.js";

function App() {

  return (
      <Routes>
        <Route path="/" element={<Sidebar />} >
            <Route path="add-metric" element={<AddMetric />} />
            <Route path="see-metrics" element={<SeeMetrics />} />
        </Route>
      </Routes>
  )
}

export default App;
