"use client";
import AddQuestion from "@/app/components/AddQuestion";
import React, { useActionState, useState } from "react";

type MyFormData = {
  errors: {};
  questions: {
    question: string;
    options: string[];
    isCorrect: string;
  }[];
};

const Questions = () => {
  const [questionsNum, setQuestionsNum] = useState<number[]>([0]); // bắt đầu với 4 câu hỏi

  const handleAddQuestion = () => {
    setQuestionsNum((prev) => [...prev, prev.length]);
  };

  function questionFormAction(prev: any, formData: FormData) {
    const data = Object.fromEntries(formData.entries());

    let errors = {};
    let questions = [];

    for (let i = 0; i < questionsNum.length; i++) {
      let errorsInQuestion = [];
      let alreadyHaveCorrect = false;

      if (!data[`question${i}`]) {
        errorsInQuestion.push("Question is required");
      }
      for (let j = 0; j < 4; j++) {
        if (!data[`${i}-option${j}`]) {
          errorsInQuestion.push(`Option ${j + 1} is required`);
        }
      }
      if (!data[`correct${i}`]) {
        if (prev.questions[i]?.isCorrect === undefined)
          errorsInQuestion.push("Correct option is required");
        else alreadyHaveCorrect = true;
      }
      if (errorsInQuestion.length > 0) {
        errors = {
          ...errors,
          [`question${i}`]: errorsInQuestion,
        };
        continue;
      }
      if (alreadyHaveCorrect) {
        questions[i] = {
          question: data[`question${i}`],
          options: [
            data[`${i}-option0`],
            data[`${i}-option1`],
            data[`${i}-option2`],
            data[`${i}-option3`],
          ],
          isCorrect: prev.questions[i].isCorrect,
        };
      } else
        questions[i] = {
          question: data[`question${i}`],
          options: [
            data[`${i}-option0`],
            data[`${i}-option1`],
            data[`${i}-option2`],
            data[`${i}-option3`],
          ],
          isCorrect: data[`correct${i}`],
        };
    }

    return {
      errors,
      questions,
    };
  }

  const [formState, formActions] = useActionState<MyFormData>(
    questionFormAction,
    {
      errors: {},
      questions: [],
    }
  );
  console.log(formState);
  return (
    <div className="flex flex-col gap-8 p-4 w-[60%] bg-white border container text-black shadow-xl rounded-lg z-30">
      <div className="flex flex-col">
        <p className="self-end text-lg px-4">Quizz Name: ABC</p>
        <p className="self-end text-lg px-4">Quizz ID: 1234</p>
        <p className="self-end text-lg px-4">
          Total Question: {questionsNum.length}
        </p>
      </div>
      {/* {questions.map((q, index) => (
        <AddQuestion key={index} />
      ))} */}
      <form action={formActions}>
        {questionsNum.map((q, index) => (
          <AddQuestion
            key={index}
            questionId={index}
            errors={formState.errors[`question${index}`]}
          />
        ))}
        <div className="flex gap-10 mt-4">
          <button className="text-white p-4 rounded-md font-bold bg-[#218d72] text-xl flex-1 hover:bg-[#218d72]">
            Submit
          </button>
          <button
            type="button"
            onClick={handleAddQuestion}
            className="text-gray-900 p-4 rounded-md font-bold hover:bg-[#218d72] hover:text-white text-xl flex-1"
          >
            Add Question
          </button>
        </div>
      </form>
    </div>
  );
};

export default Questions;
