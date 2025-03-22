"use client";
import HistoryCard from "@/app/components/StudentHistoryCard";
import QuizzCode from "@/app/components/QuizzCode";
import studentHistory from "@/app/interface/studentHistory";
import Image from "next/image";
import React from "react";

const DATA: studentHistory[] = [
  {
    attempTimes: 1,
    studentId: "1",
    studentName: "Tung",
    quizzTitle: "Quizz 1",
    teacherName: "Teacher 1",
    score: 10,
    feedback: "Good",
    startTime: new Date(),
  },
  {
    attempTimes: 2,
    studentId: "1",
    studentName: "Tung",
    quizzTitle: "Quizz 1",
    teacherName: "Teacher 1",
    score: 7,
    feedback: "Try harder next time",
    startTime: new Date(),
  },
  {
    attempTimes: 1,
    studentId: "2",
    studentName: "Linh",
    quizzTitle: "Quizz 2",
    teacherName: "Teacher 2",
    score: 8,
    feedback: "Well done",
    startTime: new Date(),
  },
  {
    attempTimes: 3,
    studentId: "3",
    studentName: "Minh",
    quizzTitle: "Quizz 3",
    teacherName: "Teacher 3",
    score: 6,
    feedback: "Needs improvement",
    startTime: new Date(),
  },
  {
    attempTimes: 2,
    studentId: "4",
    studentName: "Hoa",
    quizzTitle: "Quizz 4",
    teacherName: "Teacher 4",
    score: 9,
    startTime: new Date(),
  },
  {
    attempTimes: 1,
    studentId: "5",
    studentName: "Nam",
    quizzTitle: "Quizz 5",
    teacherName: "Teacher 5",
    score: 5,
    feedback: "Study more",
    startTime: new Date(),
  },
  {
    attempTimes: 4,
    studentId: "6",
    studentName: "An",
    quizzTitle: "Quizz 6",
    teacherName: "Teacher 6",
    score: 7,
    feedback: "Good progress",
    startTime: new Date(),
  },
  {
    attempTimes: 2,
    studentId: "7",
    studentName: "Binh",
    quizzTitle: "Quizz 7",
    teacherName: "Teacher 7",
    score: 8,
    feedback: "Keep it up",
    startTime: new Date(),
  },
  {
    attempTimes: 3,
    studentId: "8",
    studentName: "Phuong",
    quizzTitle: "Quizz 8",
    teacherName: "Teacher 8",
    score: 6,
    feedback: "Focus more on details",
    startTime: new Date(),
  },
  {
    attempTimes: 1,
    studentId: "9",
    studentName: "Huy",
    quizzTitle: "Quizz 9",
    teacherName: "Teacher 9",
    score: 10,
    feedback: "Excellent work",
    startTime: new Date(),
  },
];

function StudentHome() {
  const [quizzCode, setQuizzCode] = React.useState("");
  console.log(quizzCode);
  return (
    <div className="pb-32 flex flex-1 flex-col items-center justify-center relative ">
      <Image
        src="/outline5.png"
        alt="logo"
        width={200}
        height={200}
        className="absolute top-20 left-0"
      />
      <Image
        src="/outline4.png"
        alt="logo"
        width={200}
        height={200}
        className="absolute right-10 bottom-20"
      />

      <QuizzCode
        onClick={(code) => {
          setQuizzCode(code);
          console.log(code);
        }}
      />
      <div className="flex flex-wrap w-[60%] items-center justify-center gap-4 self-center mt-20">
        {/* <HistoryCard /> */}
        {DATA.map((data) => {
          return (
            <HistoryCard data={data} key={data.attempTimes + data.studentId} />
          );
        })}
      </div>
    </div>
  );
}

export default StudentHome;
