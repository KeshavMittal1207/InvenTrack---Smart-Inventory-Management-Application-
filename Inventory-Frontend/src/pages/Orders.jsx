import React, { useState, useEffect } from 'react';
import { useApp } from '../context/AppContext';
import { ordersAPI } from '../api/orderApi';
import DataTable from '../components/tables/DataTable';
import './Orders.css';

const Orders = () => {
  const { addNotification } = useApp();
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [formData, setFormData] = useState({
    productId: '',
    quantity: ''
  });
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    fetchOrders();
  }, []);

  const fetchOrders = async () => {
    try {
      setLoading(true);
      
      const response = await ordersAPI.getAllOrders();
      const normalizaedOrders = response.map((order) => ({
        orderId: order.orderId,
        productId: order.productId,
        quantity: order.quantity,
        orderDate: order.orderDate
      }));

      setOrders(normalizaedOrders);
      setLoading(false);

    } catch (error) {
      addNotification(error.message || 'Failed to load orders', 'danger');
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
    
    if (!formData.productId || !formData.quantity) {
      addNotification('Please fill in all required fields', 'warning');
      return;
    }

    try {
      setSubmitting(true);
      await ordersAPI.placeOrder(formData);
      addNotification('Order placed successfully!', 'success');
      setFormData({
        productId: '',
        quantity: ''
      });
      fetchOrders();

    } catch (error) {
      addNotification(error.message || 'Failed to place order', 'danger');
    } finally {
      setSubmitting(false);
    }
  };

  // const getStatusBadge = (status) => {
  //   const statusMap = {
  //     COMPLETED: 'active',
  //     PENDING: 'pending',
  //     CANCELLED: 'inactive'
  //   };
  //   return <span className={`status-badge status-${statusMap[status]}`}>{status}</span>;
  // };

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' });
  };

  const columns = [
    { header: 'Order ID', key: 'orderId' },
    { header: 'Product ID', key: 'productId' },
    { header: 'Quantity', key: 'quantity', align: 'center' },
    { 
      header: 'Order Date', 
      key: 'orderDate',
      render: (row) => formatDate(row.orderDate)
    },
    // { 
    //   header: 'Status', 
    //   key: 'status',
    //   align: 'center',
    //   render: (row) => getStatusBadge(row.status)
    // }
  ];

  return (
    <div className="orders-page fade-in">
      <div className="page-header">
        <div>
          <h2>Orders Management</h2>
          <p className="page-subtitle">Place orders and track sales & stock consumption</p>
        </div>
      </div>

      <div className="orders-content">
        {/* Place Order Form */}
        <div className="place-order-card">
          <h3>Place New Order</h3>
          <form onSubmit={handleSubmit} className="order-form">
            <div className="form-group">
              <label>Product ID *</label>
              <input
                type="text"
                name="productId"
                value={formData.productId}
                onChange={handleInputChange}
                placeholder="e.g., P001"
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
                placeholder="Enter quantity"
                min="1"
                required
              />
            </div>

            <button 
              type="submit" 
              className="btn-primary"
              disabled={submitting}
            >
              {submitting ? 'Placing Order...' : 'Place Order'}
            </button>
          </form>
        </div>

        {/* Orders History */}
        <div className="orders-history">
          <h3>Order History</h3>
          <DataTable
            columns={columns}
            data={orders}
            loading={loading}
            emptyMessage="No orders found"
          />
        </div>
      </div>
    </div>
  );
};

export default Orders;