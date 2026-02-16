import React, { useState, useEffect } from 'react';
import { useApp } from '../context/AppContext';
import { sellersAPI } from '../api/sellerApi';
import DataTable from '../components/tables/DataTable';
import './Sellers.css';

const Sellers = () => {
  const { addNotification } = useApp();
  const [sellers, setSellers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedSeller, setSelectedSeller] = useState(null);
  const [showModal, setShowModal] = useState(false);

  useEffect(() => {
    fetchSellers();
  }, []);

  const fetchSellers = async () => {
    try {
      setLoading(true);
      
      const response = await sellersAPI.getAllSellers();
      const normalizedSellers = response.map((seller) => ({
        id: seller.sellerId,
        name: seller.name,
        mobile: seller.mobile,
        status: seller.status,
        createdAt: seller.createdAt
        // email: "-",
        // productsCount: 0,
      }));
      setSellers(normalizedSellers);
      setLoading(false);

    } catch (error) {
      addNotification(error.message || 'Failed to load sellers', 'danger');
      setLoading(false);
    }
  };

  const handleViewSeller = (seller) => {
    setSelectedSeller(seller);
    setShowModal(true);
  };

  const handleToggleStatus = async (seller) => {
    const action = seller.status === 'ACTIVE' ? 'suspend' : 'activate';
    const confirmMessage = `Are you sure you want to ${action} ${seller.name}?`;
    
    if (!window.confirm(confirmMessage)) {
      return;
    }

    try {
      
      await sellersAPI.toggleSellerStatus(seller.id);
      addNotification(`Seller ${action}d successfully!`, 'success');
      fetchSellers();
      
    } catch (error) {
      addNotification(error.message || `Failed to ${action} seller`, 'danger');
    }
  };

  const getStatusBadge = (status) => {
    return <span className={`status-badge status-${status.toLowerCase()}`}>{status}</span>;
  };

  const columns = [
    { header: 'Seller ID', key: 'id' },
    { header: 'Name', key: 'name' },
    { header: 'Mobile', key: 'mobile' },
    {header: 'Created At' , key: 'createdAt',align: 'center'},
    // { header: 'Email', key: 'email' },
    // { 
    //   header: 'Products', 
    //   key: 'productsCount',
    //   align: 'center'
    // },
    { 
      header: 'Status', 
      key: 'status',
      align: 'center',
      render: (row) => getStatusBadge(row.status)
    },
    {
      header: 'Actions',
      key: 'actions',
      align: 'center',
      render: (row) => (
        <div className="action-buttons">
          <button 
            className="btn-action btn-view"
            onClick={(e) => {
              e.stopPropagation();
              handleViewSeller(row);
            }}
          >
            View
          </button>
          <button 
            className={`btn-action ${row.status === 'ACTIVE' ? 'btn-suspend' : 'btn-activate'}`}
            onClick={(e) => {
              e.stopPropagation();
              handleToggleStatus(row);
            }}
          >
            {row.status === 'ACTIVE' ? 'Suspend' : 'Activate'}
          </button>
        </div>
      )
    }
  ];

  return (
    <div className="sellers-page fade-in">
      <div className="page-header">
        <div>
          <h2>Seller Management</h2>
          <p className="page-subtitle">Manage seller identities and validation</p>
        </div>
      </div>

      {/* Sellers Table */}
      <DataTable
        columns={columns}
        data={sellers}
        loading={loading}
        emptyMessage="No sellers found"
      />

      {/* Seller Details Modal */}
      {showModal && selectedSeller && (
        <div className="modal-overlay" onClick={() => setShowModal(false)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">
              <h3>Seller Details</h3>
              <button className="modal-close" onClick={() => setShowModal(false)}>×</button>
            </div>
            
            <div className="modal-body">
              <div className="detail-row">
                <span className="detail-label">Seller ID:</span>
                <span className="detail-value">{selectedSeller.id}</span>
              </div>
              <div className="detail-row">
                <span className="detail-label">Name:</span>
                <span className="detail-value">{selectedSeller.name}</span>
              </div>
              <div className="detail-row">
                <span className="detail-label">Mobile:</span>
                <span className="detail-value">{selectedSeller.mobile}</span>
              </div>
              <div className="detail-row">
                <span className="detail-label">Created At:</span>
                <span className="detail-value">{selectedSeller.createdAt}</span>
              </div>
              {/* <div className="detail-row">
                <span className="detail-label">Email:</span>
                <span className="detail-value">{selectedSeller.email}</span>
              </div> */}
              <div className="detail-row">
                <span className="detail-label">Status:</span>
                <span className="detail-value">{getStatusBadge(selectedSeller.status)}</span>
              </div>
              {/* <div className="detail-row">
                <span className="detail-label">Products:</span>
                <span className="detail-value">{selectedSeller.productsCount} products</span>
              </div> */}
            </div>

            <div className="modal-footer">
              <button className="btn-secondary" onClick={() => setShowModal(false)}>
                Close
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Sellers;