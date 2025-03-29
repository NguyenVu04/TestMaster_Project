import { Question } from "@/lib/definitions"

export type examStruc = {
    questions: Array<any>,
    startTime: string,
    endTime: string,
    timeLimit: number,
    submitted: boolean
}

// {
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