import React, { useState, useEffect } from 'react';
import './SignUp.css';
import dummy from "../../DB/data.json";

function SignUp() {
    const [recipes, setRecipes] = useState([]);
    const [id, setId] = useState("");
    const [password, setPassword] = useState("");
    const [ratings, setRatings] = useState(Array(10).fill(""));

    useEffect(() => {
        // JSON 데이터에서 레시피를 가져와 상태에 설정
        setRecipes(dummy);
    }, []);

    const handleRatingChange = (index, value) => {
        const newRatings = [...ratings];
        newRatings[index] = value;
        setRatings(newRatings);
    };

    const handleSubmit = () => {
        alert("회원가입이 완료되었습니다.");
        // submitRatings(); // 평점 전송 함수 호출 (주석 처리)
    };

    // 평점 데이터 전송 - 프로토타입에서는 생략
    /*
    const submitRatings = () => {
        // 예시 URL - 실제 서버 URL로 대체
        const serverUrl = 'https://your-server-url.com/submit-ratings';

        // 평점 데이터 준비
        const ratingData = recipes.map((recipe, index) => ({
            recipe_id: recipe.recipe_id,
            rating: ratings[index],
        }));

        // 서버에 평점 데이터 전송
        fetch(serverUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ user_id: id, password: password, ratings: ratingData }),
        })
        .then(response => response.json())
        .then(data => {
            console.log('서버 응답:', data);
            // 성공적인 응답 처리
        })
        .catch(error => {
            console.error('평점 전송 중 오류 발생:', error);
        });
    };
    */

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
                {recipes.slice(0, 10).map((recipe, index) => (
                    <div key={index} className="recipe-rating">
                        <label>
                            <strong>{recipe.name}</strong>
                            <p>시간: {recipe.minutes}분</p>
                            <p>단계 수: {recipe.n_steps}</p>
                            <p>태그: {recipe.tags}</p>
                            <p>영양 정보: 칼로리 {recipe.nutrition.calories}, 지방 {recipe.nutrition.fat}, 단백질 {recipe.nutrition.protein}</p>
                            <select
                                value={ratings[index] === null ? "" : ratings[index]}
                                onChange={(e) => handleRatingChange(index, parseInt(e.target.value))}
                            >
                                <option value={null}>선호도 조사</option>
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
