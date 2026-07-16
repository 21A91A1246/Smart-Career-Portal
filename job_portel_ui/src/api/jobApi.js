import { jobApi } from "./axios";

export const getAllJobs = async () => {
  const response = await jobApi.get(
    "/api/jobs"
  );

  return response.data;
};

export const getJobById = async (
  id
) => {
  const response = await jobApi.get(
    `/api/jobs/${id}`
  );

  return response.data;
};

export const createJob = async (
  jobData
) => {
  const response = await jobApi.post(
    "/api/jobs",
    jobData
  );

  return response.data;
};

export const updateJob = async (
  id,
  jobData
) => {
  const response = await jobApi.put(
    `/api/jobs/${id}`,
    jobData
  );

  return response.data;
};

export const deleteJob = async (
  id
) => {
  return await jobApi.delete(
    `/api/jobs/${id}`
  );
};

export const getRecruiterJobs =
  async (recruiterId) => {
    const response =
      await jobApi.get(
        `/api/jobs/recruiter/${recruiterId}`
      );

    return response.data;
};

export const searchJobs = async (
  keyword
) => {
  const response = await jobApi.get(
    `/api/jobs/search?keyword=${keyword}`
  );

  return response.data;
};

export const applyForJob = async (
  applicationData
) => {
  const response = await jobApi.post(
    "/api/applications/apply",
    applicationData
  );

  return response.data;
};

export const getUserApplications =
  async (userId) => {
    const response = await jobApi.get(
      `/api/applications/user/${userId}`
    );

    return response.data;
  };

export const getJobApplications =
  async (jobId) => {
    const response = await jobApi.get(
      `/api/applications/job/${jobId}`
    );

    return response.data;
};

export const uploadResume =
  async (file) => {
    const formData =new FormData();
    formData.append("file",file);
    const response =
      await jobApi.post(
        "/api/applications/upload-resume",
        formData,
        {
          headers: {
            "Content-Type":
              "multipart/form-data"
          }
        }
      );

    return response.data;
};