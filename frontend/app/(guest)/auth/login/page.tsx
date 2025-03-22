"use client";
import React, { useState } from "react";
import Image from "next/image";
import Link from "next/link";

import signinIm from "@/public/Illusttration.png";

export default function SignIn() {
  const types = ["email", "password"];
  const [infor, setInfor] = useState({
    email: "",
    password: "",
  });

  function handleChange(event: any) {
    const { id, value } = event.target;
    setInfor({
      ...infor,
      [types[id]]: value,
    });
  }

  async function handleSignin() {
    console.log(infor);
  }

  function handleKeyDown(event: any) {
    if (event.key === "Enter") {
      handleSignin();
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
              <small className="text-base">woof ~</small> Đăng nhập{" "}
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
                className="p-2 !border-t-blue-gray-200 focus:!border-t-gray-900"
                onChange={(e) => handleChange(e)}
                onKeyDown={(e) => handleKeyDown(e)}
              />
              <div color="blue-gray" className="-mb-3 font-medium">
                Password
              </div>
              <div className="flex relative">
                <input
                  id="1"
                  value={infor.password}
                  placeholder="********"
                  className="p-2 !border-t-blue-gray-200 focus:!border-t-gray-900"
                  autoComplete="password"
                  onChange={(e) => handleChange(e)}
                  onKeyDown={(e) => handleKeyDown(e)}
                />
              </div>
            </div>

            <button className="p-2 mt-6 bg-sky-500 rounded-xl">Login</button>

            <div className="text-center text-blue-gray-500 font-medium mt-4">
              {/* link to signup */}
              <Link href="/auth/signup">Chưa có tài khoản?</Link>
            </div>
          </form>
        </div>
      </section>
    </div>
  );
}
