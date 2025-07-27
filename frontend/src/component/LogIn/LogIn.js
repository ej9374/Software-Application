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
            const response = await fetch(`/api/signin?id=${id}&password=${password}`, {
                method: "POST",
            });

            if (response.ok) {
                const result = await response.json()
               // 백엔드가 보내준 구조에 맞게 추출
                const { accessToken, refreshToken } = result.data;

                // localStorage에 저장
                localStorage.setItem("accessToken", accessToken);
                localStorage.setItem("refreshToken", refreshToken);
                localStorage.setItem("userId", id);

                setIsLoggedIn(true);
                navigate("/"); // Redirect to the homepage
            } else {
                const message = await response.text();
                setError(`Login failed: ${message}`);
            }
        } catch (err) {
            setError("An error occurred while connecting to the server.");
        }
    };

    return (
        <div className="login-container">
            <input
                type="text"
                placeholder="User ID"
                value={id}
                onChange={(e) => setId(e.target.value)}
            />
            <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button onClick={handleLogin}>Login</button>
            {error && <p className="error">{error}</p>}
        </div>
    );
}

export default LogIn;
