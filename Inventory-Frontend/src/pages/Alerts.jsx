import React, { useState, useEffect } from 'react';
import { useApp } from '../context/AppContext';
import { alertsAPI } from '../api/alertApi';
import DataTable from '../components/tables/DataTable';
import './Alerts.css';

const Alerts = () => {
  const { addNotification } = useApp();
  const [alerts, setAlerts] = useState([]);
  const [filteredAlerts, setFilteredAlerts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filterType, setFilterType] = useState('ALL');

  useEffect(() => {
    fetchAlerts();
  }, []);

  useEffect(() => {
    applyFilters();
  }, [alerts, filterType, ]);

  const fetchAlerts = async () => {
    try {
      setLoading(true);
    
      const response = await alertsAPI.getAllAlerts();
      const normalizedAlerts = response.map((alert) => ({
        alertId: alert.alertId,
        alertType: alert.alertType,
        batchId: alert.batchId,
        productId: alert.productId,
        date: alert.date
      }))
      console.log(normalizedAlerts)
      setAlerts(normalizedAlerts);
      setLoading(false);

    } catch (error) {
      addNotification(error.message || 'Failed to load alerts', 'danger');
      setLoading(false);
    }
  };

  const applyFilters = () => {
    let filtered = [...alerts];

    if (filterType !== 'ALL') {
      filtered = filtered.filter(alert => alert.alertType === filterType);
    }

    setFilteredAlerts(filtered);
  };

  const getTypeBadge = (alertType) => {
    const icon = alertType === 'LOW_STOCK' ? '📉' : '⏰';
    return (
      <span className="alert-type-badge">
        <span className="type-icon">{icon}</span>
        {alertType.replace('_', ' ')}
      </span>
    );
  };

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' });
  };

  const columns = [
    { 
      header: 'Alert ID', 
      key: 'alertId'
    },
    { 
      header: 'Alert Type', 
      key: 'alertType',
      align: 'center',
      render: (row) => getTypeBadge(row.alertType)
    },
    { header: 'Product ID', key: 'productId' },
    { 
      header: 'Batch ID', 
      key: 'batchId'
      // render: (row) => row.batchId || '—'
    },
    // { header: 'Message', key: 'message' },
    { 
      header: 'Date', 
      key: 'date',
      render: (row) => formatDate(row.date)
    },

  ];

  const getAlertStats = () => {
    const total = alerts.length;
    const lowStock = alerts.filter(a => a.alertType === 'LOW_STOCK').length;
    const nearExpiry = alerts.filter(a => a.alertType === 'NEAR_EXPIRY').length;

    return { total, lowStock, nearExpiry };
  };

  const stats = getAlertStats();

  return (
    <div className="alerts-page fade-in">
      <div className="page-header">
        <div>
          <h2>Alerts & Notifications</h2>
          <p className="page-subtitle">Risk monitoring and action visibility</p>
        </div>
      </div>

      {/* Alert Stats */}
      <div className="alert-stats">
        <div className="stat-item">
          <div className="stat-value">{stats.total}</div>
          <div className="stat-label">Total Alerts</div>
        </div>
        <div className="stat-item stat-warning">
          <div className="stat-value">{stats.lowStock}</div>
          <div className="stat-label">Low Stock</div>
        </div>
        <div className="stat-item stat-info">
          <div className="stat-value">{stats.nearExpiry}</div>
          <div className="stat-label">Near Expiry</div>
        </div>
      </div>

      {/* Filters */}
      <div className="alerts-filters">
        <div className="filter-group">
          <label>Filter by Type:</label>
          <select value={filterType} onChange={(e) => setFilterType(e.target.value)}>
            <option value="ALL">All Types</option>
            <option value="LOW_STOCK">Low Stock</option>
            <option value="NEAR_EXPIRY">Near Expiry</option>
          </select>
        </div>

        <div className="filter-results">
          Showing {filteredAlerts.length} of {alerts.length} alerts
        </div>
      </div>

      {/* Alerts Table */}
      <DataTable
        columns={columns}
        data={filteredAlerts}
        loading={loading}
        emptyMessage="No alerts found"
      />
    </div>
  );
};

export default Alerts;