import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./LogIn.css";

function LogIn({ setIsLoggedIn }) {
    const [id, setId] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleLogin = async () => {
        try {
            // 서버로 ID와 비밀번호 전송
            const response = await fetch("/api/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ id, password }), // JSON으로 ID와 비밀번호 전달
            });

            if (response.ok) {
                const token = await response.text(); // 서버에서 반환한 JWT 토큰
                localStorage.setItem("token", token); // 토큰 저장
                setIsLoggedIn(true);
                navigate("/"); // 로그인 성공 시 메인 페이지로 이동
            } else {
                const message = await response.text();
                setError(`로그인 실패: ${message}`);
            }
        } catch (err) {
            setError("서버 오류 발생");
        }
    };

    return (
        <div className="login-container">
            <input
                type="text"
                placeholder="아이디"
                value={id}
                onChange={(e) => setId(e.target.value)}
            />
            <input
                type="password"
                placeholder="비밀번호"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button onClick={handleLogin}>로그인</button>
            {error && <p className="error">{error}</p>} {/* 오류 메시지 표시 */}
        </div>
    );
}

export default LogIn;
