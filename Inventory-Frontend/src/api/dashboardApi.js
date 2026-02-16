import axios from "axios";
import { alertsAPI } from "./alertApi";

const DASHBOARD_BASE_URL = "http://localhost:8080/dashboard"; 

export const dashboardAPI = {

  /* KPI CARDS */
  getStats: async () => {
    const response = await axios.get(`${DASHBOARD_BASE_URL}/overview`);
    return response.data;
  },

  /* BAR CHART */
  getStockByProduct: async () => {
    const response = await axios.get(`${DASHBOARD_BASE_URL}/stock-by-product`);
    return response.data;
  },

  /* LINE CHART */
  getExpiryTrend: async () => {
    const response = await axios.get(`${DASHBOARD_BASE_URL}/expiry-trend`);
    return response.data;
  },

  getRecentAlerts: async () => {
    return alertsAPI.getRecentAlerts();
  }
  
};
