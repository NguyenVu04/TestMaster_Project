"use client"; // This is a Client Component

import { useRouter } from "next/navigation";

export default function SignOutButton() {
  const router = useRouter();

  const handleSignOut = () => {
    // confirm("Are you sure you want to sign out?") &&

    // Ask the user for confirmation
    const confirmSignOut = window.confirm("Are you sure you want to sign out?");
    if (confirmSignOut) {
      router.push("/auth/logout"); // Redirect to the logout page
    }
  };

  return (
    <button
      onClick={handleSignOut}
      style={{
        padding: "10px 20px",
        backgroundColor: "#ff4444",
        color: "#fff",
        border: "none",
        borderRadius: "5px",
        cursor: "pointer",
        fontSize: "1rem",
      }}
    >
      Sign Out
    </button>
  );
}