import { auth } from "@/app/auth/auth";
import { SessionProvider } from "next-auth/react";
import SignOutButton from "../logout/signOutButton"; // Import the Client Component
import { redirect } from "next/navigation";

async function page() {
  const session = await auth();

  if (!session) {
    return null;
  } else return redirect(`${session?.user?.role}`);

  const user = session?.user;
  console.log("User:", user);
  console.log("Session:", session);

  return (
    <SessionProvider session={session}>
      <div style={{ textAlign: "center", marginBottom: "20px" }}>
        <h1 style={{ fontSize: "2.5rem", color: "#4CAF50", margin: "0" }}>
          This is a Server Page
        </h1>
        <h2 style={{ fontSize: "1.8rem", color: "#555", margin: "10px 0" }}>
          My Account
        </h2>
      </div>
      <div
        style={{
          fontFamily: "Arial, sans-serif",
          lineHeight: "1.6",
          color: "#333",
        }}
      >
        <p>
          <strong>Email:</strong> {user?.email || "N/A"}
        </p>
        <p>
          <strong>Role:</strong> {user?.role || "N/A"}
        </p>
        <p>
          <strong>Username:</strong> {user?.username || "N/A"}
        </p>
        <p>
          <strong>AccessToken: </strong> {user?.accessToken || "N/A"}
        </p>
        <p>
          <strong>Expires in:</strong> {session.expires || "N/A"}
        </p>
      </div>
      <div style={{ textAlign: "center", marginTop: "20px" }}>
        {/* Use the Client Component */}
        <SignOutButton />
      </div>
    </SessionProvider>
  );
}

export default page;
