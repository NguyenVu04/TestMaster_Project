import Image from "next/image";
import React from "react";

const BackgroundScreen = ({ children }: any) => {
  return (
    <div className="flex flex-1 flex-col items-center relative ">
      <Image
        src="/outline5.png"
        alt="logo"
        width={200}
        height={200}
        className="absolute top-20 left-0"
      />
      <Image
        src="/outline4.png"
        alt="logo"
        width={200}
        height={200}
        className="absolute right-10 bottom-20"
      />
      {children}
    </div>
  );
};

export default BackgroundScreen;
