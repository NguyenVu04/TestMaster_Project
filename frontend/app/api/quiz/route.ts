import { NextResponse } from 'next/server';

export async function OPTIONS(request: Request) {
  // Respond to preflight requests with proper CORS headers.
  return new NextResponse(null, {
    headers: {
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Methods": "GET, POST, OPTIONS",
      "Access-Control-Allow-Headers": "Content-Type",
    },
  });
}

export async function GET(request: Request) {
  // Set debug mode (as a string) to "false" for production
  const isDebug: string = 'true';

  if (isDebug === 'true') {
    // Return sample exam session data for debugging with new data
    const sampleExam = {
      questions: [
        {
          id: "11111111-1111-1111-1111-111111111111",
          content: "What is 2+2?",
          options: ["2", "3", "4", "5"],
          type: "MULTIPLE_CHOICE",
          mediaUrls: [],
          answer: "4",
          score: 10,
        },
        {
          id: "22222222-2222-2222-2222-222222222222",
          content: "Explain the theory of relativity.",
          options: [],
          type: "SHORT_ANSWER",
          mediaUrls: [],
          answer: "Theory of relativity explanation",
          score: 15,
        },
        {
          id: "33333333-3333-3333-3333-333333333333",
          content: "What is the capital of France?",
          options: ["Berlin", "Madrid", "Paris", "Rome"],
          type: "MULTIPLE_CHOICE",
          mediaUrls: [],
          answer: "Paris",
          score: 10,
        },
        {
          id: "44444444-4444-4444-4444-444444444444",
          content: "Describe the water cycle.",
          options: [],
          type: "SHORT_ANSWER",
          mediaUrls: [],
          answer: "Water cycle description",
          score: 15,
        },
        {
          id: "55555555-5555-5555-5555-555555555555",
          content: "What is the largest planet in our solar system?",
          options: ["Earth", "Mars", "Jupiter", "Saturn"],
          type: "MULTIPLE_CHOICE",
          mediaUrls: [],
          answer: "Jupiter",
          score: 10,
        },
      ],
      startTime: 1672531200000, // Sample start time (milliseconds)
      endTime: 1672534800000,   // Sample end time (milliseconds)
      timeLimit: 60,            // Time limit in minutes
      submitted: false,
    };

    return new NextResponse(JSON.stringify(sampleExam), {
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
    });
  } else {
    // Production mode: fetch the exam object from your backend API.
    try {
      // Use the provided ngrok URL as default
      const backendUrl = process.env.BACKEND_API_URL || "https://02ea-171-253-40-101.ngrok-free.app";
      
      const res = await fetch(`${backendUrl}/api/student/exam/session`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          examId: "your-exam-uuid",
          attemptId: "your-attempt-uuid",
        }),
      });

      if (!res.ok) {
        throw new Error("Failed to fetch exam data from backend");
      }
      const examData = await res.json();
      return new NextResponse(JSON.stringify(examData), {
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
        },
      });
    } catch (error) {
      console.error("Error fetching exam data", error);
      return NextResponse.error();
    }
  }
}
