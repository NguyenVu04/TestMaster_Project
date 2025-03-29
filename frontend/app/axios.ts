import axios from "axios";

const request = axios.create({
  baseURL: "https://02ea-171-253-40-101.ngrok-free.app",
  headers: {
    "Content-Type": "application/json",
    "Accept": "application/json",
    "ngrok-skip-browser-warning": "true" // Add this for ngrok specifically
  },
  withCredentials: true // If you need to send cookies
});

// Add request interceptor to handle errors
request.interceptors.request.use(
  (config) => {
    // You can modify the config here if needed
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Add response interceptor to handle errors
request.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      console.error("API Error:", error.response.status, error.response.data);
    } else if (error.request) {
      console.error("No response received:", error.request);
    } else {
      console.error("Request setup error:", error.message);
    }
    return Promise.reject(error);
  }
);
export const get = async (path: string, options = {}) => {
  const api = await request.get(path, options);
  return api;
};

export const post = async (path: string, data = {}, options = {}) => {
  const api = await request.post(path, data, options);
  return api;
};

export const put = async (path: string, data = {}, options = {}) => {
  const api = await request.put(path, data, options);
  return api;
};

export const del = async (path: string, options = {}) => {
  const api = await request.delete(path, options);
  return api;
};

export const patch = async (path: string, data = {}, options = {}) => {
  const api = await request.patch(path, data, options);
  return api;
};
export default request;
