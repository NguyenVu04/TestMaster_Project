
import { auth } from "@/app/auth/auth";
import { SessionProvider } from "next-auth/react";
import React from "react";

async function page() {
  const session = await auth();
  
  
  if(!session) {
    return null;
  }
  const user = session?.user;
  console.log("User:", user);
  console.log("Session:", session);
  const userInfo = {
    name: user?.username,
    email: user?.email,
    role: user?.role
  };


  return (
    <SessionProvider session={session}>
      <div style={{ textAlign: "center", marginBottom: "20px" }}>
        <h1 style={{ fontSize: "2.5rem", color: "#4CAF50", margin: "0" }}>This is a Client Page</h1>
        <h2 style={{ fontSize: "1.8rem", color: "#555", margin: "10px 0" }}>My Account</h2>
      </div>
      <div style={{ fontFamily: "Arial, sans-serif", lineHeight: "1.6", color: "#333" }}>
        <p>
          <strong>Email:</strong> {user?.email || "N/A"}
        </p>
        <p>
          <strong>Role:</strong> {user?.role || "N/A"}
        </p>
        <p>
          <strong>Username:</strong> {user?.username || "N/A"}
        </p>
      </div>
    </SessionProvider>
  );
}

export default page;