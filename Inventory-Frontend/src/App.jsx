import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AppProvider } from './context/AppContext';
import Layout from './components/layout/Layout';
import Dashboard from './pages/Dashboard';
import Inventory from './pages/Inventory';
import Product from './pages/Product'
import Orders from './pages/Orders';
import Alerts from './pages/Alerts';
import Sellers from './pages/Sellers';
import './App.css';

function App() {
  return (
    <AppProvider>
      <Router>
        <Layout>
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/inventory" element={<Inventory />} />
            <Route path="/product" element={<Product />} />
            <Route path="/orders" element={<Orders />} />
            <Route path="/alerts" element={<Alerts />} />
            <Route path="/sellers" element={<Sellers />} />
          </Routes>
        </Layout>
      </Router>
    </AppProvider>
  );
}

export default App;