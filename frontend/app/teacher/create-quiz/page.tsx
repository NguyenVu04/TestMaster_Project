"use client";
import React, { useState, useActionState, useEffect } from "react";
import { redirect, useRouter } from "next/navigation";

import QuestionFormat from "@/app/interface/questionFormats";

type MyFormData = {
  errors: {
    title?: string;
    description?: string;
    passcode?: string;
    time_limit?: string;
    date?: string;
    questionFormat?: string;
    file?: string;
  };
  currInput: {
    title: string;
    description: string;
    passcode: string;
    time_limit: string;
    date: Date | undefined;
    questionFormat: QuestionFormat;
    file: File | undefined;
  };
  init: boolean;
};

function Page() {
  const [questionFormat, setQuestionFormat] = useState<QuestionFormat>("");
  const [fileName, setFileName] = useState("No file chosen");
  const [isDragOver, setIsDragOver] = useState(false);

  const handleFileChange = (event: any) => {
    const file = event.target.files[0];
    if (file) {
      setFileName(file.name);
    } else {
      setFileName("No file chosen");
    }
  };
  const handleDragOver = (event: any) => {
    event.preventDefault();
    setIsDragOver(true);
  };

  const handleDragLeave = () => {
    setIsDragOver(false);
  };

  const handleDrop = (event: any) => {
    event.preventDefault();
    setIsDragOver(false);
    const file = event.dataTransfer.files[0];
    if (file) {
      setFileName(file.name);
    }
  };

  const router = useRouter();
  function handleSubmit(event: any) {
    // router.push("/teacher/create-quiz/questions");
    event.preventDefault();
  }
  function quizzInfoAction(
    prevState: MyFormData,
    formData: FormData
  ): MyFormData | Promise<MyFormData> {
    console.log("Prev state", prevState);

    const data = Object.fromEntries(formData.entries());
    console.log(data);

    const errors: any = {};
    if (!data.title) {
      errors.title = "Title is required";
    }
    if (!data.description) {
      errors.description = "Description is required";
    }
    if (!data.passcode) {
      errors.passcode = "Passcode is required";
    }
    if (!data.time_limit) {
      errors.time_limit = "Time limit is required";
    }
    if (!data.date) {
      errors.date = "Date is required";
    }
    if (!data.questionFormat) {
      errors.questionFormat = "Question format is required";
    }
    if (data.questionFormat === "file" && !data.file) {
      errors.file = "File is required";
    }
    return {
      errors,
      currInput: {
        title: data.title as string,
        description: data.description as string,
        passcode: data.passcode as string,
        time_limit: data.time_limit as string,
        date: data.date ? new Date(data.date as string) : undefined,
        questionFormat: data.questionFormat as QuestionFormat,
        file: data.file,
      },
      init: false,
    };
  }

  const [formState, formAction] = useActionState(quizzInfoAction, {
    errors: {},
    currInput: {
      title: "",
      description: "",
      passcode: "",
      time_limit: "",
      date: undefined,
      questionFormat: "",
    },
    init: true,
  });

  useEffect(() => {
    if (
      formState.errors &&
      Object.keys(formState.errors).length == 0 &&
      !formState.init
    ) {
      localStorage.setItem("quizzInfo", JSON.stringify(formState.currInput));
      redirect("/teacher/create-quiz/questions");
    }
  }, [formState]);
  return (
    <>
      <form
        action={formAction}
        className="flex flex-col gap-8 p-4 w-[60%] bg-white border container text-black shadow-xl rounded-lg z-30"
      >
        <div className="flex flex-col">
          <label className="text-3xl" htmlFor="title">
            Quizz title
          </label>
          <input
            className="py-2 px-4 border text-xl rounded"
            type="text"
            name="title"
            id="title"
            placeholder="Enter your quizz title"
            defaultValue={formState.currInput.title}
          />
          {formState.errors.title && (
            <p className="text-red-500">{formState.errors.title}</p>
          )}
        </div>

        <div className="flex flex-col">
          <label className="text-3xl" htmlFor="description">
            Quizz description
          </label>
          <textarea
            className="py-2 px-4 border text-xl rounded"
            rows={5}
            name="description"
            id="title"
            placeholder="Enter your quizz description"
            defaultValue={formState.currInput.description}
          />
          {formState.errors.description && (
            <p className="text-red-500">{formState.errors.description}</p>
          )}
        </div>

        <div className="flex flex-col">
          <label className="text-3xl" htmlFor="passcode">
            Passcode
          </label>
          <input
            className="py-2 px-4 border text-xl rounded"
            type="text"
            name="passcode"
            id="passcode"
            placeholder="Enter your quizz passcode"
            defaultValue={formState.currInput.passcode}
          />
          {formState.errors.passcode && (
            <p className="text-red-500">{formState.errors.passcode}</p>
          )}
        </div>

        <div className="flex gap-8">
          <div className="flex flex-col flex-1">
            <label className="text-3xl" htmlFor="time_limit">
              Time Limit
            </label>
            <input
              className="py-2 px-4 border text-xl rounded"
              type="number"
              name="time_limit"
              id="time_limit"
              placeholder="minutes"
              defaultValue={formState.currInput.time_limit}
            />
            {formState.errors.time_limit && (
              <p className="text-red-500">{formState.errors.time_limit}</p>
            )}
          </div>

          <div className="flex flex-col flex-1">
            <label className="text-3xl" htmlFor="date">
              Date
            </label>
            <input
              className="py-2 px-4 border text-xl rounded"
              type="date"
              name="date"
              id="date"
              placeholder="Chose your quizz start date"
              defaultValue={
                formState.currInput.date
                  ? formState.currInput.date.toISOString().slice(0, 10)
                  : ""
              }
            />
            {formState.errors.date && (
              <p className="text-red-500">{formState.errors.date}</p>
            )}
          </div>
        </div>
        <div className="flex flex-col">
          <h3 className="text-3xl mb-3">
            Already have file? If not, please hard-code the questions
          </h3>
          <div className="flex gap-12 justify-center">
            <button type="button" onClick={() => setQuestionFormat("file")}>
              <label htmlFor="questionFormat" className="text-xl flex gap-2">
                <input
                  type="radio"
                  id="file"
                  name="questionFormat"
                  value={"file"}
                  checked={questionFormat === "file"}
                  onChange={() => setQuestionFormat("file")}
                />
                Using File
              </label>
            </button>
            <button
              type="button"
              onClick={() => setQuestionFormat("hard-code")}
            >
              <label htmlFor="hardcode" className="text-xl flex gap-2">
                <input
                  type="radio"
                  id="hardcode"
                  name="questionFormat"
                  value={"hardcode"}
                  onChange={() => setQuestionFormat("hard-code")}
                  checked={questionFormat === "hard-code"}
                />
                Hard-code
              </label>
            </button>
          </div>
          {formState.errors.questionFormat && (
            <p className="text-red-500">{formState.errors.questionFormat}</p>
          )}
        </div>

        {questionFormat === "file" && (
          <div className="flex items-center justify-center flex-col gap-4">
            <div
              className="text-center border-2 flex items-center flex-col border-dashed border-[#29bc98] rounded-lg p-10 space-y-4 w-[50%]"
              onDragOver={handleDragOver}
              onDragLeave={handleDragLeave}
              onDrop={handleDrop}
            >
              <label
                htmlFor="file-upload"
                className="cursor-pointer bg-[#31F7C4] text-white py-2 px-6 rounded-lg hover:bg-[#29bc98] transition duration-200"
              >
                {isDragOver ? "Drop the file here" : "Browse Files to upload"}
              </label>

              <input
                type="file"
                id="file-upload"
                className="hidden"
                onChange={handleFileChange}
                accept=".docx, .pdf"
              />

              {/* Display the selected file name */}
              {fileName !== "No file chosen" && (
                <div className="text-white w-fit max-w-sm px-6 py-2 border-2 rounded-lg bg-[#31F7C4] h-40 flex flex-col items-center justify-between">
                  <p></p>
                  <p>{fileName}</p>
                  <button
                    className="bg-red-200 hover:bg-red-400 p-2 rounded-md"
                    onClick={() => setFileName("No file chosen")}
                  >
                    Delete
                  </button>
                </div>
              )}
            </div>
            {formState.errors.file && (
              <p className="text-red-500">{formState.errors.file}</p>
            )}
          </div>
        )}
        <button
          className="p-4 rounded bg-[#31F7C4] text-white font-bold text-xl "
          type="submit"
        >
          Create quizz!
        </button>
      </form>
    </>
  );
}

export default Page;
