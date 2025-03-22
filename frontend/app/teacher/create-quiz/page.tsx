import React from 'react'

function page() {
  return (
    <div className='bg-[#2faffe] flex justify-center items-center text-white min-h-lvh'>
        <form 
            action=""
            className='flex flex-col gap-8 p-4 bg-white rounded container text-black shadow-xl'
        >
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

            <button className='p-2 rounded bg-[#31F7C4]' type='submit'>Create quizz!</button>
        </form>
    </div>
  )
}

export default page