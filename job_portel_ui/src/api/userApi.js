import { userApi } from "./axios";

export const registerUser = async (userData) => {
  const response = await userApi.post(
    "/api/users/register",
    userData
  );

  return response.data;
};

export const loginUser = async (loginData) => {
  const response =
    await userApi.post(
      "/api/users/login",
      loginData
    );

  return response.data;
};

export const getAllUsers = async () => {
  const response = await userApi.get(
    "/api/users"
  );

  return response.data;
};

export const getUserById = async (id) => {
  const response = await userApi.get(
    `/api/users/${id}`
  );

  return response.data;
};

export const createProfile = async (userId,profileData) => {
  const response = await userApi.post(
    `/api/users/${userId}/profile`,
    profileData
  );

  return response.data;
};

export const getProfile = async (userId) => {
  const response = await userApi.get(`/api/users/${userId}/profile`);
  return response.data;
};

export const updateProfile = async (
  userId,
  profileData
) => {
  const response = await userApi.put(
    `/api/users/${userId}/profile`,
    profileData
  );

  return response.data;
};