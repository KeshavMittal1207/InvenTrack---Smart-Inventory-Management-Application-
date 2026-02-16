import React, { useState, useEffect } from 'react';
import { useApp } from '../context/AppContext';
import { dashboardAPI } from '../api/dashboardApi';
import KPICard from '../components/cards/KPICard';
import { BarChart, Bar, LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';
import './Dashboard.css';

const Dashboard = () => {
  const { addNotification, setAlertsCount } = useApp();
  const [loading, setLoading] = useState(true);
  const [stats, setStats] = useState({
    totalProducts: 0,
    totalStock: 0,
    lowStockProducts: 0,
    expiringSoon: 0
  });
  const [stockData, setStockData] = useState([]);
  const [expiryTrend, setExpiryTrend] = useState([]);
  const [recentAlerts, setRecentAlerts] = useState([]);

  useEffect(() => {
    fetchDashboardData();
  }, []);

  const fetchDashboardData = async () => {
    try {
      setLoading(true);
      
      const [statsRes, stockRes, trendRes] = await Promise.all([
        dashboardAPI.getStats(),
        dashboardAPI.getStockByProduct(),
        dashboardAPI.getExpiryTrend(),
      ]);

      const alertsRes = await dashboardAPI.getRecentAlerts();
      setStats(statsRes);
      setStockData(stockRes);
      setExpiryTrend(trendRes);
      setRecentAlerts(alertsRes);
      setAlertsCount(alertsRes.length);
      setLoading(false);
    } catch (error) {
      addNotification(error.message || 'Failed to load dashboard data', 'danger');
      setLoading(false);
    }
  };

  return (
    <div className="dashboard-page fade-in">
      <div className="page-header">
        <h2>Dashboard Overview</h2>
        <p className="page-subtitle">Real-time inventory health and operational metrics</p>
      </div>

      {/* KPI Cards */}
      <div className="kpi-grid">
        <KPICard
          title="Total Products"
          value={stats.totalProducts.toLocaleString()}
          icon="📦"
          color="default"
          loading={loading}
          trend={{ direction: 'up', value: 12 }}
        />
        <KPICard
          title="Total Stock"
          value={stats.totalStock.toLocaleString()}
          icon="📊"
          color="success"
          loading={loading}
          trend={{ direction: 'up', value: 8 }}
        />
        <KPICard
          title="Low Stock Items"
          value={stats.lowStockProducts}
          icon="⚠️"
          color="warning"
          loading={loading}
        />
        <KPICard
          title="Expiring Soon"
          value={stats.expiringSoon}
          icon="⏰"
          color="danger"
          loading={loading}
        />
      </div>

      {/* Charts Section */}
      <div className="charts-grid">
        <div className="chart-card">
          <div className="chart-header">
            <h3>Stock by Product</h3>
            <p>Current inventory levels across top products</p>
          </div>
          <div className="chart-content">
            {loading ? (
              <div className="chart-loading">
                <div className="loading-spinner"></div>
              </div>
            ) : (
              <ResponsiveContainer width="100%" height={300}>
                <BarChart data={stockData}>
                  <CartesianGrid strokeDasharray="3 3" stroke="#E5E7EB" />
                  <XAxis dataKey="product" stroke="#6B7280" style={{ fontSize: '12px' }} />
                  <YAxis stroke="#6B7280" style={{ fontSize: '12px' }} />
                  <Tooltip 
                    contentStyle={{ 
                      background: 'white', 
                      border: '1px solid #E5E7EB',
                      borderRadius: '8px',
                      boxShadow: '0 4px 6px -1px rgba(0, 0, 0, 0.1)'
                    }}
                  />
                  <Bar dataKey="stock" fill="#6366F1" radius={[8, 8, 0, 0]} />
                </BarChart>
              </ResponsiveContainer>
            )}
          </div>
        </div>

        <div className="chart-card">
          <div className="chart-header">
            <h3>Expiry Trend (Next 30 Days)</h3>
            <p>Projected batches expiring over time</p>
          </div>
          <div className="chart-content">
            {loading ? (
              <div className="chart-loading">
                <div className="loading-spinner"></div>
              </div>
            ) : (
              <ResponsiveContainer width="100%" height={300}>
                <LineChart data={expiryTrend}>
                  <CartesianGrid strokeDasharray="3 3" stroke="#E5E7EB" />
                  <XAxis dataKey="day" stroke="#6B7280" style={{ fontSize: '12px' }} />
                  <YAxis stroke="#6B7280" style={{ fontSize: '12px' }} />
                  <Tooltip 
                    contentStyle={{ 
                      background: 'white', 
                      border: '1px solid #E5E7EB',
                      borderRadius: '8px',
                      boxShadow: '0 4px 6px -1px rgba(0, 0, 0, 0.1)'
                    }}
                  />
                  <Line 
                    type="monotone" 
                    dataKey="batches" 
                    stroke="#EF4444" 
                    strokeWidth={3}
                    dot={{ fill: '#EF4444', r: 5 }}
                    activeDot={{ r: 7 }}
                  />
                </LineChart>
              </ResponsiveContainer>
            )}
          </div>
        </div>
      </div>

      {/* Recent Alerts */}
<div className="alerts-preview">
  <div className="section-header">
    <h3>Recent Alerts</h3>
    <a href="/alerts" className="view-all-link">View All →</a>
  </div>
  
  {loading ? (
    <div className="alerts-loading">
      <div className="loading-spinner"></div>
    </div>
  ) : (
    <div className="alerts-list">
      {recentAlerts.length === 0 ? (
        <div className="no-alerts">No recent alerts 🎉</div>
      ) : (
        recentAlerts.map(alert => {
          const isLowStock = alert.alertType === 'LOW_STOCK';

          return (
            <div key={alert.alertId} className="alert-item">
              
              <div className="alert-icon">
                {isLowStock ? '📉' : '⏰'}
              </div>

              <div className="alert-content">
                <div className="alert-message">
                  {isLowStock
                    ? `Low stock for Product ID ${alert.productId}`
                    : `Batch ${alert.batchId} is near expiry`
                  }
                </div>

                <div className="alert-time">
                  {alert.createdAt}
                </div>
              </div>

              <div className={`alert-badge ${isLowStock ? 'badge-warning' : 'badge-danger'}`}>
                {alert.alertType.replace('_', ' ')}
              </div>

            </div>
          );
        })
      )}
    </div>
  )}
</div>

    </div>
  );
};

export default Dashboard;