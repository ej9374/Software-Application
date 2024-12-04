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
                // 사용자 ID (예시로 1L 사용)
                const userId = 1; 

                // 백엔드에서 레시피 데이터를 가져오기
                const response = await fetch("/api/home", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(userId), // userId를 POST 바디로 전달
                });

                if (!response.ok) {
                    throw new Error("Failed to fetch recipes");
                }

                const data = await response.json();
                setRecipes(data); // 상태에 데이터 설정
            } catch (error) {
                setError(error.message);
                console.error("Failed to fetch recipes:", error);
            } finally {
                setLoading(false); // 로딩 상태 해제
            }
        };

        fetchRecipes();
    }, []);

    if (loading) {
        return <div>Loading recipes...</div>; // 로딩 중 메시지
    }

    if (error) {
        return <div>Error: {error}</div>; // 에러 메시지
    }

    if (recipes.length === 0) {
        return <div>No recipes found.</div>; // 레시피가 없을 때
    }

    return (
        <div className="container">
            {recipes.map((recipe) => (
                <Icon key={recipe.recipeId} recipe={recipe} />
            ))}
        </div>
    );
}

function Icon({ recipe }) {
    const navigate = useNavigate();

    return (
        <span
            className="icon"
            onClick={() => {
                navigate("/recipe", { state: { recipeId: recipe.recipeId } }); // recipeId 전달
            }}
        >
            <h4>{recipe.name}</h4>
            <p>Time: {recipe.minutes} minutes</p>
        </span>
    );
}

export default Home;
