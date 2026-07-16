import axios from "axios";

export const userApi = axios.create({
  baseURL: import.meta.env.VITE_USER_SERVICE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

export const jobApi = axios.create({
  baseURL: import.meta.env.VITE_JOB_SERVICE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

export const resumeApi = axios.create({
  baseURL: "http://localhost:8080",
  headers: {
    "Content-Type": "application/json",
  },
});


const addToken = config => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
};
userApi.interceptors.request.use(addToken);

jobApi.interceptors.request.use(addToken);

resumeApi.interceptors.request.use(addToken);