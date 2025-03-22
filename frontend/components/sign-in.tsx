"use client"
import { authenticate } from "@/utils/actions";
import { useRouter } from "next/navigation";
 
export function SignIn() {
  const router = useRouter();
  const credentialsAction = async (values: any) => {
    const {username, password} = values;
    const response = await authenticate(username, password);
    if (response?.error) {
      // error
      console.log('eror', response);
    }
    else {
        // redirect to /dashboard
        router.push('/');
    }
    return response;
  }
 
  return (
    <form action={credentialsAction}>
      <label htmlFor="credentials-email">
        Email
        <input type="email" id="credentials-email" name="email" />
      </label>
      <label htmlFor="credentials-password">
        Password
        <input type="password" id="credentials-password" name="password" />
      </label>
      <input type="submit" value="Sign In" />
    </form>
  )
}