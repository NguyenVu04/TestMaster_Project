'use server'
import { cookies } from "next/headers";
import { signIn } from "@/lib/auth";

export async function authenticate(username: string, password: string) {
    try {
        await cookies();
        const r = await signIn('credentials', {
            username: username,
            password: password,
            redirect: false
        });
        return r;
    } catch (error) {
        if ((error as any).type === "InvalidEmailPasswordError") {
            return {
                error: (error as any).type,
                code: 1
            }
        }
        else if ((error as any).type === "InactiveAccountError") {
            return {
                error: (error as any).type,
                code: 2
            }
        }
        else {
            return {
                error: "Internal Server Error",
                code: 0
            }
        }
    }
}