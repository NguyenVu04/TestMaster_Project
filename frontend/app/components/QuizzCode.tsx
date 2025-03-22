import React, { useRef } from "react";

type QuizzCodeProps = {
  onClick: (code: string) => void;
};

const QuizzCode = ({ onClick }: QuizzCodeProps) => {
  const inputRef = useRef<HTMLInputElement>(null);
  return (
    <div className="flex flex-row items-center gap-0 mt-20">
      <input
        ref={inputRef}
        type="text"
        placeholder="Enter Quizz Code"
        className="border-2 border-gray-300 p-2 rounded-lg"
      />
      <button
        onClick={() => {
          if (inputRef.current) {
            onClick(inputRef.current.value);
          }
        }}
        className={`text-white p-2 rounded-md font-bold `}
        style={{ backgroundColor: "#2DFF9C" }}
      >
        Go
      </button>
    </div>
  );
};

export default QuizzCode;
