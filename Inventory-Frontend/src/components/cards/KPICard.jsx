import React from 'react';
import './KPICard.css';

const KPICard = ({ title, value, icon, color = 'default', trend, loading = false }) => {
  return (
    <div className={`kpi-card kpi-card-${color}`}>
      <div className="kpi-header">
        <div className="kpi-icon">{icon}</div>
        {trend && (
          <div className={`kpi-trend ${trend.direction}`}>
            <span className="trend-arrow">{trend.direction === 'up' ? '↗' : '↘'}</span>
            <span className="trend-value">{trend.value}%</span>
          </div>
        )}
      </div>
      
      {loading ? (
        <div className="kpi-loading">
          <div className="loading-spinner"></div>
        </div>
      ) : (
        <>
          <div className="kpi-value">{value}</div>
          <div className="kpi-title">{title}</div>
        </>
      )}
    </div>
  );
};

export default KPICard;