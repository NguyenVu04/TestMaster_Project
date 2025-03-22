
function Home() {
  return (
    <div className="bg-[#2faffe] min-h-lvh flex justify-center items-center">
      <form action="" className="flex flex-col gap-2 p-4 bg-white rounded">
        <input type="text" placeholder="Enter your quizz code" className="py-2 px-4 border text-xl rounded focus:outline-[#0ffc8e]"/>
        <button type="submit" className="bg-[#31f7c4] text-xl p-4 rounded">Enter</button>
      </form>
    </div>
  )
}

export default Home