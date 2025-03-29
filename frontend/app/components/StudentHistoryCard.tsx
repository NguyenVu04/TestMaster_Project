import studentHistory from "@/app/interface/studentHistory";
import React, { useCallback } from "react";

// attempTimes: number;
// studentId: string;
// //
// studentName: string;
// quizzTitle: string;
// teacherName: string;
// score: number;
// feedback?: string;
// startTime: Date;

type StudentHistoryCardProps = {
  data: studentHistory;
};
const StudentHistoryCard = ({ data }: StudentHistoryCardProps) => {
  const formatDate = useCallback((date: Date) => {
    return date.toLocaleDateString("en-UK");
  }, []);

  return (
    <div
      className="h-60 w-60 flex  flex-col gap-10 text-black  items-center border-2 rounded-lg"
      style={{ borderColor: "#2ABF9C" }}
    >
      <div
        className="text-2xl py-2 border-b-2 border-b-cyan-500 w-full text-center font-bold"
        style={{ borderColor: "#2ABF9C" }}
      >
        {data.quizzTitle}
        <p className="text-sm font-light pt-1">{formatDate(data.startTime)}</p>
      </div>
      <div className="flex flex-col gap-2  justify-center px-2">
        <div>You got {data.score}%</div>
        {data.feedback && <div>Feedback from teacher: {data.feedback}</div>}
      </div>
    </div>
  );
};

export default StudentHistoryCard;
