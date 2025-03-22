'use client';

import React, { useState } from 'react'
import { Question } from '@/lib/definitions';
import Questioncard from '@/app/components/QuestionCard';

function page() {
  const [form_status, setForm_status] = useState('question')
  const [isShownOverlay, setIsShownOverlay] = useState(false)
  const [isEdit, setIsEdit] = useState(false)
  const [newQuestion, setNewQuestion] = useState({
    id: '',
    type: '',
    content: '',
    media_url: [],
    options: ['','','',''],
    answer: ''
  } as Question)
  const [questions, setQuestions] = useState([
    {
      id: '1',
      type: 'text',
      content: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam, quas.',
      media_url: [],
      options: ['Lorem1', 'Lorem2', 'Lorem3', 'Lorem4'],
      answer: 'Lorem1'
    },
    {
      id: '2',
      type: 'text',
      content: 'asdflaksdjfhlkjashdadsf',
      media_url: [],
      options: ['A1', 'A2', 'A3', 'A4'],
      answer: 'A3'
    }
  ] as Question[])

  return (
    <div className='bg-[#2faffe] flex justify-center items-center text-white min-h-lvh'>
        <form 
            action=""
            className='flex flex-col gap-8 p-4 bg-white rounded container text-black shadow-xl max-h-[700px]'
        >
            {form_status === 'infor' && (
                <>
                    <div className='flex flex-col'>
                        <label className='text-3xl' htmlFor="title">Quizz title</label>
                        <input className='py-2 px-4 border text-xl rounded' type="text" name="title" id="title" placeholder='Enter your quizz title' />
                    </div>

                    <div className='flex flex-col'>
                        <label className='text-3xl' htmlFor="description">Quizz description</label>
                        <textarea className='py-2 px-4 border text-xl rounded' rows={5} name="description" id="title" placeholder='Enter your quizz description' />
                    </div>
                    
                    <div className='flex flex-col'>
                        <label className='text-3xl' htmlFor="passcode">Quizz passcode</label>
                        <input className='py-2 px-4 border text-xl rounded' type="text" name="passcode" id="passcode" placeholder='Enter your quizz passcode' />
                    </div>
                    
                    <div className='flex flex-col'>
                        <label className='text-3xl' htmlFor="time_limit">Quizz time limit</label>
                        <input className='py-2 px-4 border text-xl rounded' type="text" name="time_limit" id="time_limit" placeholder='Enter your quizz time limit (in minute)' />
                    </div>
                    
                    <div className='flex flex-col'>
                        <label className='text-3xl' htmlFor="date">Quizz date</label>
                        <input className='py-2 px-4 border text-xl rounded' type="date" name="date" id="date" placeholder='Chose your quizz start date' />
                    </div>

                    <button 
                        className='p-2 rounded bg-[#31F7C4]' 
                        type='submit'
                        onClick={() => setForm_status('question')}
                    >
                        Create quizz!
                    </button>
                </>
            )}

            {form_status === 'question' && (
                <>
                    <p className='text-3xl text-center'>Add your questions</p>
                    <div className='flex justify-between items-center'>
                        <p>Total: {questions.length} questtion</p>
                        <button 
                            className='p-2 rounded bg-[#31F7C4]'
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
                            <Questioncard key={index} question={question}/>
                        ))
                    }
                </>
            )}

            {isShownOverlay && (
                <div className='flex justify-center items-center fixed w-100 h-100 top-0 left-0 right-0 bottom-0 z-50 bg-[#bfdbfe80]'>
                    <div className='container flex justify-center items-center flex-col gap-4 p-4 rounded-xl shadow-xl bg-[#EDE8E3]'>
                        <textarea 
                            placeholder='Type question heare' 
                            rows={5} 
                            className='py-2 px-4 border text-xl rounded text-center w-full outline-none' 
                            name="content" 
                            id="content"
                            onChange={e => {
                                setNewQuestion({
                                    ...newQuestion,
                                    content: e.target.value
                                })

                            }}
                        />
                        <div className='flex gap-4 p-4'>
                            <div className='bg-[#31F7C4] rounded flex flex-col p-3 gap-3'>
                                <input 
                                    className='self-end' 
                                    type="radio" 
                                    name='correct' 
                                    id='correct'
                                    onChange={e => {
                                        setNewQuestion({
                                            ...newQuestion,
                                            answer: newQuestion.options[0]
                                        })
                                    }}
                                />
                                <textarea 
                                    rows={5} 
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
                            <div className='bg-[#31F7C4] rounded flex flex-col p-3 gap-3'>
                                <input 
                                    className='self-end' 
                                    type="radio" 
                                    name='correct' 
                                    id='correct'
                                    onChange={e => {
                                        setNewQuestion({
                                            ...newQuestion,
                                            answer: newQuestion.options[1]
                                        })
                                    }}
                                />
                                <textarea 
                                    rows={5} 
                                    name="answer1" 
                                    id="answer1" 
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
                            <div className='bg-[#31F7C4] rounded flex flex-col p-3 gap-3'>
                                <input 
                                    className='self-end' 
                                    type="radio" 
                                    name='correct' 
                                    id='correct'
                                    onChange={e => {
                                        setNewQuestion({
                                            ...newQuestion,
                                            answer: newQuestion.options[2]
                                        })
                                    }}
                                />
                                <textarea 
                                    rows={5} 
                                    name="answer1" 
                                    id="answer1" 
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
                            <div className='bg-[#31F7C4] rounded flex flex-col p-3 gap-3'>
                                <input 
                                    className='self-end' 
                                    type="radio" 
                                    name='correct' 
                                    id='correct'
                                    onChange={e => {
                                        setNewQuestion({
                                            ...newQuestion,
                                            answer: newQuestion.options[3]
                                        })
                                    }}
                                />
                                <textarea 
                                    rows={5} 
                                    name="answer1" 
                                    id="answer1" 
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
                            className='p-2 rounded bg-[#31F7C4]'
                            onClick={e => {


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