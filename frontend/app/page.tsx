import StudentHome from "@/app/student/[id]/page";
import TeacherScreen from "@/app/teacher/[id]/page";
import Image from "next/image";

export default function Home() {
  return (
    <main style={{ backgroundColor: "white" }} className="flex flex-1">
      <TeacherScreen />
      {/* <StudentHome /> */}
    </main>
  );
}
