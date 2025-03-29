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

const AddQuestion = () => {
  const [questionForm, setQuestionForm] = useState<QuestionType>({
    question: "",
    options: [],
    isCorrect: undefined,
  });

  return (
    <div className="flex flex-col gap-4 w-full border-dashed border-2 p-4 rounded-lg border-[#218d72]">
      {/* <label className={styles.label} htmlFor="title">
          1.
        </label> */}
      <textarea
        className={styles.questionArea}
        name="title"
        id="title"
        placeholder="Type question here"
        defaultValue={questionForm.question}
      />
      <div className="gap-4 grid grid-cols-4">
        <div className={styles.row}>
          {/* <label className={styles.label} htmlFor="option1">
            A.
          </label> */}
          <textarea
            className={styles.textarea}
            name="option1"
            id="option1"
            placeholder="Enter your option"
            defaultValue={questionForm.options[0]}
          />
          <button
            className="absolute right-2 top-2"
            onClick={() => {
              setQuestionForm({
                ...questionForm,
                isCorrect: 0,
              });
            }}
          >
            <SiTicktick
              color={questionForm.isCorrect === 0 ? "#31F7C4" : "gray"}
            />
          </button>
        </div>
        <div className={styles.row}>
          {/* <label className={styles.label} htmlFor="option2">
            B.
          </label> */}
          <textarea
            className={styles.textarea}
            name="option2"
            id="option2"
            placeholder="Enter your option"
            defaultValue={questionForm.options[1]}
          />
          <button
            className="absolute right-2 top-2"
            onClick={() => {
              setQuestionForm({
                ...questionForm,
                isCorrect: 1,
              });
            }}
          >
            <SiTicktick
              color={questionForm.isCorrect === 1 ? "#31F7C4" : "gray"}
            />
          </button>
        </div>
        <div className={styles.row}>
          {/* <label className={styles.label} htmlFor="option3">
            C.
          </label> */}
          <textarea
            className={styles.textarea}
            name="option3"
            id="option3"
            placeholder="Enter your option"
            defaultValue={questionForm.options[2]}
          />
          <button
            className="absolute right-2 top-2"
            onClick={() => {
              setQuestionForm({
                ...questionForm,
                isCorrect: 2,
              });
            }}
          >
            <SiTicktick
              color={questionForm.isCorrect === 2 ? "#31F7C4" : "gray"}
            />
          </button>
        </div>
        <div className={styles.row}>
          {/* <label className={styles.label} htmlFor="option4">
            D.
          </label> */}
          <textarea
            className={styles.textarea}
            name="option4"
            id="option4"
            placeholder="Enter your option"
            defaultValue={questionForm.options[3]}
          />
          <button
            className="absolute right-2 top-2"
            onClick={() => {
              setQuestionForm({
                ...questionForm,
                isCorrect: 3,
              });
            }}
          >
            <SiTicktick
              color={questionForm.isCorrect === 3 ? "#31F7C4" : "gray"}
            />
          </button>
        </div>
      </div>
    </div>
  );
};

export default AddQuestion;
