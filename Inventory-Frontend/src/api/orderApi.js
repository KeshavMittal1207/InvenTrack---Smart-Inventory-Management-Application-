import axios from "axios";

const ORDER_BASE_URL = "http://localhost:8080/order";

export const ordersAPI = {
  placeOrder: async (formData) => {
    try {
      const res = await axios.post(
        `${ORDER_BASE_URL}/placeOrder`,
        formData
      );
      return res.data;
    } catch (err) {
      throw new Error(
        err.response?.data?.message || "Order failed"
      );
    }
  },

  getAllOrders: async () => {
    try {
      const res = await axios.get(
        `${ORDER_BASE_URL}/getOrders`
      );
      return res.data;
    } catch (err) {
      throw new Error("Failed to fetch orders");
    }
  },
};
