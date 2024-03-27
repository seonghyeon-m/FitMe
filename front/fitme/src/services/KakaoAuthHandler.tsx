import axios from 'axios';
import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

const KakaoAuthHandler: React.FC = () => {
  const location = useLocation();
  const navigate = useNavigate();
  let count = 0;

  useEffect(() => {
    if (count === 0) {
      count++;
      console.log(count);
      const searchParams = new URLSearchParams(location.search);
      const code = searchParams.get('code');

      if (code) {
        axios
          .get('https://fit-me.site/api/auth/login/oauth2/code/kakao?code=' + code)
          .then((res) => {
            console.log(res.status);

            if (res.status === 200) {
              const refreshToken = res.headers.authorizationrefresh?.replace('Bearer', '').trim();
              const accessToken = res.headers.authorization?.replace('Bearer', '').trim();

              localStorage.setItem('refreshToken', refreshToken);
              localStorage.setItem('accessToken', accessToken);

              console.log('로그인 성공');
              navigate('/signup');
            } else {
              alert('로그인 오류');
              navigate('/home');
            }
          })
          .catch((error) => {
            console.log('에러');
            console.log(error);
          });
      }
    }
  }, [location]);

  return <div className='flex justify-center items-center h-full'>카카오 로그인 처리 중...</div>;
};

export default KakaoAuthHandler;
