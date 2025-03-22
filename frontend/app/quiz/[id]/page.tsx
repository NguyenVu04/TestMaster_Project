'use client';
import React, { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';

interface QuizOption {
  id: string;
  answer: string;
}

interface QuizData {
  id: string;
  questionNumber: number;
  question: string;
  options: QuizOption[];
  total: number;
}

export default function QuizPage({ params }: { params: { id: string } }) {
  const [quiz, setQuiz] = useState<QuizData | null>(null);
  const [selectedOption, setSelectedOption] = useState<string | null>(null);
  const router = useRouter();

  // Fetch quiz data from /api/quiz/[id]
  useEffect(() => {
    async function fetchQuiz() {
      const res = await fetch(`/api/quiz/${params.id}`);
      const data = await res.json();
      setQuiz(data);
    }
    fetchQuiz();
  }, [params.id]);

  if (!quiz) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <p>Loading quiz...</p>
      </div>
    );
  }

  // Handle user selecting an option
  const handleOptionSelect = (optionId: string) => {
    setSelectedOption(optionId);
  };

  // Calculate progress based on current question number.
  // Assuming the first question is 155.
  const firstQuestion = 155;
  const lastQuestion = firstQuestion + quiz.total - 1;
  const currentIndex = quiz.questionNumber - firstQuestion + 1;
  const progressPercentage = (currentIndex / quiz.total) * 100;

  // Navigation handlers
  const handleBack = () => {
    const previousQuestion = quiz.questionNumber - 1;
    router.push(`/quiz/${previousQuestion}`);
  };

  const handleContinue = () => {
    if (quiz.questionNumber === lastQuestion) {
      // Last question: for example, redirect to a summary or show "Done"
      router.push('/quiz/done');
    } else {
      const nextQuestion = quiz.questionNumber + 1;
      router.push(`/quiz/${nextQuestion}`);
    }
  };

  return (
    <div className="min-h-screen bg-white flex items-center justify-center p-4">
      {/* Main quiz container */}
      <div className="w-full max-w-2xl bg-transparent rounded-md">
        {/* --- Top Bar (Title at the top) --- */}
        <div className="relative px-4 py-3 rounded-t-md bg-transparent border-b border-gray-200">
          {/* Close Button */}
          <button
            onClick={() => router.push('/')}
            className="absolute right-4 top-3 text-gray-400 hover:text-gray-600"
          >
            ✕
          </button>
          {/* Centered Title */}
          <h2 className="text-center text-gray-800 font-semibold">
            Fantasy Quiz #{quiz.questionNumber}
          </h2>
        </div>

        {/* Content Area */}
        <div className="p-6 space-y-6 bg-transparent">
          {/* Centered Question */}
          <h1 className="text-lg md:text-xl font-semibold text-center text-blue-800">
            {quiz.question}
          </h1>

          {/* Options container with grey background */}
          <div className="space-y-4 bg-gray-50 p-4 rounded-md">
            {quiz.options.map((opt) => (
              <button
                key={opt.id}
                onClick={() => handleOptionSelect(opt.id)}
                className={`w-full flex items-center justify-start space-x-4 px-4 py-3 rounded-md border transition-colors ${
                  selectedOption === opt.id
                    ? 'border-indigo-500 bg-gradient-to-r from-[#56CCF2] to-[#01FF88]'
                    : 'border-gray-300 '
                } bg-transparent`}
              >
                {/* Transparent circle around option identifier */}
                <span className="flex items-center justify-center w-8 h-8 rounded-full border border-gray-300 text-gray-700 bg-white">
                  {opt.id}
                </span>
                {/* Option Answer */}
                <span className="text-gray-700">{opt.answer}</span>
              </button>
            ))}
          </div>
        </div>

        {/* Bottom Bar (Back, Progress, Continue) with gradient background */}
        <div className="border-t border-gray-200 p-4 flex items-center justify-between rounded-b-md bg-white">
          {/* Show Back button only if not on first question */}
          {quiz.questionNumber > firstQuestion ? (
            <button
              className="px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gradient-to-r from-[#01FF88] to-[#56CCF2] transition"
              onClick={handleBack}
            >
              BACK
            </button>
          ) : (
            <div className="w-24" /> // Empty space placeholder for alignment
          )}

          {/* Progress Area */}
          <div className="flex flex-row items-center">
            {/* Progress bar */}
            <div className="w-48 h-2 bg-gray-200 rounded-full">
              <div
                className="h-2 rounded-full"
                style={{
                  width: `${progressPercentage}%`,
                  backgroundColor: '#01FF88',
                }}
              ></div>
            </div>
            {/* Progress text */}
            <div className="text-sm text-gray-600 mb-1 px-2">
              {currentIndex}/{quiz.total}
            </div>
          </div>

          {/* Continue / Done Button */}
          <button
            className="px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gradient-to-r from-[#01FF88] to-[#56CCF2] transition"
            onClick={handleContinue}
          >
            {quiz.questionNumber === lastQuestion ? 'DONE' : 'CONTINUE'}
          </button>
        </div>
      </div>
    </div>
  );
}
