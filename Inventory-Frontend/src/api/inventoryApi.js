import axios from "axios";

const INVENTORY_BASE_URL = "http://localhost:8080/inventory";

export const inventoryAPI = {
  addBatch: async (data) => {
    try {
      const res = await axios.post(
        `${INVENTORY_BASE_URL}/add-batch`,
        data
      );
      return res.data;
    } catch (err) {
      throw new Error(
        err.response?.data?.message || "Failed to add batch"
      );
    }
  },

  getAllBatches: async () => {
    try{
      const res = await axios.get(
        `${INVENTORY_BASE_URL}/AllBatches`,
      );
      return res.data;
    }catch (err){
      throw new Error("Failed to fetch batches");
    }
  },

  getBatchesByProduct: async (productId) => {
    try {
      const res = await axios.get(
        `${INVENTORY_BASE_URL}/batches-by-product`,
        { params: { productId } }
      );
      return res.data;
    } catch (err) {
      throw new Error("Failed to fetch batches of given product ID");
    }
  },

  reduceStock: async (data) => {
    try {
      const res = await axios.put(
        `${INVENTORY_BASE_URL}/reduce-stock`,
        data
      );
      return res.data;
    } catch (err) {
      throw new Error(
        err.response?.data?.message || "Insufficient stock"
      );
        }
},  
    getExpiringSoon: async () => {
        try{
            const res = await axios.get(
                `${INVENTORY_BASE_URL}/expiring`,
                {params : {days}}
            );
            return res.data;
        } catch(err){
            throw new Error("Failed to fetch expiring soon batches");
        }
    },

    getStockSummary: async () => { 
        try{
            const res = await axios.get(
                `${INVENTORY_BASE_URL}/summary`,
                {params : {productId}}
            );
            return res.data;
        } catch(err){
            throw new Error("Failed to fetch Stock Summary");
        }
    },
};


