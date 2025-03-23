import Logo from "@/public/Logo.png";
import Image from "next/image";
import Link from "next/link";

function Header() {
  return (
    <div
      className="bg-white sticky top-0 w-full z-50 shadow-md"
      // style={{ backgroundColor: "#1A202C" }}
    >
      <nav className="container mx-auto flex justify-between items-center py-4 ">
        <div className="flex-1">
          <Link href="/">
            <Image
              src={Logo}
              alt="Logo"
              width={100}
              height={100}
              className="transition-transform duration-300 ease-in-out hover:scale-120"
            />
          </Link>
        </div>
        <ul className="flex justify-between items-center flex-1 gap-8 text-[#E0E0E0]">
          <li className="flex items-center">
            <a className="inline-block w-full p-4 hover:text-gray-400" href="#">
              How it work
            </a>
          </li>
          <li className="flex items-center">
            <a className="inline-block w-full p-4 hover:text-gray-400" href="#">
              Features
            </a>
          </li>
          <li className="flex items-center">
            <a className="inline-block w-full p-4 hover:text-gray-400" href="#">
              About us
            </a>
          </li>
          <li className="flex items-center">
            <Link className="inline-block w-full py-4 px-6 border border-[#31F7C4] text-[#31F7C4]" href="/auth/login">Login</Link>
          </li>
        </ul>
      </nav>
    </div>
  );
}

export default Header;
