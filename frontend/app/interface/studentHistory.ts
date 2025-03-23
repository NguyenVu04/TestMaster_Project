type studentHistory = {
  // id for list
  attempTimes: number;
  studentId: string;
  //
  studentName: string;
  quizzTitle: string;
  teacherName: string;
  score: number;
  feedback?: string;
  startTime: Date;
};

export default studentHistory;
