"use client";
import Image from "next/image";
import React, { useRef } from "react";
import QuizzCode from "@/app/components/QuizzCode";
import HistoryCard from "@/app/components/HistoryCard";

const TeacherScreen = () => {
  const [quizzCode, setQuizzCode] = React.useState("");
  console.log(quizzCode);
  return (
    <div className="flex flex-col items-center relative h-screen">
      <Image
        src="/outline5.png"
        alt="logo"
        width={200}
        height={200}
        className="absolute top-20 left-0"
      />
      <Image
        src="/outline4.png"
        alt="logo"
        width={200}
        height={200}
        className="absolute right-10 bottom-20"
      />
      <div>Header</div>

      <QuizzCode
        onClick={(code) => {
          setQuizzCode(code);
          console.log(code);
        }}
      />
      <div>
        <HistoryCard />
        <HistoryCard />
        <HistoryCard />
      </div>
    </div>
  );
};

export default TeacherScreen;
