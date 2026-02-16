import axios from "axios";

const ALERT_BASE_URL = "http://localhost:8080/alert";

export const alertsAPI = {
  getAllAlerts: async () => {
    try {
      const res = await axios.get(
        `${ALERT_BASE_URL}/getAlert`
      );
      return res.data;
    } catch (err) {
      throw new Error("Failed to fetch alerts");
    }
  },

  getAlertsByType: async (type) => {
    try {
      const res = await axios.get(
        `${ALERT_BASE_URL}/getAlert/${type}`
      );
      return res.data;
    } catch (err) {
      throw new Error("Failed to fetch alerts by type");
    }
  },

  deleteAlert: async (alertId) => {
    try {
      const res = await axios.delete(
        `${ALERT_BASE_URL}/deleteAlert/${alertId}`
      );
      return res.data;
    } catch (err) {
      throw new Error("Failed to delete alert");
    }
  },

  getRecentAlerts: async () => {
    try{
      const res = await axios.get(
        `${ALERT_BASE_URL}/recent`
      );
      console.log(res.data);
      return res.data;
    } catch(err){
      throw new Error("Failed to fetch recent alerts");
    }
  }
};
