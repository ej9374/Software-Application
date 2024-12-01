import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./Home.css";

function Home() {
    const [recipes, setRecipes] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchRecipes = async () => {
            try {
                // 백엔드에서 레시피 데이터를 가져오기
                const response = await fetch("/api/recipes/list", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify([]), // 빈 리스트를 전송하면 백엔드가 기본값으로 처리
                });

                if (!response.ok) {
                    throw new Error("Failed to fetch recipes");
                }

                const data = await response.json();
                setRecipes(data); // 상태에 데이터 설정
            } catch (error) {
                console.error("Failed to fetch recipes:", error);
            }
        };

        fetchRecipes();
    }, []);

    if (recipes.length === 0) {
        return <div>Loading...</div>;
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
