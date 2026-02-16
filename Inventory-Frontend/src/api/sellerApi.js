import axios from "axios";

const SELLER_BASE_URL = "http://localhost:8080/seller";

export const sellersAPI = {
  getAllSellers: async () => {
    try {
      const res = await axios.get(
        `${SELLER_BASE_URL}/getAllSellers`
      );
      return res.data;
    } catch (err) {
      throw new Error("Failed to fetch sellers");
    }
  },

  getSellerById: async (sellerId) => {
    try {
      const res = await axios.get(
        `${SELLER_BASE_URL}/getSeller/${sellerId}`
      );
      return res.data;
    } catch (err) {
      throw new Error("Seller not found");
    }
  },

  addSeller: async (data) => {
    try {
      const res = await axios.post(
        `${SELLER_BASE_URL}/addSeller`,
        data
      );
      return res.data;
    } catch (err) {
      throw new Error("Failed to add seller");
    }
  },

  toggleSellerStatus: async (sellerId) => {
    try {
      const res = await axios.patch(
        `${SELLER_BASE_URL}/toggle-status/${sellerId}`
      );
      return res.data;
    } catch (err) {
      throw new Error("Failed to update seller status");
    }
  },
};
