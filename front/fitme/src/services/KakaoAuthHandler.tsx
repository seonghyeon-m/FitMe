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
        // console.log('받은 코드:', code);
        // const AXIOS_URL = 'https://fit-me.site/api/auth/login/oauth2/code/kakao?code=' + code;
        // console.log(AXIOS_URL);

        axios
          .get('https://fit-me.site/api/auth/login/oauth2/code/kakao?code=' + code)
          .then((res) => {
            console.log(res.status);

            if (res.status === 200) {
              const refreshToken = res.headers.authorizationrefresh?.replace('Bearer', '').trim();
              const accessToken = res.headers.authorization?.replace('Bearer', '').trim();

              console.log('refresh 토큰 :', refreshToken);
              console.log('access 토큰 :', accessToken);

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
            console.log('안돼');
            console.log(error);
          });
      }

      console.log('끝까지 왔다');
    }
  }, [location]);

  return <div>카카오 로그인 처리 중...</div>;
};

export default KakaoAuthHandler;
