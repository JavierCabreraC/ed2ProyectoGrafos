import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { NetworkPlanner } from './pages/NetworkPlanner';

import './App.css'


function App() {
    return (
        <BrowserRouter>
            <Routes>
              <Route path="/" element={<NetworkPlanner />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
