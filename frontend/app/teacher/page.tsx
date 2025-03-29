"use client";
import Image from "next/image";
import React, { useRef } from "react";
import QuizzCode from "@/app/components/QuizzCode";
import HistoryCard from "@/app/components/StudentHistoryCard";
import Link from "next/link";

const TeacherScreen = () => {
  const [quizzCode, setQuizzCode] = React.useState("");
  console.log(quizzCode);
  return (
    <>
      <button className={`text-white p-2 rounded-md font-bold bg-[#2DFF9C] `}>
        <Link href={"/teacher/create-quiz"}>Create Quizz now</Link>
      </button>
      {/* <div className="flex flex-row gap-4 self-center mt-20">
        <HistoryCard />
        <HistoryCard />
        <HistoryCard />
      </div> */}
    </>
  );
};

export default TeacherScreen;
