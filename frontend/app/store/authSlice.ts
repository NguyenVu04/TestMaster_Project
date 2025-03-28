import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface AuthState {
  role: "teacher" | "student" | "admin" | null;
  email: string | null;
  userId: string | null;
}

const initialState: AuthState = {
  role: null,
  email: null,
  userId: null,
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
      }>
    ) => {
      state.role = action.payload.role;
      state.email = action.payload.email;
      state.userId = action.payload.userId;
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
