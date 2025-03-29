export type Question = {
    id: string,
    type: string,
    content: string,
    media_url: string[],
    options: string[],
    answer: string
}

export type Exam = {
    title: string,
    description: string,
    attempt_limit: number,
    passcode: string,
    start_time: Date,
    end_time: Date,
    time_limit: number
}