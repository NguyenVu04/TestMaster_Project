'use client'

import { useSession } from 'next-auth/react'
import React, { useEffect } from 'react'

import * as axiosReq from '@/app/axios';
function page() {
  const session = useSession();
    
  console.log('my session' ,session);

  if(session.status === 'authenticated'){ 
    // const res = axiosReq.get('/api/user/profile', {
    //   headers: {
    //     'Authorization': `Bearer ${session.data?.user?.accessToken}`,
    //     'Access-Control-Allow-Origin': '*'
    //   }
    // });


    // console.log('res', res);

    fetch('https://b8c6-171-253-40-101.ngrok-free.app/api/user/profile',
        {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${session.data?.user?.accessToken}`
            },
            method: 'GET',
        }
    )
    .then(res => {
        console.log('res', res);
        console.log(res.text())
        // return res.json()
    })
    // .then(data => {
    //     console.log(data);
    // })
    .catch(err => {
        console.log(err);
    })



  }

  return (
    <div className='py-[81px]'>

        Hlellooosoasod
    </div>
  )
}

export default page