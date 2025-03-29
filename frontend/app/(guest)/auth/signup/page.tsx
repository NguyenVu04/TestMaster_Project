"use client";
import React, { useState } from "react";
import Image from "next/image";

import signinIm from "@/public/Illusttration.png";
import Link from "next/link";
import { validateSignupData } from "@/lib/validation/auth";
import { redirect } from "next/navigation";

export default function SignIn() {
  const types = [
    "first_name",
    "last_name",
    "email",
    "password",
    "confirm_password",
    "phone_number",
    "role",
  ];
  const [infor, setInfor] = useState({
    first_name: "",
    last_name: "",
    email: "",
    password: "",
    confirm_password: "",
    phone_number: "",
    role: "student",
  });

  const [errors, setErrors] = useState<any[]>([]);

  

  function handleChange(event: any) {
    const { id, value } = event.target;
    setInfor({
      ...infor,
      [types[id]]: value,
    });
    if (errors && errors.length > 0) {
      const er = errors;
      er[id].message = "";
      setErrors(er);
    }
  }

  async function handleSignUp() {
    const { success, errors } = validateSignupData(infor);
    if (!success) {
      console.log(errors);
      setErrors(errors);
      return;
    }

    try {
      const res = await fetch(
        `http://localhost:8080/api/auth/signup/${infor.role}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            firstName: infor[`first_name`],
            lastName: infor[`last_name`],
            email: infor.email,
            password: infor.password,
            phoneNumber: infor[`phone_number`],
          }),
        }
      );

      if (!res.ok) {
        console.error("Sign up failed:", res.statusText);
      } else {
        console.log("Sign up successful with student", res);
      }
    } catch (error) {
      console.error("Sign up failed:", error);
    }

    console.log("Sign up success!", infor);
    // reset();
    redirect('/auth/login');
  }

  const reset = () => {
    setInfor({
      first_name: "",
      last_name: "",
      email: "",
      password: "",
      confirm_password: "",
      phone_number: "",
      role: "student",
    });
  };

  function handleKeyDown(event: any) {
    if (event.key === "Enter") {
      handleSignUp();
    }
  }

  return (
    <div className="bg-white pb-2">
      <section className="flex gap-4">
        <div className="w-full lg:w-7/12 my-24">
          <div className="text-center text-black">
            <div className="font-bold text-6xl mb-4">
              <small className="text-base">meow ~</small> Sign Up{" "}
              <small className="text-base">~ meow</small>
            </div>
          </div>
          <form className="mt-8 mb-2 mx-auto w-80 max-w-screen-lg lg:w-1/2 text-black">
            <div className="mb-1 flex flex-col gap-6">
              <div color="blue-gray" className="-mb-3 font-medium">
                First Name
              </div>
              <input
                id="0"
                value={infor.first_name}
                placeholder="Lương"
                className="w-full p-3 rounded-xl border border-gray-200 bg-white/70 backdrop-blur-md shadow-md placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-400 transition"
                onChange={(e) => handleChange(e)}
                onKeyDown={(e) => handleKeyDown(e)}
              />
              {errors.some((err) => err.field === "first_name") && (
                <p className="text-red-500 text-sm">
                  {errors.find((err) => err.field === "first_name")?.message}
                </p>
              )}

              <div color="blue-gray" className="-mb-3 font-medium">
                Last Name
              </div>
              <input
                id="1"
                value={infor.last_name}
                placeholder="Tùng"
                className="w-full p-3 rounded-xl border border-gray-200 bg-white/70 backdrop-blur-md shadow-md placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-400 transition"
                onChange={(e) => handleChange(e)}
                onKeyDown={(e) => handleKeyDown(e)}
              />
              {errors.some((err) => err.field === "last_name") && (
                <p className="text-red-500 text-sm">
                  {errors.find((err) => err.field === "last_name")?.message}
                </p>
              )}

              <div color="blue-gray" className="-mb-3 font-medium">
                Email
              </div>
              <input
                id="2"
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
                  id="3"
                  value={infor.password}
                  placeholder="********"
                  className="w-full p-3 rounded-xl border border-gray-200 bg-white/70 backdrop-blur-md shadow-md placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-400 transition"
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
                Confirm Password
              </div>
              <div className="flex relative">
                <input
                  id="4"
                  value={infor.confirm_password}
                  placeholder="********"
                  className="w-full p-3 rounded-xl border border-gray-200 bg-white/70 backdrop-blur-md shadow-md placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-400 transition"
                  onChange={(e) => handleChange(e)}
                  onKeyDown={(e) => handleKeyDown(e)}
                />
              </div>
              {errors.some((err) => err.field === "confirm_password") && (
                <p className="text-red-500 text-sm">
                  {
                    errors.find((err) => err.field === "confirm_password")
                      ?.message
                  }
                </p>
              )}

              <div color="blue-gray" className="-mb-3 font-medium">
                Phone Number
              </div>
              <input
                id="5"
                value={infor.phone_number}
                placeholder="095325352"
                className="w-full p-3 rounded-xl border border-gray-200 bg-white/70 backdrop-blur-md shadow-md placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-400 transition"
                onChange={(e) => handleChange(e)}
                onKeyDown={(e) => handleKeyDown(e)}
              />
              {errors.some((err) => err.field === "phone_number") && (
                <p className="text-red-500 text-sm">
                  {errors.find((err) => err.field === "phone_number")?.message}
                </p>
              )}

              <div color="blue-gray" className="-mb-3 font-medium">
                Role
              </div>
              <div className="flex relative">
                <select
                  id="6"
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
              className="py-2 px-4 mt-6 bg-red-500 rounded-xl text-slate-50 font-bold"
              onClick={(e) => {
                e.preventDefault();
                handleSignUp();
              }}
            >
              Sign up
            </button>

            <div className="text-center text-blue-gray-500 font-medium mt-4">
              Đã có tài khoản?
              <Link className="ml-2 text-sky-500 font-bold" href="/auth/login">
                Login
              </Link>
            </div>
          </form>
        </div>
        <div className="basis-3/5 max-h-fit hidden lg:block">
          <Image
            alt="Sign in"
            src={signinIm}
            className="h-full w-full object-cover rounded-3xl"
          />
        </div>
      </section>
    </div>
  );
}
