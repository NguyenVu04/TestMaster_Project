import { NextResponse } from 'next/server';

export async function GET(
  request: Request,
  { params }: { params: { id: string } }
) {
  let quizData;

  if (params.id === "155") {
    quizData = {
      id: params.id,
      questionNumber: 155,
      question: "What is the capital of France?",
      options: [
        { id: 'A', answer: "Paris" },
        { id: 'B', answer: "Berlin" },
        { id: 'C', answer: "Madrid" },
      ],
      progress: 1,
      total: 3,
    };
  } else if (params.id === "156") {
    quizData = {
      id: params.id,
      questionNumber: 156,
      question: "PREDICT THE TOP LOSER (for tomorrow) across these indices",
      options: [
        { id: 'A', answer: "NIFTY50" },
        { id: 'B', answer: "NIFTYNEXT50" },
        { id: 'C', answer: "NIFTYBANK" },
      ],
      progress: 2,
      total: 3,
    };
  } else if (params.id === "157") {
    quizData = {
      id: params.id,
      questionNumber: 157,
      question: "What is 2 + 2?",
      options: [
        { id: 'A', answer: "3" },
        { id: 'B', answer: "4" },
        { id: 'C', answer: "5" },
      ],
      progress: 3,
      total: 3,
    };
  } else {
    // Default fallback (returns question 156)
    quizData = {
      id: params.id,
      questionNumber: 156,
      question: "PREDICT THE TOP LOSER (for tomorrow) across these indices",
      options: [
        { id: 'A', answer: "NIFTY50" },
        { id: 'B', answer: "NIFTYNEXT50" },
        { id: 'C', answer: "NIFTYBANK" },
      ],
      progress: 2,
      total: 3,
    };
  }

  return NextResponse.json(quizData);
}
