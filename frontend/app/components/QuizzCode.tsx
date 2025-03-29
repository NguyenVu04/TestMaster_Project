'use client'

import React, { useRef } from "react";
import { QuizzInfo } from "../interface/quizzInfo";
// import { useRouter } from "next/router";
import { redirect } from "next/navigation";

type QuizzCodeProps = {
  onClick: (code: string) => void;
};

const QuizzCode = ({ onClick }: QuizzCodeProps) => {

  const [examInfo, setExamInfo] = React.useState({} as QuizzInfo);

  const inputRef = useRef<HTMLInputElement>(null);
  return (
    <div className="flex flex-col gap-4 items-center gap-0 mt-20">
      <input 
        className="border-2 border-gray-300 p-2 rounded-lg text-black" 
        type="text" 
        placeholder="Quizz ID"
        value={examInfo.id}
        onChange={(e) => {
          setExamInfo({
            ...examInfo,
            id: e.target.value
          })
        }}
      />

      <input
        ref={inputRef}
        type="text"
        placeholder="Enter Quizz Code"
        className="border-2 border-gray-300 p-2 rounded-lg text-black"
        value={examInfo.passcode}
        onChange={(e) => {
          setExamInfo({
            ...examInfo,
            passcode: e.target.value
          })
        }}
      />
      <button
        onClick={() => {
          if (inputRef.current) {
            onClick(inputRef.current.value);
          }

          redirect(`/student/quizz?id=${examInfo.id}&passcode=${examInfo.passcode}`);
          // router.push(`/exam?id=${examInfo.id}&passcode=${examInfo.passcode}`);

        }}
        className={`text-white w-full p-2 rounded-md font-bold bg-[#2DFF9C] `}
      >
        Go
      </button>
    </div>
  );
};

export default QuizzCode;
