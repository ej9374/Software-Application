import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './SignUp.css';

function SignUp() {
    const [recipes, setRecipes] = useState([]);
    const [id, setId] = useState("");
    const [password, setPassword] = useState("");
    const [ratings, setRatings] = useState(Array(10).fill(""));
    const navigate = useNavigate();

    useEffect(() => {
        const fetchRecipes = async () => {
            try {
                const response = await fetch('/api/random');
                if (!response.ok) {
                    throw new Error(`Error fetching recipes: ${response.statusText}`);
                }
                const result = await response.json();
                setRecipes(result.data);
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

        submitRatings();
    };

    const submitRatings = async () => {
        const ratingData = recipes.map((recipe, index) => ({
            recipeId: recipe.recipeId, // 수정된 필드명
            rating: ratings[index],
        }));        

        try {
            const response = await fetch('/api/signup', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ 
                    id: id, 
                    password, 
                    ratings: ratingData 
                }),
            });

            if (!response.ok) {
                const errorMessage = await response.text();
                throw new Error(`Error submitting ratings: ${response.statusText}`);
            }
            const result = await response.json();
            console.log("회원가입 응답:", result);
            alert(result.message); 
            navigate("/login");
        } catch (error) {
            console.error('Error submitting ratings:', error);
            alert("회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
        }
    };

    return (
        <div className="signup-container">
            <h2>회원가입</h2>
            <label>아이디</label>
            <input
                type="text"
                value={id}
                onChange={(e) => setId(e.target.value)}
                placeholder="아이디를 입력하세요"
            />
            <label>비밀번호</label>
            <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="비밀번호를 입력하세요"
            />
            <label>레시피 선호도 조사</label>
            <div className="rating-container">
                {recipes.map((recipe, index) => (
                    <div key={recipe.recipeId} className="recipe-card">
                        <h3>{recipe.name}</h3>
                        <p>조리 시간: {recipe.minutes}분</p>
                        <p>태그: {recipe.tags}</p>
                        <p>재료: {recipe.ingredients}</p>
                        <select
                            value={ratings[index] === "" ? "" : ratings[index]}
                            onChange={(e) => handleRatingChange(index, parseInt(e.target.value, 10))}
                        >
                            <option value="">선호도 선택</option>
                            {[0, 1, 2, 3, 4, 5].map(value => (
                                <option key={value} value={value}>{value}</option>
                            ))}
                        </select>
                    </div>
                ))}
            </div>
            <button onClick={handleSubmit}>회원가입</button>
        </div>
    );
}

export default SignUp;