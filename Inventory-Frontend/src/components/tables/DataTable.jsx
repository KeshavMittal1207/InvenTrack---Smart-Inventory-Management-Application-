import React from 'react';
import './DataTable.css';

const DataTable = ({ 
  columns, 
  data, 
  loading = false, 
  emptyMessage = 'No data available',
  onRowClick 
}) => {
  if (loading) {
    return (
      <div className="table-container">
        <div className="table-loading">
          <div className="loading-spinner"></div>
          <p>Loading data...</p>
        </div>
      </div>
    );
  }

  if (!data || data.length === 0) {
    return (
      <div className="table-container">
        <div className="table-empty">
          <span className="empty-icon">📭</span>
          <p>{emptyMessage}</p>
        </div>
      </div>
    );
  }

  return (
    <div className="table-container">
      <div className="table-wrapper">
        <table className="data-table">
          <thead>
            <tr>
              {columns.map((column, index) => (
                <th 
                  key={index} 
                  style={{ width: column.width }}
                  className={column.align ? `text-${column.align}` : ''}
                >
                  {column.header}
                </th>
              ))}
            </tr>
          </thead>
          <tbody>
            {data.map((row, rowIndex) => (
              <tr 
                key={rowIndex}
                onClick={() => onRowClick && onRowClick(row)}
                className={onRowClick ? 'clickable' : ''}
              >
                {columns.map((column, colIndex) => (
                  <td 
                    key={colIndex}
                    className={column.align ? `text-${column.align}` : ''}
                  >
                    {column.render ? column.render(row) : row[column.key]}
                  </td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default DataTable;