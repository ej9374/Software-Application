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
            const response = await fetch("/api/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ id, password }),
            });

            if (response.ok) {
                const { token, userId } = await response.json();
                localStorage.setItem("token", token); // Save JWT
                localStorage.setItem("userId", userId); // Save userId
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
