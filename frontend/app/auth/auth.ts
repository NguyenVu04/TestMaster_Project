import NextAuth, { User } from "next-auth";
import Credentials from "next-auth/providers/credentials";

// Extend the User type to include custom properties
declare module "next-auth" {
  interface User {
    username?: string;
    role?: string;
  }

  interface Session {
    user: {
      email?: string;
      id?: string;
      username?: string;
      role?: string;
    };
  }
}

export const { handlers, signIn, signOut, auth } = NextAuth({
    providers: [
        Credentials({
            credentials: {
                username:{},
                email: {},
                password: {},
                role: {}
            },

            authorize: async (credentials, request) => {
                const user = {
                    username: credentials?.username as string,
                    email: credentials?.email as string,
                    password: credentials?.password as string,
                    role: credentials?.role as string,
                };
                console.log("Credentials:", credentials);
                if (credentials?.email === user.email && credentials?.password === user.password) {
                    console.log("User:", user);
                    return user;
                } else {
                    return null;
                }
            }
        })
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
        }
        return token;
      },
      // Add custom properties to the session object
      session: async ({ session, token }) => {
        if (token) {
          session.user.id = token.id as string; // Include the user ID
          session.user.username = token.username as string; // Include the username
          session.user.role = token.role as string; // Include the role
        }
        return session;
      },
    },
    secret: process.env.NEXTAUTH_SECRET,
    trustHost: true,
})