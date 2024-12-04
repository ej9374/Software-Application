import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './SignUp.css';

function SignUp() {
    const [recipes, setRecipes] = useState([]);
    const [id, setId] = useState("");
    const [password, setPassword] = useState("");
    const [ratings, setRatings] = useState(Array(10).fill("")); // 초기 상태: 빈 값
    const navigate = useNavigate();

    // 서버에서 랜덤으로 10개의 레시피 가져오기
    useEffect(() => {
        const fetchRecipes = async () => {
            try {
                const response = await fetch('/api/random'); // 서버의 API 엔드포인트 호출
                if (!response.ok) {
                    throw new Error(`Error fetching recipes: ${response.statusText}`);
                }
                const data = await response.json();
                setRecipes(data); // 상태에 서버에서 가져온 데이터 설정
            } catch (error) {
                console.error('Error fetching random recipes:', error);
                alert("랜덤 레시피를 불러오는 중 오류가 발생했습니다.");
            }
        };

        fetchRecipes();
    }, []);

    const handleRatingChange = (index, value) => {
        const newRatings = [...ratings];
        newRatings[index] = value;
        setRatings(newRatings);
    };

    const handleSubmit = () => {
        if (id === "" || password === "") {
            alert("아이디와 비밀번호를 입력해주세요.");
            return;
        }
        if (ratings.some(rating => rating === "")) {
            alert("모든 레시피의 선호도를 선택해주세요.");
            return;
        }

        submitRatings(); // 평점 전송 함수 호출
    };

    const submitRatings = async () => {
        const serverUrl = '/api/signup';

        // 평점 데이터 준비
        const ratingData = recipes.map((recipe, index) => ({
            recipe_id: recipe.recipeId, // 서버에서 반환한 recipeId
            rating: ratings[index],
        }));

        try {
            const response = await fetch(serverUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ user_id: id, password: password, ratings: ratingData }),
            });

            if (!response.ok) {
                throw new Error(`Error submitting ratings: ${response.statusText}`);
            }

            const data = await response.json();
            console.log('서버 응답:', data);
            alert("회원가입이 완료되었습니다.");
            navigate("/login");
        } catch (error) {
            console.error('평점 전송 중 오류 발생:', error);
            alert("회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
        }
    };

    return (
        <div className="signup-container">
            <label>아이디</label>
            <input
                type="text"
                value={id}
                onChange={(e) => setId(e.currentTarget.value)}
                placeholder="아이디를 입력하세요"
            />
            <label>비밀번호</label>
            <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.currentTarget.value)}
                placeholder="비밀번호를 입력하세요"
            />
            <label>선호도 조사</label>
            <div className="rating-container">
                {recipes.slice(0, 10).map((recipe, index) => (
                    <div key={recipe.recipeId} className="recipe-rating">
                        <label>
                            <strong>{recipe.name}</strong>
                            <p>시간: {recipe.minutes}분</p>
                            <p>단계 수: {recipe.nSteps}</p>
                            <p>태그: {recipe.tags}</p>
                            <p>영양 정보: {recipe.nutrition}</p>
                            <select
                                value={ratings[index] === "" ? "" : ratings[index]}
                                onChange={(e) => handleRatingChange(index, parseInt(e.target.value, 10))}
                            >
                                <option value="">선호도 선택</option>
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
