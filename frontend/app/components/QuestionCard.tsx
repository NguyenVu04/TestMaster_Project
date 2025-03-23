'use client'

import React, { useState } from 'react'
import { Question } from '@/lib/definitions'

function Questioncard({ question } : { question : Question }) {
  const [isEdit, setIsEdit] = useState(false)

  return (
    <div className='flex flex-col'>
        <div className='flex justify-between gap-4 pb-3'>
            <input className='text-3xl w-100 flex-1 px-4' disabled={!isEdit} value={question.content}/>
            <button 
                className='flex-3 p-2 rounded bg-[#f7f131]'
                onClick={e => {
                    e.preventDefault()
                    setIsEdit(!isEdit)
                }}
            >
                Edit
            </button>
        </div>
        {
            question.options.map((option, index) => (
                <input disabled={!isEdit} key={index} value={`${index + 1} - ${option}`}/>
            ))
        }
        <input disabled={!isEdit} value={`Answer: ${question.answer}`}/>
    </div>
  )
}

export default Questioncard