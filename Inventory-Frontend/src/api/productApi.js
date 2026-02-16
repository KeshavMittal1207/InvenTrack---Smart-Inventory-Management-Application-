import axios from "axios";

const PRODUCT_BASE_URL = "http://localhost:8080/product";

export const productAPI = {
    addProduct: async (formData) => {
        try{
            const res = await axios.post(`${PRODUCT_BASE_URL}/add-product` , formData);
            return res.data;
        }catch(err){
            throw new Error(err.response?.data?.message || "Failed to add Product");
            
        }
    },
    getAllProducts: async () => {
        try{
            const res = await axios.get(`${PRODUCT_BASE_URL}/get-all-products`);
            return res.data;
        }catch(err){
            throw new Error("Failed to fetch Products")
        }
    },

};