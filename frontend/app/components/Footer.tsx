import Image from "next/image"
import { FaFacebook } from "react-icons/fa";
import { FaInstagramSquare } from "react-icons/fa";
import { FaLinkedin } from "react-icons/fa";
import { FaTwitter } from "react-icons/fa";

import Logo from "@/public/Logo.png"

function footer() {
  return (
    <div className="bg-white fixed bottom-0 w-full">
        <footer className="container mx-auto flex justify-between items-center py-4">
            <div className="flex-1">
                <Image src={Logo} alt="Logo" width={100} height={100} />
            </div>
            <ul className="text-black flex flex-1 justify-between items-center">
                <li className="flex items-center"><a className="inline-block w-full py-2 px-4 " href="#"><FaFacebook size={30} /></a></li>
                <li className="flex items-center"><a className="inline-block w-full py-2 px-4 " href="#"><FaInstagramSquare size={30} /></a></li>
                <li className="flex items-center"><a className="inline-block w-full py-2 px-4 " href="#"><FaLinkedin size={30} /></a></li>
                <li className="flex items-center"><a className="inline-block w-full py-2 px-4 " href="#"><FaTwitter size={30} /></a></li>
            </ul>
        </footer>
    </div>
  )
}

export default footer