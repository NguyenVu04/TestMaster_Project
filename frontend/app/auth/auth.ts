import NextAuth, { User } from "next-auth";
import Credentials from "next-auth/providers/credentials";
import * as axiosReq from '@/app/axios';
// Extend the User type to include custom properties
declare module "next-auth" {
  interface User {
    username?: string;
    role?: string;
    accessToken?: string;
  }

  interface Session {
    user: {
      email?: string;
      id?: string;
      username?: string;
      role?: string;
      accessToken?: string;
    };
  }
}

export const { handlers, signIn, signOut, auth } = NextAuth({
  providers: [
    Credentials({
      credentials: {
        username: {},
        email: {},
        password: {},
        role: {},
      },

      authorize: async (credentials, request) => {
        // console api
        console.log(
          ">>>>>>" +
            `https://02ea-171-253-40-101.ngrok-free.app/api/auth/signin/${credentials?.role}`
        );
        // const response = await fetch(
        //   `https://02ea-171-253-40-101.ngrok-free.app/api/auth/signin/${credentials?.role}`,
        //   {
        //     method: "POST",
        //     body: JSON.stringify({
        //       email: credentials?.email,
        //       password: credentials?.password,
        //     }),
        //     headers: {
        //       "Content-Type": "application/json",
        //     },
        //   }
        // );
        const response = await axiosReq.post(
          `/api/auth/signin/${credentials?.role}`,
          {
            email: credentials?.email,
            password: credentials?.password,
          })
        console.log("Credentials:", credentials);
        console.log("Response:", response);
        
        let data = null;
        if (response.data.accessToken) {
          data = response.data;
        } 
        else {
          throw new Error("Invalid credentials");
        }

        console.log("Data:", data);

        return data;
      },
    }),
  ],
  pages: {
    signIn: "/auth/login",
    signOut: "/auth/logout",
  },
  callbacks: {
    authorized: async ({ auth }) => {
      return !!auth;
    },
    // Add custom properties to the JWT token
    jwt: async ({ token, user }) => {
      if (user) {
        token.id = user.id; // Include the user ID
        token.username = user.username; // Include the username
        token.role = user.role; // Include the role
        token.accessToken = user.accessToken; // Include the access token
      }
      return token;
    },
    // Add custom properties to the session object
    session: async ({ session, token }) => {
      if (token) {
        session.user.id = token.id as string; // Include the user ID
        session.user.username = token.username as string; // Include the username
        session.user.role = token.role as string; // Include the role
        session.user.accessToken = token.accessToken as string; // Include the access token
      }
      return session;
    },
  },
  secret: process.env.NEXTAUTH_SECRET,
  trustHost: true,
});
