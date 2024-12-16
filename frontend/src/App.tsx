import './App.css'


import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { NetworkPlanner } from './pages/NetworkPlanner';

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
