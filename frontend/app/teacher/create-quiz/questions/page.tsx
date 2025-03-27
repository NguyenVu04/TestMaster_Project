import AddQuestion from "@/app/components/AddQuestion";
import React from "react";

const Questions = () => {
  return (
    <div className="flex flex-col gap-8 p-4 w-[60%] bg-white border container text-black shadow-xl rounded-lg z-30">
      <div className="flex flex-col">
        <p className="self-end text-lg px-4">Quizz Name: ABC</p>
        <p className="self-end text-lg px-4">Quizz ID: 1234</p>
        <p className="self-end text-lg px-4">Total Question: 4</p>
      </div>
      <AddQuestion />
      <AddQuestion />
      <AddQuestion />
      <AddQuestion />
    </div>
  );
};

export default Questions;
