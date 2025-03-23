"use client"; // This is a Client Component

import { signOut } from "next-auth/react";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function LogoutPage() {
  const router = useRouter();

  useEffect(() => {
    // Sign out the user
    signOut({ redirect: false }).then(() => {
      // Redirect to the login page after signing out
      router.push("/auth/login");
    });
  }, [router]);

  return (
    <div style={{ textAlign: "center", marginTop: "50px" }}>
      <h1 style={{ fontSize: "2rem", color: "#333" }}>Logging out...</h1>
      <p style={{ fontSize: "1.2rem", color: "#555" }}>Please wait while we log you out.</p>
    </div>
  );
}