'use client';

import React, { useState } from 'react'
import { IoReturnUpBackOutline } from "react-icons/io5";

import { Question, Exam } from '@/lib/definitions';
import Questioncard from '@/app/components/QuestionCard';
import { useRouter } from 'next/navigation';

function page() {
  const [form_status, setForm_status] = useState('infor')
  
  const [isShownOverlay, setIsShownOverlay] = useState(false)
  
  const [quizzInfor, setQuizzInfor] = useState({
    title: 'Test exam',
  } as Exam)

  const [newQuestion, setNewQuestion] = useState({
    id: '',
    type: '',
    content: '',
    media_url: [],
    options: ['','','',''],
    answer: ''
  } as Question)

  const [questions, setQuestions] = useState([] as Question[])

  const router = useRouter();

  return (
    <div className='bg-[#2faffe] pb-[80px] pt-[90px] flex justify-center items-center text-white  min-h-screen'>
        <form 
            action=""
            method='POST'
            className='relative flex flex-col gap-8 mb-[80px] mt-[90px] p-4 bg-white rounded container text-black shadow-xl'
        >
            {form_status === 'infor' && (
                <>
                    <div className='flex flex-col'>
                        <label className='text-3xl' htmlFor="title">Quizz title</label>
                        <input 
                            className='py-2 px-4 border text-xl rounded' 
                            type="text" 
                            value={quizzInfor.title} 
                            onChange={e => setQuizzInfor({...quizzInfor, title: e.target.value})}
                            name="title" id="title" 
                            placeholder='Enter your quizz title' />
                    </div>

                    <div className='flex flex-col'>
                        <label className='text-3xl' htmlFor="description">Quizz description</label>
                        <textarea 
                            className='py-2 px-4 border text-xl rounded' 
                            rows={5} 
                            name="description" 
                            id="description" 
                            value={quizzInfor.description || ''}
                            onChange={e => setQuizzInfor({...quizzInfor, description: e.target.value})}
                            placeholder='Enter your quizz description' />
                    </div>
                    
                    <div className='flex flex-col'>
                        <label className='text-3xl' htmlFor="passcode">Quizz passcode</label>
                        <input 
                            className='py-2 px-4 border text-xl rounded' 
                            type="text" 
                            name="passcode" 
                            id="passcode" 
                            value={quizzInfor.passcode || ''}
                            onChange={e => setQuizzInfor({...quizzInfor, passcode: e.target.value})}
                            placeholder='Enter your quizz passcode' />
                    </div>

                    <div className='flex flex-col'>
                        <label className='text-3xl' htmlFor="attempt_limit">Quizz attempt limit</label>
                        <input 
                            className='py-2 px-4 border text-xl rounded' 
                            type="number" 
                            name="attempt_limit" 
                            id="attempt_limit" 
                            value={quizzInfor.attempt_limit || 0}
                            onChange={e => {
                                if (Number(e.target.value) < 0) {
                                    alert('Attempt limit must be greater than 0')
                                    return
                                }

                                setQuizzInfor({...quizzInfor, attempt_limit: Number(e.target.value)})
                            }}
                            placeholder='Enter your quizz attempt limit' />
                    </div>

                    <div className='flex flex-col'>
                        <label className='text-3xl' htmlFor="time_limit">Quizz time limit</label>
                        <input 
                            className='py-2 px-4 border text-xl rounded' 
                            type="number" 
                            name="time_limit" 
                            id="time_limit" 
                            value={quizzInfor.time_limit || 0}
                            onChange={e => {
                                if (Number(e.target.value) < 0) {
                                    alert('Time limit must be greater than 0')
                                    return
                                }

                                setQuizzInfor({...quizzInfor, time_limit: Number(e.target.value)})
                            }}
                            placeholder='Enter your quizz time limit (in minute)' />
                    </div>
                    
                    <div className='flex flex-col'>
                        <label className='text-3xl' htmlFor="date">Quizz start date</label>
                        <input 
                            className='py-2 px-4 border text-xl rounded' 
                            type="date" 
                            name="date" 
                            id="date"
                            value={quizzInfor.start_time?.toISOString().split('T')[0] || ''}
                            onChange={e => {
                                if (Date.parse(e.target.value) < Date.now()) {
                                    alert('Start date must be greater than current date')
                                    return
                                }

                                setQuizzInfor({...quizzInfor, start_time: new Date(e.target.value)})
                            }} 
                            placeholder='Chose your quizz start date' />
                    </div>

                    <button 
                        className='p-2 rounded bg-[#31F7C4] mt-auto' 
                        type='submit'
                        onClick={e => {
                            e.preventDefault()
                            if(!quizzInfor.title) {
                                alert('Quizz title is required')
                                return
                            }

                            setForm_status('question')
                        }}
                    >
                        Create quizz!
                    </button>
                </>
            )}

            {form_status === 'question' && (
                <>
                    <div 
                        className='absolute top-4 left-4 transition duration-300 ease-in-out hover:scale-110 hover:cursor-pointer'
                        onClick={() => setForm_status('infor')}
                    >
                        <IoReturnUpBackOutline size={30}/>
                    </div>
                    <p className='text-3xl text-center'>Add your questions</p>
                    <div className='flex justify-between items-center'>
                        <p>Total: {questions.length} questtion</p>
                        <button 
                            className='rounded text-white py-2 px-4 bg-[#8954C0]'
                            onClick={e => {
                                e.preventDefault()
                                setIsShownOverlay(true)
                            }}
                        >
                            Add question
                        </button>
                    </div>
                    {
                        questions.map((question, index) => (
                            <Questioncard key={index} setQuestions={setQuestions} question={question}/>
                        ))
                    }
                    <button 
                        // type='submit'
                        className='p-2 rounded bg-[#31F7C4]'
                        onClick={e => {
                            e.preventDefault()
                            // setForm_status('infor')
                            router.push('/teacher/1')
                        }}
                    >
                        Submit
                    </button>
                </>
            )}

            {isShownOverlay && (
                <div 
                    className='flex justify-center items-center fixed top-0 left-0 right-0 bottom-0 z-50 bg-[#bfdbfe80]'
                    onClick={e => {
                        e.preventDefault()
                        setIsShownOverlay(false)
                    }}
                >
                    <div 
                        className='container flex max-h-full items-center flex-col gap-4 p-4 rounded-xl shadow-xl bg-[#471A43] overflow-y-scroll md:overflow-y-auto'
                        onClick={e => e.stopPropagation()}
                    >
                       <div className='w-full'>
                        <textarea 
                            placeholder='Type your question here' 
                            rows={8} 
                            className='py-2 px-4 border border-[#764A92] w-full text-2xl text-white rounded-xl text-center bg-[#281326] caret-white outline-none' 
                            name="content" 
                            id="content"
                            onChange={e => {
                                setNewQuestion({
                                    ...newQuestion,
                                    content: e.target.value
                                })

                            }}
                        />
                       </div>
                        <div className='flex gap-4 p-4 w-full flex-wrap'>
                            <div className='bg-[#2B71AF] flex-1 rounded-xl flex flex-col p-3 gap-3'>
                                <input 
                                    className='self-end' 
                                    style={{
                                        width: '30px',
                                        height: '30px'
                                    }}
                                    type="radio" 
                                    name='correct' 
                                    onChange={e => {
                                        setNewQuestion({
                                            ...newQuestion,
                                            answer: newQuestion.options[0]
                                        })
                                    }}
                                />
                                <textarea 
                                    className='bg-transparent text-white outline-none text-white placeholder:text-white'
                                    rows={10} 
                                    name="answer1" 
                                    id="answer1" 
                                    placeholder='Type answer option here' 
                                    onChange={e => {
                                        const newOptions = newQuestion.options;
                                        newOptions[0] = e.target.value

                                        setNewQuestion({
                                            ...newQuestion,
                                            options: newOptions
                                        })
                                    }}
                                />
                            </div>
                            <div className='bg-[#289DA7] flex-1 rounded-xl flex flex-col p-3 gap-3'>
                                <input 
                                    style={{
                                        width: '30px',
                                        height: '30px'
                                    }}
                                    className='self-end' 
                                    type="radio" 
                                    name='correct' 
                                    onChange={e => {
                                        setNewQuestion({
                                            ...newQuestion,
                                            answer: newQuestion.options[1]
                                        })
                                    }}
                                />
                                <textarea 
                                    className='bg-transparent text-white outline-none text-white placeholder:text-white'
                                    rows={10} 
                                    name="answer2" 
                                    id="answer2" 
                                    placeholder='Type answer option here' 
                                    onChange={e => {
                                        const newOptions = newQuestion.options;
                                        newOptions[1] = e.target.value

                                        setNewQuestion({
                                            ...newQuestion,
                                            options: newOptions
                                        })
                                    }}
                                />
                            </div>
                            <div className='bg-[#F0A929] flex-1 rounded-xl flex flex-col p-3 gap-3'>
                                <input 
                                    style={{
                                        width: '30px',
                                        height: '30px'
                                    }}
                                    className='self-end' 
                                    type="radio" 
                                    name='correct' 
                                    onChange={e => {
                                        setNewQuestion({
                                            ...newQuestion,
                                            answer: newQuestion.options[2]
                                        })
                                    }}
                                />
                                <textarea 
                                    className='bg-transparent text-white outline-none text-white placeholder:text-white'
                                    rows={10} 
                                    name="answer3" 
                                    id="answer3" 
                                    placeholder='Type answer option here' 
                                    onChange={e => {
                                        const newOptions = newQuestion.options;
                                        newOptions[2] = e.target.value

                                        setNewQuestion({
                                            ...newQuestion,
                                            options: newOptions
                                        })
                                    }}
                                />
                            </div>
                            <div className='bg-[#D55470] flex-1 rounded-xl flex flex-col p-3 gap-3'>
                                <input 
                                    style={{
                                        width: '30px',
                                        height: '30px'
                                    }}
                                    className='self-end' 
                                    type="radio" 
                                    name='correct' 
                                    onChange={e => {
                                        setNewQuestion({
                                            ...newQuestion,
                                            answer: newQuestion.options[3]
                                        })
                                    }}
                                />
                                <textarea 
                                    className='bg-transparent text-white outline-none text-white placeholder:text-white'
                                    rows={10} 
                                    name="answer4" 
                                    id="answer4" 
                                    placeholder='Type answer option here' 
                                    onChange={e => {
                                        const newOptions = newQuestion.options;
                                        newOptions[3] = e.target.value

                                        setNewQuestion({
                                            ...newQuestion,
                                            options: newOptions
                                        })
                                    }}
                                />
                            </div>
                            
                        </div>
                        <button 
                            className='rounded text-white py-2 px-4 bg-[#8954C0]'
                            onClick={e => {
                                e.preventDefault()

                                if(!newQuestion.content || newQuestion.options.length < 4 || !newQuestion.answer) {
                                    alert('Please fill in all fields')
                                    
                                    return
                                }

                                setQuestions([...questions, newQuestion])
                                setNewQuestion({
                                    id: '',
                                    type: '',
                                    content: '',
                                    media_url: [],
                                    options: ['','','',''],
                                    answer: ''
                                })
                                setIsShownOverlay(false)
                            }}    
                        >
                            Save this question
                        </button>
                    </div>
                </div>
            )}
        </form>
    </div>
  )
}

export default page