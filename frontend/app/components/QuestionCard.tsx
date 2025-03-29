'use client'

import React, { useState } from 'react'
import { Question } from '@/lib/definitions'

function Questioncard({ question, setQuestions } : { question : Question, setQuestions : Function }) {
  const [isEdit, setIsEdit] = useState(false)

  return (
    <div className='flex flex-col'>
        <div className='flex justify-end gap-4 pb-3 flex-wrap'>
            <input 
                className='text-xl max-w-full md:text-3xl w-100 flex-1 px-4 text-wrap' disabled={!isEdit} 
                value={question.content}
                onChange={e => {
                    const content = e.target.value

                    setQuestions((prev : Question[]) => {
                        return prev.map(q => {
                            if (q.id === question.id) {
                                return {
                                    ...q,
                                    content
                                }
                            }
                            return q
                        })
                    })
                }}
            />
            <div className='flex gap-4'>
                <button 
                    className='flex-3 p-2 rounded bg-[#f73131]'
                    onClick={e => {
                        e.preventDefault()
                        setQuestions((prev : Question[]) => {
                            const newQuestions = [...prev]

                            newQuestions.splice(newQuestions.indexOf(question), 1)

                            return newQuestions
                            // prev.filter(q => q.id !== question.id)
                        })
                    }}
                >
                    Delete
                </button>
                {
                    isEdit? (
                        <button 
                            className='flex-3 p-2 rounded bg-[#31f767]'
                            onClick={e => {
                                e.preventDefault()
                                setIsEdit(!isEdit)
                            }}
                        >
                            Save changes
                        </button>
                    ) : (
                        <button 
                            className='flex-3 p-2 rounded bg-[#f7f131]'
                            onClick={e => {
                                e.preventDefault()
                                setIsEdit(!isEdit)
                            }}
                        >
                            Edit
                        </button>
                    )
                }
            </div>
            
        </div>
        {
            question.options.map((option, index) => (
                <input 
                    disabled={!isEdit} 
                    key={index} 
                    value={`${index + 1} - ${option}`}
                    onChange={e => {
                        const options = question.options
                        options[index] = e.target.value.split(' - ')[1] || ''
                        setQuestions((prev : Question[]) => {
                            return prev.map(q => {
                                if (q.id === question.id) {
                                    return {
                                        ...q,
                                        options
                                    }
                                }
                                return q
                            })
                        })
                    }}
                />
            ))
        }
        <div className='flex gap-3'>
            <label htmlFor="">Answer:</label>
            <select 
                disabled={!isEdit} 
                className='flex-1' 
                name="" 
                id=""
                onChange={e => {
                    const answer = e.target.value
                    setQuestions((prev : Question[]) => {
                        return prev.map(q => {
                            if (q.id === question.id) {
                                return {
                                    ...q,
                                    answer
                                }
                            }
                            return q
                        })
                    })
                }} 
            >
                {
                    question.options.map((option, index) => (
                        <option key={index} selected={option === question.answer} value={option}>{option}</option>
                    ))
                }
            </select>
        </div>
    </div>
  )
}

export default Questioncard