"use client";
import React, { useState } from "react";

const styles = {
  row: "flex flex-row gap-4 justify-start items-center relative",
  questionArea:
    "py-2 px-4 text-md rounded w-full h-40 text-center leading-loose resize-none",
  textarea:
    "py-2 px-4 border text-md rounded w-full h-40 text-center leading-loose resize-none border-[#218d72]",
  label: "text-xl",
};
import { SiTicktick } from "react-icons/si";

type QuestionType = {
  question: string;
  options: string[];
  isCorrect: 0 | 1 | 2 | 3 | undefined;
};
type AddQuestionProps = {
  questionId: number;
  errors?: string[];
};

const arr = [1, 2, 3, 4];

const AddQuestion = (props: AddQuestionProps) => {
  const [questionForm, setQuestionForm] = useState<QuestionType>({
    question: "",
    options: [],
    isCorrect: undefined,
  });

  return (
    <div className="flex flex-col gap-4 w-full border-dashed border-2 p-4 rounded-lg border-[#218d72] mb-4">
      <textarea
        className={styles.questionArea}
        name={`question${props.questionId}`}
        id="question"
        placeholder="Type question here"
        defaultValue={questionForm.question}
        onBlur={(e) => {
          if (e.target.value === questionForm.question) {
            return;
          }
          setQuestionForm({
            ...questionForm,
            question: e.target.value,
          });
        }}
      />
      <div className="gap-4 grid grid-cols-4">
        {arr.map((value, index) => {
          return (
            <div key={props.questionId + "." + index}>
              <div className={styles.row}>
                <textarea
                  className={styles.textarea}
                  name={`${props.questionId}-option${index}`}
                  id={`${props.questionId}-option${index}`}
                  placeholder="Enter your option"
                  defaultValue={questionForm.options[index]}
                  onBlur={(e) => {
                    const newOptions = [...questionForm.options];
                    newOptions[index] = e.target.value;
                    if (newOptions[index] === questionForm.options[index])
                      return;

                    setQuestionForm({
                      ...questionForm,
                      options: newOptions,
                    });
                  }}
                />
                <button
                  className="absolute right-2 top-2"
                  type="button"
                  onClick={() => {
                    setQuestionForm({
                      ...questionForm,
                      isCorrect: index as 0 | 1 | 2 | 3,
                    });
                  }}
                >
                  <input
                    type="radio"
                    name={`correct${props.questionId}`}
                    value={index}
                    className="hidden"
                    checked={questionForm.isCorrect === index}
                    onChange={() => {
                      setQuestionForm({
                        ...questionForm,
                        isCorrect: index as 0 | 1 | 2 | 3,
                      });
                    }}
                  />
                  <SiTicktick
                    color={
                      questionForm.isCorrect === index ? "#31F7C4" : "gray"
                    }
                  />
                </button>
              </div>
            </div>
          );
        })}
      </div>
      <div>
        {props.errors &&
          props.errors.map((error, index) => (
            <p key={index} className="text-red-500">
              {error}
            </p>
          ))}
      </div>
    </div>
  );
};

export default AddQuestion;
