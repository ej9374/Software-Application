import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./Home.css";

function Home() {
    const [recipes, setRecipes] = useState([]); // 레시피 데이터 상태
    const [loading, setLoading] = useState(true); // 로딩 상태
    const [error, setError] = useState(null); // 에러 상태
    const navigate = useNavigate();

    useEffect(() => {
        const fetchRecipes = async () => {
            try {
                const userId = localStorage.getItem("userId"); // 로컬 스토리지에서 userId 가져오기
                const token = localStorage.getItem("token");   // 로컬 스토리지에서 JWT 토큰 가져오기

                if (!token || !userId) {
                    navigate("/login");
                    return;
                }

                // JWT 토큰 유효성 검증 요청
                const validateResponse = await fetch("/api/validate", {
                    method: "GET",
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

                if (!validateResponse.ok) {
                    localStorage.clear(); // 토큰이 유효하지 않으면 스토리지 초기화
                    navigate("/login");
                    return;
                }

                // 추천 레시피 요청
                const response = await fetch("/api/home", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                    body: JSON.stringify(parseInt(userId)), // userId를 숫자로 전송
                });

                if (!response.ok) {
                    throw new Error("Failed to fetch recipes");
                }

                const data = await response.json(); // 서버 응답 JSON 파싱
                setRecipes(data); // 상태 업데이트
            } catch (error) {
                setError(error.message); // 에러 상태 업데이트
            } finally {
                setLoading(false); // 로딩 상태 해제
            }
        };

        fetchRecipes();
    }, [navigate]);

    if (loading) return <div>Loading recipes...</div>;
    if (error) return <div>Error: {error}</div>;
    if (recipes.length === 0) return <div>No recipes found.</div>;

    return (
        <div className="container">
            {recipes.map((recipe) => (
                <RecipeCard key={recipe.recipeId} recipe={recipe} />
            ))}
        </div>
    );
}

function RecipeCard({ recipe }) {
    const navigate = useNavigate();

    return (
        <div
            className="recipe-card"
            onClick={() => navigate("/recipe", { state: { recipeId: recipe.recipeId } })}
        >
            <h4>{recipe.name}</h4>
            <p>Cooking Time: {recipe.minutes} minutes</p>
        </div>
    );
}

export default Home;
