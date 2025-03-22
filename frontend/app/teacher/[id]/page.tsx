"use client";
import Image from "next/image";
import React, { useRef } from "react";
import QuizzCode from "@/app/components/QuizzCode";
import HistoryCard from "@/app/components/HistoryCard";

const TeacherScreen = () => {
  const [quizzCode, setQuizzCode] = React.useState("");
  console.log(quizzCode);
  return (
    <div className="flex flex-1 border-2 flex-col items-center  relative ">
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

      <QuizzCode
        onClick={(code) => {
          setQuizzCode(code);
          console.log(code);
        }}
      />
      <div className="flex flex-row gap-4 self-center mt-20">
        <HistoryCard />
        <HistoryCard />
        <HistoryCard />
      </div>
    </div>
  );
};

export default TeacherScreen;
