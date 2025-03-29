'use client';
import React, { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { post } from '@/app/axios'; // if you wish to use your axios instance
import { useSession } from 'next-auth/react';

interface QuizQuestion {
  id: string;
  content: string;
  options: string[];
  type: string; // "MULTIPLE_CHOICE" or "SHORT_ANSWER"
  mediaUrls: string[];
  answer: string | string[];
  score: number;
}

interface QuizData {
  questions: QuizQuestion[];
  startTime: number;
  endTime: number;
  timeLimit: number; // in minutes
  submitted: boolean;
}

export default function QuizPage() {
  const [quiz, setQuiz] = useState<QuizData | null>(null);
  // Global answers: keys are question IDs, values are answers
  const [answers, setAnswers] = useState<Record<string, string>>({});
  // Tracks question status (updated on navigation)
  const [answeredStatus, setAnsweredStatus] = useState<Record<string, boolean>>({});
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [timeLeft, setTimeLeft] = useState<number>(0);
  const router = useRouter();

  // Hard-coded exam and attempt IDs (adjust as needed)
  const examId = "your-exam-uuid";
  const attemptId = "your-attempt-uuid";

  // DEBUG_MODE and API_URL constant
  const DEBUG_MODE = true;
  const API_URL = DEBUG_MODE
    ? '/api/quiz'
    : 'http://02ea-171-253-40-101.ngrok-free.app/api/student/exam/session';

  const session = useSession();
  // check status of session
  if (session.status === 'loading') {
    return <div>Loading...</div>;

  }
  else if (session.status === 'unauthenticated') {
    return <div className="min-h-screen flex items-center justify-center">
      <p className="text-red-500">You are not authenticated. Please log in.</p>
    </div>;
  }
  else {
    console.log('Session:', session.data);
//
  }

  // Fetch the entire quiz session from the API
  useEffect(() => {
    async function fetchQuiz() {
      const res = await fetch(API_URL, {
        method: DEBUG_MODE ? 'GET' : 'POST',
        headers: { 'Content-Type': 'application/json' ,
          'Authorization': 'Bearer '
        },
        ...(DEBUG_MODE ? {} : { body: JSON.stringify({ examId, attemptId }) }),
      });
      const data = await res.json();
      setQuiz(data);
      // Initialize timer based on timeLimit (in minutes)
      setTimeLeft(data.timeLimit * 60);
    }
    fetchQuiz();
  }, [API_URL, examId, attemptId, DEBUG_MODE]);

  // Timer effect: update every second
  useEffect(() => {
    if (timeLeft <= 0) return;
    const timer = setInterval(() => {
      setTimeLeft((prev) => prev - 1);
    }, 1000);
    return () => clearInterval(timer);
  }, [timeLeft]);

  // Periodic auto-save every 30 seconds
  useEffect(() => {
    const interval = setInterval(() => {
      saveSession();
    }, 30000);
    return () => clearInterval(interval);
  }, [answers]);

  // Function to update answeredStatus based on global answers
  async function updateAnsweredStatus() {
    if (!quiz) return;
    const updated: Record<string, boolean> = {};
    quiz.questions.forEach((q) => {
      const ans = answers[q.id] || '';
      // For both multiple-choice and short-answer, an answer is valid if not empty
      updated[q.id] = ans.trim().length > 0;
    });
    setAnsweredStatus(updated);
  }

  // Save the session (auto-save / on navigation)
  async function saveSession() {
    if (!quiz) return;
    const payload = {
      examId,
      attemptId,
      answers: Object.entries(answers).map(([questionId, answer]) => ({
        questionId,
        answer,
      })),
    };
    try {
      // Using fetch directly here; you could replace with your axios instance if desired.
      const res = await fetch('http://02ea-171-253-40-101.ngrok-free.app/api/student/exam/save', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
      });
      if (!res.ok) {
        console.error('Failed to save exam session');
      } else {
        console.log('Exam session saved');
      }
    } catch (error) {
      console.error('Error saving exam session', error);
    }
  }

  // Submit the exam (final submission)
  async function submitExam() {
    if (!quiz) return;
    const payload = {
      examId,
      attemptId,
      answers: Object.entries(answers).map(([questionId, answer]) => ({
        questionId,
        answer,
      })),
    };
    try {
      const res = await fetch('http://02ea-171-253-40-101.ngrok-free.app/api/student/exam/submit', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
      });
      if (!res.ok) {
        console.error('Failed to submit exam session');
      } else {
        console.log('Exam session submitted');
      }
    } catch (error) {
      console.error('Error submitting exam session', error);
    }
  }

  if (!quiz) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <p>Loading quiz...</p>
      </div>
    );
  }

  const currentQuestion = quiz.questions[currentQuestionIndex];
  const totalQuestions = quiz.questions.length;
  const progressPercentage = ((currentQuestionIndex + 1) / totalQuestions) * 100;

  // Format time in mm:ss
  const formatTime = (seconds: number): string => {
    const m = Math.floor(seconds / 60);
    const s = seconds % 60;
    return `${m.toString().padStart(2, '0')}:${s.toString().padStart(2, '0')}`;
  };

  // Check if current question is multiple-choice
  const isMultipleChoice = currentQuestion.type === 'MULTIPLE_CHOICE';

  // Get current saved answer from global state
  const currentAnswer = answers[currentQuestion.id] || '';

  // Handle option selection (for multiple-choice)
  const handleOptionSelect = (option: string) => {
    setAnswers((prev) => ({ ...prev, [currentQuestion.id]: option }));
  };

  // Handle short answer change
  const handleShortAnswerChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setAnswers((prev) => ({ ...prev, [currentQuestion.id]: value }));
  };

  // Navigation handlers
  const handleBack = async () => {
    if (currentQuestionIndex > 0) {
      await saveSession();
      await updateAnsweredStatus();
      setCurrentQuestionIndex(currentQuestionIndex - 1);
    }
  };

  const handleContinue = async () => {
    await saveSession();
    await updateAnsweredStatus();
    if (currentQuestionIndex < totalQuestions - 1) {
      setCurrentQuestionIndex(currentQuestionIndex + 1);
    } else {
      await submitExam();
      router.push('/student/1');
    }
  };
  return (
    <div className="w-screen h-screen bg-white p-4 flex items-start justify-center">
      {/* Outer container for left sidebar + quiz content */}
      <div className="flex flex-row w-full max-w-7xl gap-8">
        {/* Sidebar on the left */}
        <div className="w-64 bg-gray-100 p-4 rounded-md">
          <h3 className="text-lg font-bold mb-2">Question Status</h3>
          <div className="grid grid-cols-5 gap-2">
            {quiz.questions.map((q, index) => {
              const isAnswered = answeredStatus[q.id] || false;
              return (
                <div
                  key={q.id}
                  className={`w-8 h-8 rounded-full flex items-center justify-center text-xs cursor-pointer
                    ${isAnswered ? 'bg-green-200' : 'bg-red-200'}
                    ${index === currentQuestionIndex ? 'border-2 border-blue-500' : ''}`}
                  onClick={async () => {
                    await saveSession();
                    await updateAnsweredStatus();
                    setCurrentQuestionIndex(index);
                  }}
                >
                  {index + 1}
                </div>
              );
            })}
          </div>
        </div>

        {/* Main Quiz Content */}
        <div className="flex-1 flex flex-col items-center">
          <div className="w-full max-w-xl bg-transparent rounded-md">
            {/* Header */}
            <div className="flex justify-between items-center px-4 py-3 border-b border-gray-200 rounded-t-md bg-transparent">
              <button
                onClick={() => router.push('/')}
                className="text-gray-400 hover:text-gray-600"
              >
                ✕
              </button>
              <h2 className="text-center text-gray-800 font-semibold flex-1">
                Fantasy Quiz #{currentQuestionIndex + 1}
              </h2>
              <div className="text-gray-800 font-medium">
                {formatTime(timeLeft)}
              </div>
            </div>

            {/* Content Area */}
            <div className="p-6 space-y-6 bg-transparent">
              <h1 className="text-lg md:text-xl font-semibold text-center text-blue-800">
                {currentQuestion.content}
              </h1>
              <div className="space-y-4 bg-gray-50 p-4 rounded-md">
                {isMultipleChoice ? (
                  currentQuestion.options.map((option, index) => (
                    <button
                      key={index}
                      onClick={() => handleOptionSelect(option)}
                      className={`w-full flex items-center justify-start space-x-4 px-4 py-3 rounded-md border transition-colors ${
                        currentAnswer === option
                          ? 'border-indigo-500 bg-gradient-to-r from-[#56CCF2] to-[#01FF88]'
                          : 'border-gray-300'
                      } bg-transparent`}
                    >
                      <span className="flex items-center justify-center w-8 h-8 rounded-full border border-gray-300 text-gray-700 bg-white">
                        {String.fromCharCode(65 + index)}
                      </span>
                      <span className="text-gray-700">{option}</span>
                    </button>
                  ))
                ) : (
                  <input
                    type="text"
                    value={currentAnswer}
                    onChange={handleShortAnswerChange}
                    placeholder="Type your answer here..."
                    className="w-full p-3 text-black rounded-md border border-gray-300 focus:outline-none focus:border-indigo-500"
                  />
                )}
              </div>
            </div>

            {/* Bottom Bar */}
            <div className="border-t border-gray-200 p-4 flex items-center justify-between rounded-b-md bg-white">
              {currentQuestionIndex > 0 ? (
                <button
                  className="px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gradient-to-r from-[#01FF88] to-[#56CCF2] transition"
                  onClick={handleBack}
                >
                  BACK
                </button>
              ) : (
                <div className="w-24" />
              )}

              {/* Progress Bar & Text */}
              <div className="flex flex-row items-center">
                <div className="w-48 h-2 bg-gray-200 rounded-full">
                  <div
                    className="h-2 rounded-full"
                    style={{
                      width: `${progressPercentage.toFixed(0)}%`,
                      backgroundColor: '#01FF88',
                    }}
                  ></div>
                </div>
                <div className="text-sm text-gray-600 mb-1 px-2">
                  {currentQuestionIndex + 1}/{totalQuestions}
                </div>
              </div>

              <button
                className="px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gradient-to-r from-[#01FF88] to-[#56CCF2] transition"
                onClick={handleContinue}
              >
                {currentQuestionIndex === totalQuestions - 1 ? 'DONE' : 'CONTINUE'}
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}