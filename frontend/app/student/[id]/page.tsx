
function Home() {
  return (
    <div className="bg-[#2faffe] min-h-lvh flex justify-center items-center">
      <form action="" className="flex flex-col">
        <label htmlFor="quizz_code">Quizz code</label>
        <input type="text" placeholder="Enter your quizz code" className="py-2 px-4 border rounded focus:outline-[#0ffc8e]"/>
      </form>
    </div>
  )
}

export default Home