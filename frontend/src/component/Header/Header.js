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
                    <span onClick={async () => {
                        const id = localStorage.getItem("userId");
                        const token = localStorage.getItem("accessToken");

                        try {
                            const response = await fetch(`/api/logout?id=${id}`, {
                                method: "POST",
                                headers: {
                                    "Content-Type": "application/json",
                                    Authorization: `Bearer ${token}`,
                                }
                            });

                            if (!response.ok) {
                                throw new Error("로그아웃 요청 실패");
                            }

                            localStorage.clear(); // 토큰 및 id 제거
                            setIsLoggedIn(false);
                            alert("로그아웃되었습니다.");
                            navigate("/");
                        } catch (error) {
                            console.error("로그아웃 중 오류:", error);
                            alert("로그아웃 중 오류가 발생했습니다.");
                        }
                    }}>
                        로그아웃
                    </span>

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
