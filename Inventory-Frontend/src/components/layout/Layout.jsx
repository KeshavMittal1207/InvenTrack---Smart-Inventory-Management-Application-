import React, { useState } from 'react';
import { NavLink } from 'react-router-dom';
import { useApp } from '../../context/AppContext';
import './Layout.css';

const Layout = ({ children }) => {
  const { user, alertsCount, notifications } = useApp();
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);

  const menuItems = [
    { path: '/', label: 'Dashboard', icon: '📊' },
    {path: '/product' , label: 'Products' , icon: '🛍️'},
    { path: '/inventory', label: 'Inventory', icon: '📦' },
    { path: '/orders', label: 'Orders', icon: '🛒' },
    { path: '/alerts', label: 'Alerts', icon: '🔔', badge: alertsCount },
    { path: '/sellers', label: 'Sellers', icon: '👥' },
  ];

  return (
    <div className="layout">
      {/* Sidebar */}
      <aside className={`sidebar ${!isSidebarOpen ? 'collapsed' : ''}`}>
        <div className="sidebar-header">
          <div className="logo">
            <a href='http://localhost:3000/'><span className="logo-icon">📊</span></a>
            {isSidebarOpen && <span className="logo-text">InvenTrack</span>}
          </div>
        </div>

        <nav className="sidebar-nav">
          {menuItems.map((item) => (
            <NavLink
              key={item.path}
              to={item.path}
              className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}
              end={item.path === '/'}
            >
              <span className="nav-icon">{item.icon}</span>
              {isSidebarOpen && (
                <>
                  <span className="nav-label">{item.label}</span>
                  {item.badge > 0 && (
                    <span className="nav-badge">{item.badge}</span>
                  )}
                </>
              )}
            </NavLink>
          ))}
        </nav>

        <button
          className="sidebar-toggle"
          onClick={() => setIsSidebarOpen(!isSidebarOpen)}
        >
          <span>{isSidebarOpen ? '◀' : '▶'}</span>
        </button>
      </aside>

      {/* Main Content Area */}
      <div className="main-wrapper">
        {/* Top Navbar */}
        <header className="navbar">
          <div className="navbar-left">
            <h1 className="page-title">Inventory Management System</h1>
          </div>
          
          <div className="navbar-right">
            <div className="user-info">
              <div className="user-avatar">{user.name.charAt(0)}</div>
              <div className="user-details">
                <div className="user-name">{user.name}</div>
                <div className="user-role">{user.role}</div>
              </div>
            </div>
          </div>
        </header>

        {/* Page Content */}
        <main className="content">
          {children}
        </main>
      </div>

      {/* Notifications */}
      {notifications.length > 0 && (
        <div className="notifications-container">
          {notifications.map((notif) => (
            <div key={notif.id} className={`notification notification-${notif.type}`}>
              {notif.message}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Layout;