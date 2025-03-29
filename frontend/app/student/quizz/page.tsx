'use client'

import { examStruc } from '@/app/interface/examStruc';
import { useSession } from 'next-auth/react';
import { useSearchParams } from 'next/navigation';
import React from 'react'

function page() {
  const session = useSession();
  const searchParams = useSearchParams();

  const [attemptId, setAttemptId] = React.useState(null);
  const [examInfo, setExamInfo] = React.useState({} as examStruc);

  const id = searchParams.get("id");
  const passcode = searchParams.get("passcode");

  console.log(id, passcode);

  fetch('http://localhost:8080/api/student/exam/start', {
    method: 'POST',
    headers: { 
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${session.data?.user.accessToken}`
    },
    body: JSON.stringify({
      examId: `${id}`,
      passcode: `${passcode}` 
    }),
  })
  .then((res) => res.json())
  .then((data) => {
    setAttemptId(data.attemptId);
  })
  ;

  if(attemptId) {
    fetch('http://localhost:8080/api/student/exam/session', {
      method: 'POST',
      headers: { 
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${session.data?.user.accessToken}`
      },
      body: JSON.stringify({
        examId: `${id}`,
        attemptId: `${attemptId}`
      }),
    })
    .then((res) => {
      if (res.ok) {
        res.json().then((data) => {
          console.log(data);
          setExamInfo(data);
        });
      }
    });
  }

//   {
//     "questions": [
//         {
//             "id": "adea9db5-0467-4c9d-97b5-0559ca368cea",
//             "content": "What is polymorphism in Java?",
//             "options": [
//                 "Overloading",
//                 "Overriding",
//                 "Both",
//                 "None"
//             ],
//             "type": "MULTIPLE_CHOICE",
//             "mediaUrl": [],
//             "currentAnswer": null,
//             "score": 10
//         }
//     ],
//     "startTime": 1743230081121,
//     "endTime": 1743233681121,
//     "timeLimit": 3600,
//     "submitted": false
// }

  return (
    <div className='py-[81px] text-black w-full'>
      <h1 className='text-center'>Quizz</h1>
      {
        examInfo && (
          <div className='text-start container mx-auto'>
            <p>Start time: {examInfo.startTime}</p>
            <p>End time: {examInfo.endTime}</p>
            <p>Is submitted: {examInfo.submitted}</p>
            <p>Time limit: {examInfo.timeLimit}</p>
            <p>Questions: {examInfo.questions?.length}</p>
            <ul className='text-start'>
              {
                examInfo.questions && examInfo.questions.map((question, index) => (
                  <li key={index}>
                    <p>Question {index + 1}: {question.content}</p>
                    <ul>
                      {
                        question.options.map((option, optionIndex) => (
                          <li key={optionIndex} className='relative'>
                            <input type="radio" name={`question${index}`} className='absolute left-0 top-[50%]' style={{width: '20px', height: '20px'}}/>
                            <p className='px-12'>{option}</p>
                          </li>
                        ))
                      }
                    </ul>
                  </li>
                ))
              }
            </ul>
          </div>
        )
      }
    </div>
  )
}

export default page