"use client";
import React, { Suspense, useState } from "react";
import Image from "next/image";
import Link from "next/link";

import signinIm from "@/public/Illusttration.png";
import { validateLoginData } from "@/lib/validation/auth";
import { useRouter } from "next/navigation";
import { signIn } from "next-auth/react";
import * as request from "@/app/axios";
export default function SignIn() {
  const router = useRouter();
  const types = ["email", "password", "role"];
  const [infor, setInfor] = useState({
    email: "",
    password: "",
    role: "student",
  });

  const [errors, setErrors] = useState<any[]>([]);

  function handleChange(event: any) {
    const { id, value } = event.target;
    setInfor({
      ...infor,
      [types[id]]: value,
    });
    const er = errors;

    if (er[id]) {
      er[id].message = "";
      setErrors(er);
    }
  }

  const handleSignIn = async () => {
    const { success, errors } = validateLoginData(infor);
    if (!success) {
      setErrors(errors);
      return;
    }
    console.log("Login success", infor);

    // const res = await request.post(`/auth/signin/${infor.role}`, {
    //   email: infor.email,
    //   password: infor.password,
    // });
    // if (!res.ok) {
    //   console.error("Login failed:", res.statusText);
    // } else {
    //   console.log("Login successful with student", res);
    // }

    // Use signIn from next-auth/react
    const result = await signIn("credentials", {
      redirect: false,
      email: infor.email,
      password: infor.password,
      role: infor.role, // Include role if needed
    });

    if (result?.error) {
      console.error("Login failed:", result.error);
    } else {
      console.log("Login successful");
      // Redirect to the home page
      router.push("/auth/my-account");
    }
    reset();

    // router.push("/student/1");
  };

  const reset = () => {
    setInfor({
      email: "",
      password: "",
      role: "student",
    });
  };

  function handleKeyDown(event: any) {
    if (event.key === "Enter") {
      handleSignIn();
    }
  }

  return (
    <div className="bg-white">
      <section className="flex gap-4">
        <div className="basis-3/5 max-h-fit hidden lg:block">
          <Image
            alt="Sign in"
            src={signinIm}
            className="h-full w-full object-cover rounded-3xl"
          />
        </div>
        <div className="w-full lg:w-7/12 mt-24">
          <div className="text-center">
            <div className="font-bold text-6xl mb-4 text-black">
              <small className="text-base">woof ~</small> Login{" "}
              <small className="text-base">~ woof</small>
            </div>
          </div>
          <form className="mt-8 mb-2 mx-auto w-80 max-w-screen-lg lg:w-1/2 text-black">
            <div className="mb-1 flex flex-col gap-6">
              <div color="blue-gray" className="-mb-3 font-medium">
                Email
              </div>
              <input
                id="0"
                value={infor.email}
                placeholder="tung@gmail.com"
                className="w-full p-3 rounded-xl border border-gray-200 bg-white/70 backdrop-blur-md shadow-md placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-400 transition"
                onChange={(e) => handleChange(e)}
                onKeyDown={(e) => handleKeyDown(e)}
              />
              {errors.some((err) => err.field === "email") && (
                <p className="text-red-500 text-sm">
                  {errors.find((err) => err.field === "email")?.message}
                </p>
              )}
              <div color="blue-gray" className="-mb-3 font-medium">
                Password
              </div>
              <div className="flex relative">
                <input
                  id="1"
                  value={infor.password}
                  placeholder="********"
                  className="w-full p-3 rounded-xl border border-gray-200 bg-white/70 backdrop-blur-md shadow-md placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-400 transition"
                  autoComplete="password"
                  onChange={(e) => handleChange(e)}
                  onKeyDown={(e) => handleKeyDown(e)}
                />
              </div>
              {errors.some((err) => err.field === "password") && (
                <p className="text-red-500 text-sm">
                  {errors.find((err) => err.field === "password")?.message}
                </p>
              )}
              <div color="blue-gray" className="-mb-3 font-medium">
                Role
              </div>
              <div className="flex relative">
                <select
                  id="2"
                  value={infor.role}
                  className="w-full p-3 rounded-xl border border-gray-200 bg-white/70 backdrop-blur-md shadow-md placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-400 transition"
                  onChange={(e) => handleChange(e)}
                  onKeyDown={(e) => handleKeyDown(e)}
                >
                  <option value="" disabled>
                    Select your role
                  </option>
                  <option value="student">Student</option>
                  <option value="teacher">Teacher</option>
                </select>
              </div>
              {errors.some((err) => err.field === "role") && (
                <p className="text-red-500 text-sm">
                  {errors.find((err) => err.field === "role")?.message}
                </p>
              )}
            </div>

            <button
              className="p-2 px-4 mt-6 bg-sky-500 rounded-xl text-slate-50 font-bold"
              onClick={(e) => {
                e.preventDefault();
                handleSignIn();
              }}
            >
              Login
            </button>

            <div className="text-center text-blue-gray-500 font-medium mt-4">
              {/* link to signup */}
              Chưa có tài khoản?
              <Link className="ml-2 text-red-500 font-bold" href="/auth/signup">
                Sign Up
              </Link>
            </div>
          </form>
        </div>
      </section>
    </div>
  );
}
