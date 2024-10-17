import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Header.css';

function Header({ isLoggedIn, setIsLoggedIn }) {
    const navigate = useNavigate();

    return (
        <header> {/* 전체 헤더 컨테이너 */}
            <h1 onClick={() => { navigate("/"); }}>모해모그까🌱</h1>
            <div className="bar"> {/* 네비게이션 바 */}
                {isLoggedIn ? (
                    <span onClick={() => {
                        setIsLoggedIn(false);
                        alert("로그아웃되었습니다.");
                        navigate("/");
                    }}>로그아웃</span>
                ) : (
                    <>
                        <span onClick={() => navigate("/signup")}>회원가입</span>
                        <span onClick={() => navigate("/login")}>로그인</span>
                    </>
                )}
            </div>
        </header>
    );
}

export default Header;
