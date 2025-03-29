import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface AuthState {
  role: "teacher" | "student" | "admin" | null;
  email: string | null;
  userId: string | null;
  accessToken: string | null;
}

const initialState: AuthState = {
  role: null,
  email: null,
  userId: null,
  accessToken: null,
};

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    login: (
      state,
      action: PayloadAction<{
        role: "teacher" | "student" | "admin";
        email: string;
        userId: string;
        accessToken: string;
      }>
    ) => {
      state.role = action.payload.role;
      state.email = action.payload.email;
      state.userId = action.payload.userId;
      state.accessToken = action.payload.accessToken;
    },
    logout: (state) => {
      state.role = null;
      state.email = null;
      state.userId = null;
    },
  },
});

export const { login, logout } = authSlice.actions;
export default authSlice.reducer;
