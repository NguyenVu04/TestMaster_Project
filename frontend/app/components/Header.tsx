"use client";

import { auth } from "@/app/auth/auth";
import Logo from "@/public/Logo.png";
import { useSession } from "next-auth/react";
import Image from "next/image";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

function Header() {
  // const [session, setSession] = useState<null | any>(null);
  // useEffect(() => {
  //   async function fetchSession() {
  //     const session = await auth();
  //     setSession(null);

  //     console.log("Session:", session);
  //   }
  //   fetchSession();
  // }, []);

  const role = window.localStorage.getItem("role");

  const session = useSession();
  console.log("Session:", session);

  // if (session.status === "loading") {
  //   return <div>Loading...</div>;
  // }
  // if (session.status === "unauthenticated") {
  //   return <div>Please sign in</div>;
  // }
  // if (session.status === "authenticated") {
  //   console.log("User:", session.data.user);
  // }
  const userInfo = null;

  return (
    <div
      className="bg-white fixed top-0 w-full z-50 shadow-md"
      // style={{ backgroundColor: "#1A202C" }}
    >
      <nav className="container mx-auto flex justify-between items-center py-4 ">
        <div className="flex-1">
          <Link href="/">
            <Image
              src={Logo}
              alt="Logo"
              width={100}
              height={100}
              className="transition-transform duration-300 ease-in-out hover:scale-120"
            />
          </Link>
        </div>
        <ul className="flex justify-between items-center flex-1 gap-8 text-[#E0E0E0]">
          <li className="flex items-center">
            <Link className="inline-block w-full p-4 hover:text-gray-400" href={`/${role}`}>
              Home
            </Link>
          </li>
          <li className="flex items-center">
            <a className="inline-block w-full p-4 hover:text-gray-400" href="#">
              Features
            </a>
          </li>
          <li className="flex items-center">
            <Link className="inline-block w-full p-4 hover:text-gray-400" href={`/${role}/profile`}>
              Your profile
            </Link>
          </li>
          <li className="flex items-center">
            {userInfo ? (
              <div>
                {/* <p className="text-black">Hello student: {userInfo?.email}</p> */}

                <Link
                  className="inline-block w-full hover:text-gray-400"
                  href="/"
                >
                  Logout
                </Link>
              </div>
            ) : (
              <Link
                className="inline-block w-full py-4 px-6 border border-[#31F7C4] text-[#31F7C4]"
                href="/auth/login"
              >
                Login
              </Link>
            )}
          </li>
        </ul>
      </nav>
    </div>
  );
}

export default Header;
