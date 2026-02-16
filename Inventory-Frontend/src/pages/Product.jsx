import React, { useState, useEffect } from 'react';
import { useApp } from '../context/AppContext';
import { productAPI } from '../api/productApi';
import DataTable from '../components/tables/DataTable';
import './Product.css';

const Product = () => {
  const { addNotification } = useApp();
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showAddForm, setShowAddForm] = useState(false);
  const [formData, setFormData] = useState({
    name: '',
    category: '',
    sku: ''
    });
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      setLoading(true);
      
      const response = await productAPI.getAllProducts();
      const normalizedProducts = response.map((product) => ({
        productId: product.productId,
        name: product.name,
        category: product.category,
        sku: product.sku,
        status: product.status,
      }))

      setProducts(normalizedProducts);
      setLoading(false);

    } catch (error) {
      addNotification(error.message || 'Failed to load product', 'danger');
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
    
    if (!formData.name || !formData.category || !formData.sku) {
      addNotification('Please fill in all required fields', 'warning');
      return;
    }

    try {
      setSubmitting(true);

      await productAPI.addProduct(formData);

      addNotification('Product added successfully!', 'success');
      setShowAddForm(false);
      fetchProducts();

      setFormData({
        name: '',
        category: '',
        sku: ''
      });
    
    } catch (error) {
      addNotification(error.message || 'Failed to add Product', 'danger');
    } finally {
      setSubmitting(false);
    }
  };

  const handleReset = () => {
    setFormData({
        name: '',
        category: '',
        sku: ''
      });
  };

  const getStatusBadge = (status) => {
    const statusMap = {
      ACTIVE: 'Active',
      DISCONTINUED: 'Discontinued',
    };
    return <span className={`status-badge status-${statusMap[status]}`}>{status}</span>;
  };

  const getRowClass = (product) => {
    if (product.status === 'DISCONTINUED') return 'row-danger';
    return '';
  };

  const columns = [
    { header: 'Product ID', key: 'productId' , align: 'center'},
    {header: 'Name' , key: 'name' , align: 'center'},
    { header: 'Category', key: 'category', align: 'center' },
    { header: 'Sku', key: 'sku', align: 'center' },
    { 
      header: 'Status', 
      key: 'status',
      align: 'center',
      render: (row) => getStatusBadge(row.status)
    }
  ];

  const dataWithRowClass = products.map(product => ({
    ...product,
    _rowClass: getRowClass(product)
  }));

  return (
    <div className="product-page fade-in">
      <div className="page-header">
        <div>
          <h2>Product Management</h2>
          {/* <p className="page-subtitle">Batch-wise inventory control with FEFO tracking</p> */}
        </div>
        <button 
          className="btn-primary"
          onClick={() => setShowAddForm(!showAddForm)}
        >
          {showAddForm ? '− Close Form' : '+ Add New Product'}
        </button>
      </div>

      {/* Add Product Form */}
      {showAddForm && (
        <div className="add-product-form">
          <h3>Add New Product</h3>
          <form onSubmit={handleSubmit}>
            <div className="form-grid">

              <div className="form-group">
                <label>Name *</label>
                <input
                  type="text"
                  name="name"
                  value={formData.name}
                  onChange={handleInputChange}
                  placeholder="e.g., XYZ"
                  required
                />
              </div>

              <div className="form-group">
                <label>Category *</label>
                <input
                  type="text"
                  name="category"
                  value={formData.category}
                  onChange={handleInputChange}
                  placeholder="e.g., ABC"
                  required
                />
              </div>

              <div className="form-group">
                <label>Sku *</label>
                <input
                  type="text"
                  name="sku"
                  value={formData.sku}
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
                {submitting ? 'Adding...' : 'Add Product'}
              </button>
            </div>
          </form>
        </div>
      )}

      <div className="product-table">
        <DataTable
          columns={columns}
          data={dataWithRowClass}
          loading={loading}
          emptyMessage="No products found"
        />
      </div>
    </div>
  );
};

export default Product;