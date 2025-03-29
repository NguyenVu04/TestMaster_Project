'use client'

import { useSession } from 'next-auth/react'
import React, { useEffect } from 'react'


function page() {
  const session = useSession();
    
  console.log('my session' ,session);

  if(session.status === 'authenticated'){ 

    // fetch('https://02ea-171-253-40-101.ngrok-free.app/api/user/profile',
    //     {
    //         headers: {
    //             'Content-Type': 'application/json',
    //             'Authorization': `Bearer ${session.data?.user?.accessToken}`
    //         },
    //         method: 'GET',
    //     }
    // )
    // .then(res => {
    //     console.log(res.text())
    // })
    // // .then(data => {
    // //     console.log(data);
    // // })
    // .catch(err => {
    //     console.log(err);
    // })

  }

  return (
    <div className='py-[81px]'>

        Hlellooosoasod
    </div>
  )
}

export default page