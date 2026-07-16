// api/resumeApi.js

import { resumeApi } from "./axios";

export const generateResume = async (
  profileId,
  jobId
) => {
  const response = await resumeApi.post(
    "/api/v1/resumes/generate",
    {
      profileId,
      jobId,
    }
  );

  return response.data;
};