import NextAuth from "next-auth"
import { ZodError } from "zod"
import Credentials from "next-auth/providers/credentials"
import { signInSchema } from "./zod"
// Your own logic for dealing with plaintext password strings; be careful!
import { getUserFromDb } from "@/app/utils/db" 
 

declare module "next-auth" {
    /**
     * Returned by `useSession`, `getSession` and received as a prop on the `SessionProvider` React Context
     */
    interface Session {
        idToken?: string;
        user: {
            name: string;
            email: string;
            role: string;
        };
    }


}
export const { handlers, signIn, signOut, auth } = NextAuth({
  providers: [
    Credentials({
      // You can specify which fields should be submitted, by adding keys to the `credentials` object.
      // e.g. domain, username, password, 2FA token, etc.
      credentials: {
        email: {label: "Email", type: "email"},
        password: {label: "Password", type: "password"},
      },
authorize: async (credentials) => {
  try {
    const { email, password } = await signInSchema.parseAsync(credentials)

    // logic to verify if the user exists
    const user = await getUserFromDb(email, password)

    if (!user) {
      throw new Error("Invalid credentials.")
    }

    // return JSON object with the user data
    return {
        userId: user.userId,
        username: user.username,
        email: user.email,
        role: user.role,
    }
} catch (error) {
    if (error instanceof ZodError) {
      // Return `null` to indicate that the credentials are invalid
      return null // Explicitly return null for invalid credentials
    }
    throw error // Re-throw other errors
  }
},
    }),
  ],
  pages: {
    signIn: "/auth/signin",
    signOut: "/auth/signout",
  }
    , callbacks: {
        session: async({ session, token }) => {
            session.idToken = token.idToken as string;
            session.user.role = token.role as string;
            return session;
        }
    }
})