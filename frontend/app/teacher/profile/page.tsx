'use client'

import { useSession } from 'next-auth/react'
import React, { useEffect } from 'react'

import * as axiosReq from '@/app/axios';
import { Userinfor } from '@/app/interface/userInfor';
function Page() {
  const session = useSession();

  const [userInfo, setUserInfo] = React.useState({} as Userinfor);
    
  const [newProfile, setNewProfile] = React.useState({} as Userinfor);

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
        setUserInfo(data);
        // console.log('data', data);
    }).catch((error) => {
        console.log('error', error);
    })
  }

  console.log('userInfo', userInfo)

  return (
    <div className='py-[81px] w-full'>
        {
            userInfo? (
                <form action="" className='container mx-auto shadow-2xl p-6'>
                    <div className='flex flex-col text-black mb-12 text-3xl'>
                        <label htmlFor="">First name</label>
                        <input type="text" className='border px-4 py-2' defaultValue={userInfo.firstName || ''}
                            onChange={e => {
                                setNewProfile({
                                    ...newProfile,
                                    firstName: e.target.value
                                })
                            }}
                        />
                    </div>

                    <div className='flex flex-col text-black mb-12 text-3xl'>
                        <label htmlFor="">Last name</label>
                        <input type="text" className='border px-4 py-2' defaultValue={userInfo.lastName || ''}
                            onChange={e => {
                                setNewProfile({
                                    ...newProfile,
                                    lastName: e.target.value
                                })
                            }}
                        />
                    </div>

                    <div className='flex flex-col text-black mb-12 text-3xl'>
                        <label htmlFor="">Email</label>
                        <input type="text" className='border px-4 py-2' defaultValue={userInfo.email || ''}
                            onChange={e => {
                                setNewProfile({
                                    ...newProfile,
                                    email: e.target.value
                                })
                            }}
                        />
                    </div>

                    <div className='flex flex-col text-black mb-12 text-3xl'>
                        <label htmlFor="">Phone number</label>
                        <input type="text" className='border px-4 py-2' defaultValue={userInfo.phoneNumber || ''}
                            onChange={e => {
                                setNewProfile({
                                    ...newProfile,
                                    phoneNumber: e.target.value
                                })
                            }}
                        />
                    </div>

                    <button 
                        className='bg-[#2DFF9C] px-4 py-2 rounded-md mx-auto block'
                        onClick={e => {

                            e.preventDefault()
                        }}
                    >
                        Update
                    </button>
                </form>


            ) : (
                <p>loading......</p>
            )

        }
    </div>
  )
}

export default Page