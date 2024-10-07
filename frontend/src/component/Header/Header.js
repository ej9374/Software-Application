import React, { useState, useEffect } from 'react';
import { useNavigate  } from 'react-router-dom';


function Header() {

    const navigate = useNavigate();

    return (
        <div>
            <div>
                <h1 onClick={() => {
                    navigate("/");
                }}>모해모그까🌱</h1>
            </div>
            <div className = "bar">
                <span onClick={ () => {
                    navigate("./signup");
                }}>회원가입</span>
                <span onClick={ () => {
                    navigate("./login");
                }}>로그인</span>
            </div>
        </div>
    );
}

export default Header;