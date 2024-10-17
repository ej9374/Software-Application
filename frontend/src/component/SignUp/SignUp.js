import React, { useState } from 'react';
import './SignUp.css';

function SignUp() {
    const [id, setId] = useState("");
    const [password, setPassword] = useState("");
    const [ratings, setRatings] = useState(Array(10).fill(""));

    const handleRatingChange = (index, value) => {
        const newRatings = [...ratings];
        newRatings[index] = value;
        setRatings(newRatings);
    };

    const handleSubmit = () => {
        alert("회원가입이 완료되었습니다.");
        // 여기에 DB 연결 로직 추가
    };

    return (
        <div className="signup-container">
            <label>아이디</label>
            <input
                type="text"
                value={id}
                onChange={(e) => setId(e.currentTarget.value)}
            />
            <label>비밀번호</label>
            <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.currentTarget.value)}
            />
            <div className="rating-container">
                {Array.from({ length: 10 }, (_, index) => (
                    <div key={index}>
                        <label>
                            레시피 {index + 1}
                            <select
                                value={ratings[index] === null ? "" : ratings[index]}
                                onChange={(e) => handleRatingChange(index, parseInt(e.target.value))}
                            >
                                <option value={null}>평점 선택</option>
                                {[0, 1, 2, 3, 4, 5].map(value => (
                                    <option key={value} value={value}>
                                        {value}
                                    </option>
                                ))}
                            </select>
                        </label>
                    </div>
                ))}
            </div>
            <button onClick={handleSubmit}>회원가입</button>
        </div>
    );
}

export default SignUp;
