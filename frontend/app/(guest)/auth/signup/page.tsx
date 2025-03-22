'use client'
import React, { useState } from "react";
import Image from 'next/image'

import signinIm from '@/public/Illusttration.png';
import Link from "next/link";


export default function SignIn() {
    const types = [
        'firstName',
        'lastName',
        'email',
        'password',
        // 'confirmPassword',
        'phone_number',
        'role',

      ];
  const [infor, setInfor] = useState({
      first_name: '',
      last_name: '',
      email: '',
      password: '',
    //   confirmPassword: '',
      phone_number: '',
      role: '',
    });



  function handleChange(event: any) {
    const { id, value } = event.target;
    setInfor({
      ...infor,
      [types[id]]: value,
    });
  }

  async function handleSignUp() {
    console.log(infor);
  }

  function handleKeyDown(event: any) {
    if (event.key === "Enter") {
      handleSignUp();
    }
  }

  return (
    <div className="bg-white">
        <section className="flex gap-4">
      <div className="w-full lg:w-7/12 mt-24">
        <div className="text-center text-black">
          <div className="font-bold text-6xl mb-4">
            <small className="text-base">meow ~</small> Đăng kí{" "}
            <small className="text-base">~ meow</small>
          </div>
        </div>
        <form className="mt-8 mb-2 mx-auto w-80 max-w-screen-lg lg:w-1/2 text-black">
          <div className="mb-1 flex flex-col gap-6">
            <div
              
              color="blue-gray"
              className="-mb-3 font-medium"
            >
              First Name
            </div>
            <input
              id="0"
              value={infor.first_name}
              placeholder="Lương"
              className="p-2 !border-t-blue-gray-200 focus:!border-t-gray-900"
              onChange={(e) => handleChange(e)}
              onKeyDown={(e) => handleKeyDown(e)}
            />
            <div
              color="blue-gray"
              className="-mb-3 font-medium"
            >
              Last Name
            </div>
            <input
              id="1"
              value={infor.last_name}
              placeholder="Tùng"
              className="p-2 !border-t-blue-gray-200 focus:!border-t-gray-900"
              onChange={(e) => handleChange(e)}
              onKeyDown={(e) => handleKeyDown(e)}
            />

            <div
              color="blue-gray"
              className="-mb-3 font-medium"
            >
              Email
            </div>
            <input
              id="2"

              value={infor.email}
              placeholder="tung@gmail.com"
              className="p-2 !border-t-blue-gray-200 focus:!border-t-gray-900"
              onChange={(e) => handleChange(e)}
              onKeyDown={(e) => handleKeyDown(e)}
            />
            <div
              color="blue-gray"
              className="-mb-3 font-medium"
            >
              Phone Number
            </div>
            <input
              id="3"
              value={infor.phone_number}
              placeholder="095325352"
              className="p-2 !border-t-blue-gray-200 focus:!border-t-gray-900"
              onChange={(e) => handleChange(e)}
              onKeyDown={(e) => handleKeyDown(e)}
            />
            <div

              color="blue-gray"
              className="-mb-3 font-medium"
            >
              Password
            </div>
            <div className="flex relative">
              <input
                id="4"
                value={infor.password}
                placeholder="********"
                className="p-2 !border-t-blue-gray-200 focus:!border-t-gray-900"
                onChange={(e) => handleChange(e)}
                onKeyDown={(e) => handleKeyDown(e)}
              />
            </div>
            {/* <div

              color="blue-gray"
              className="-mb-3 font-medium"
            >
              Confirm Password
            </div>
            <div className="flex relative">
              <input
                id="4"
                value={infor.password}
                placeholder="********"
                className="p-2 !border-t-blue-gray-200 focus:!border-t-gray-900"
                autoComplete="password"
                onChange={(e) => handleChange(e)}
                onKeyDown={(e) => handleKeyDown(e)}
              />
            </div> */}
            <div

              color="blue-gray"
              className="-mb-3 font-medium"
            >
              Role
            </div>
            <div className="flex relative">
              <input
                id="5"
                value={infor.password}
                placeholder="********"
                className="p-2 !border-t-blue-gray-200 focus:!border-t-gray-900"
                autoComplete="password"
                onChange={(e) => handleChange(e)}
                onKeyDown={(e) => handleKeyDown(e)}
              />
            </div>
          </div>

          <button className="p-2 mt-6 bg-red-400 rounded-xl">
            Sign up
          </button>

          <div

            className="text-center text-blue-gray-500 font-medium mt-4"
          >

            <Link href="/auth/login">Đã có tài khoản?</Link>
          </div>
        </form>
      </div>
      <div className="basis-3/5 max-h-fit hidden lg:block">
        <Image
          alt = "Sign in"
          src={signinIm}
          className="h-full w-full object-cover rounded-3xl"
        />
      </div>
    </section>
    </div>
  );
}
