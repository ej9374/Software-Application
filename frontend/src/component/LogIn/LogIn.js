import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './LogIn.css';

function LogIn({setIsLoggedIn}) {
    const [id, setId] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleLogin = () => {
        if (id === "test" && password === "password"){
            setIsLoggedIn(true);
            navigate("/");
        }
    }

    return (
        <div className="login-container">
            <input
                type="text"
                placeholder="아이디"
                value={id}
                onChange={(e)=>setId(e.target.value)}
            />
            <input
                type="password"
                placeholder="비밀번호"
                value={password}
                onChange={(e)=>setPassword(e.target.value)}
            />
            <button onClick={handleLogin}>로그인</button>
        </div>
    );
}

export default LogIn;