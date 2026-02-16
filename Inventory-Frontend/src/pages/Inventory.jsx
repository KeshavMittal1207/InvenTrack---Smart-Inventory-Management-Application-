import React, { useState, useEffect } from 'react';
import { useApp } from '../context/AppContext';
import { inventoryAPI } from '../api/inventoryApi';
import DataTable from '../components/tables/DataTable';
import './Inventory.css';

const Inventory = () => {
  const { addNotification } = useApp();
  const [batches, setBatches] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showAddForm, setShowAddForm] = useState(false);
  const [formData, setFormData] = useState({
    productId: '',
    batchNo: '',
    quantity: '',
    thresholdQuantity: '',
    expiryDate: '',
    sellerId: ''
  });
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    fetchBatches();
  }, []);

  const fetchBatches = async () => {
    try {
      setLoading(true);
      
      const response = await inventoryAPI.getAllBatches();
      const normalizedBatches = response.map((batch) => ({
        batchId: batch.batchId,
        productId: batch.productId,
        batchNo: batch.batchNo,
        quantity: batch.quantity,
        thresholdQuantity: batch.thresholdQuantity,
        expiryDate: batch.expiryDate,
        status: batch.status,
        sellerId: batch.sellerId
      }))

      setBatches(normalizedBatches);
      setLoading(false);

    } catch (error) {
      addNotification(error.message || 'Failed to load inventory batches', 'danger');
      setLoading(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!formData.productId || !formData.batchNo || !formData.quantity || !formData.thresholdQuantity || !formData.expiryDate || !formData.sellerId) {
      addNotification('Please fill in all required fields', 'warning');
      return;
    }

    try {
      setSubmitting(true);

      await inventoryAPI.addBatch(formData);

      addNotification('Batch added successfully!', 'success');
      setShowAddForm(false);
      fetchBatches();

      setFormData({
        productId: '',
        batchNo: '',
        quantity: '',
        thresholdQuantity: '',
        expiryDate: '',
        sellerId: ''
      });
    
    } catch (error) {
      addNotification(error.message || 'Failed to add batch', 'danger');
    } finally {
      setSubmitting(false);
    }
  };

  const handleReset = () => {
    setFormData({
      productId: '',
      batchNo: '',
      quantity: '',
      thresholdQuantity: '',
      expiryDate: '',
      sellerId: ''
    });
  };

  const getStatusBadge = (status) => {
    const statusMap = {
      ACTIVE: 'active',
      SOLD_OUT: 'sold-out',
      EXPIRED: 'expired'
    };
    return <span className={`status-badge status-${statusMap[status]}`}>{status}</span>;
  };

  const getRowClass = (batch) => {
    if (batch.status === 'EXPIRED') return 'row-danger';
    
    const expiryDate = new Date(batch.expiryDate);
    const today = new Date();
    const daysUntilExpiry = Math.floor((expiryDate - today) / (1000 * 60 * 60 * 24));
    
    if (daysUntilExpiry <= 30 && daysUntilExpiry > 0) return 'row-warning';
    return '';
  };

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' });
  };

  const columns = [
    { header: 'Batch ID', key: 'batchId' , align: 'center'},
    { header: 'Product ID', key: 'productId', align: 'center' },
    { header: 'Batch Number', key: 'batchNo', align: 'center' },
    { header: 'Quantity', key: 'quantity', align: 'center' },
    { header: 'Threshold Quantity', key: 'thresholdQuantity', align: 'center' },
    { 
      header: 'Expiry Date', 
      key: 'expiryDate',
      render: (row) => formatDate(row.expiryDate)
    },
    { 
      header: 'Status', 
      key: 'status',
      align: 'center',
      render: (row) => getStatusBadge(row.status)
    },
    { header: 'Seller ID', key: 'sellerId' }
  ];

  const dataWithRowClass = batches.map(batch => ({
    ...batch,
    _rowClass: getRowClass(batch)
  }));

  return (
    <div className="inventory-page fade-in">
      <div className="page-header">
        <div>
          <h2>Inventory Management</h2>
          <p className="page-subtitle">Batch-wise inventory control with FEFO tracking</p>
        </div>
        <button 
          className="btn-primary"
          onClick={() => setShowAddForm(!showAddForm)}
        >
          {showAddForm ? '− Close Form' : '+ Add New Batch'}
        </button>
      </div>

      {/* Add Batch Form */}
      {showAddForm && (
        <div className="add-batch-form">
          <h3>Add New Inventory Batch</h3>
          <form onSubmit={handleSubmit}>
            <div className="form-grid">
              <div className="form-group">
                <label>Product ID *</label>
                <input
                  type="number"
                  name="productId"
                  value={formData.productId}
                  onChange={handleInputChange}
                  placeholder="e.g., P001"
                  required
                />
              </div>

              <div className="form-group">
                <label>Batch Number *</label>
                <input
                  type="text"
                  name="batchNo"
                  value={formData.batchNo}
                  onChange={handleInputChange}
                  placeholder="e.g., B2024-001"
                  required
                />
              </div>

              <div className="form-group">
                <label>Quantity *</label>
                <input
                  type="number"
                  name="quantity"
                  value={formData.quantity}
                  onChange={handleInputChange}
                  placeholder="e.g., 100"
                  min="1"
                  required
                />
              </div>

              <div className="form-group">
                <label>Threshold Quantity *</label>
                <input
                  type="number"
                  name="thresholdQuantity"
                  value={formData.thresholdQuantity}
                  onChange={handleInputChange}
                  placeholder="e.g., 100"
                  min="1"
                  required
                />
              </div>

              <div className="form-group">
                <label>Expiry Date *</label>
                <input
                  type="date"
                  name="expiryDate"
                  value={formData.expiryDate}
                  onChange={handleInputChange}
                  required
                />
              </div>

              <div className="form-group">
                <label>Seller ID *</label>
                <input
                  type="text"
                  name="sellerId"
                  value={formData.sellerId}
                  onChange={handleInputChange}
                  placeholder="e.g., S001"
                  required
                />
              </div>
            </div>

            <div className="form-actions">
              <button 
                type="button" 
                className="btn-secondary"
                onClick={handleReset}
                disabled={submitting}
              >
                Reset
              </button>
              <button 
                type="submit" 
                className="btn-primary"
                disabled={submitting}
              >
                {submitting ? 'Adding...' : 'Add Batch'}
              </button>
            </div>
          </form>
        </div>
      )}

      {/* Inventory Table */}
      <div className="inventory-table">
        <DataTable
          columns={columns}
          data={dataWithRowClass}
          loading={loading}
          emptyMessage="No inventory batches found"
        />
      </div>
    </div>
  );
};

export default Inventory;