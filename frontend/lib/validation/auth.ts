import { z } from "zod";

// Login validation schema
export const loginValidationSchema = z.object({
  email: z.string().email({ message: "Invalid email address" }),
  password: z
    .string()
    .min(6, { message: "Password must be at least 6 characters" }),
  role: z.enum(["student", "teacher"], {
    message: "Role must be either 'student' or 'teacher'",
  }),
});

// Type for login form
export type LoginValidationSchema = z.infer<typeof loginValidationSchema>;

// Function to validate login data
export function validateLoginData(data: any) {
  try {
    loginValidationSchema.parse(data);
    return { success: true, errors: null };
  } catch (error: any) {
    const errors = error.errors.map((err: any) => ({
      field: err.path[0],
      message: err.message,
    }));
    return { success: false, errors };
  }
}

export const signupValidationSchema = z.object({
  first_name: z.string().min(1, { message: "First name is required" }),
  last_name: z.string().min(1, { message: "Last name is required" }),
  email: z.string().email({ message: "Invalid email address" }),
  password: z
    .string()
    .min(6, { message: "Password must be at least 6 characters" }),
  phone_number: z.string().regex(/^\d{9,10}$/, {
    message: "Phone number must be between 9-10 digits",
  }),
  role: z.enum(["student", "teacher"], {
    message: "Role must be either 'student' or 'teacher'",
  }),
});

// Optional: Extend schema with confirm password
export const signupWithConfirmPasswordSchema = signupValidationSchema
  .extend({
    confirm_password: z
      .string()
      .min(6, { message: "Please confirm your password" }),
  })
  .refine((data) => data.password === data.confirm_password, {
    message: "Passwords do not match",
    path: ["confirm_password"],
  });

export type SignupValidationSchema = z.infer<
  typeof signupWithConfirmPasswordSchema
>;

const desiredOrder = [
  "first_name",
  "last_name",
  "email",
  "password",
  "confirm_password",
  "phone_number",
  "role",
];

// Function to validate signup data
export function validateSignupData(data: any) {
  try {
    signupWithConfirmPasswordSchema.parse(data);
    return { success: true, errors: null };
  } catch (error: any) {
    const errors = error.errors.map((err: any) => ({
      field: err.path[0],
      message: err.message,
    }));
    const sortedErrors = errors.sort(
      (a: any, b: any) =>
        desiredOrder.indexOf(a.field) - desiredOrder.indexOf(b.field)
    );

    return { success: false, errors: sortedErrors };
  }
}
