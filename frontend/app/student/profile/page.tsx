'use client'

import { useSession } from 'next-auth/react'
import React, { useEffect } from 'react'

import * as axiosReq from '@/app/axios';
function Page() {
  const session = useSession();
    
  console.log('my session' ,session);

  if(session.status === 'authenticated'){ 
    // const res = axiosReq.get('/api/user/profile', {
    //   headers: {
    //     'Authorization': `Bearer ${session.data?.user?.accessToken}`,
    //     'Access-Control-Allow-Origin': '*'
    //   }
    // });

    fetch('http://localhost:8080/api/user/profile',
        {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${session.data?.user?.accessToken}`
            },
            method: 'GET',
        }
    ).then((response) => {
        return response.json()
    }).then((data) => {
        console.log('data', data);
    }).catch((error) => {
        console.log('error', error);
    })
  }

  return (
    <div className='py-[81px]'>

        Hlellooosoasod
    </div>
  )
}

export default Page