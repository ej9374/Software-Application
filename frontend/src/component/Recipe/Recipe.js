import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "./Recipe.css";

function Recipe() {
    const { state } = useLocation();
    const { recipeId } = state || {};
    const [recipe, setRecipe] = useState(null);
    const navigate = useNavigate();
    
    useEffect(() => {
        if (!recipeId) {
            console.error("No recipeId provided in state.");
            return;
        }

        const fetchRecipeDetails = async () => {
            try {
                const token = localStorage.getItem("accessToken");

                const response = await fetch(`/api/recipes/${recipeId}`, {
                    method: "GET",
                    headers: {
                        Authorization: `Bearer ${token}`, // ✅ 인증
                    }
                });

                if (!response.ok) {
                    if (response.status === 401 || response.status === 403) {
                        localStorage.clear();
                        navigate("/login");
                        return;
                    }
                    throw new Error("Failed to fetch recipe details");
                }

                const result = await response.json();
                console.log("Fetched Recipe Details:", result.data);
                setRecipe(result.data);
            } catch(error){
                console.error("Failed to fetch recipe details:", error);
            }
        };

        fetchRecipeDetails();
    }, [recipeId]);

    if (!recipe) {
        return <div>Loading...</div>;
    }

    return (
        <div className="recipe-page">
            <div className="recipe-header">
                <h1>{recipe.name}</h1>
                <p className="recipe-time">⏱ Time: {recipe.minutes} minutes</p>
            </div>

            <div className="recipe-content">
                <section className="recipe-description">
                    <h2>Description</h2>
                    <p>{recipe.description}</p>
                </section>

                <section className="recipe-tags">
                    <h2>Tags</h2>
                    <ul>
                        {recipe.tags.split(",").map((tag, index) => (
                            <li key={index}>{tag.trim()}</li>
                        ))}
                    </ul>
                </section>

                <section className="recipe-nutrition">
                    <h2>Nutrition</h2>
                    <p>Calories: {recipe.nutrition.split(",")[0]}</p>
                    <p>Fat: {recipe.nutrition.split(",")[1]}g</p>
                    <p>Protein: {recipe.nutrition.split(",")[2]}g</p>
                    <p>Carbs: {recipe.nutrition.split(",")[3]}g</p>
                    <p>Sugar: {recipe.nutrition.split(",")[4]}g</p>
                </section>

                <section className="recipe-steps">
                    <h2>Steps</h2>
                    <ol>
                        {recipe.steps.split(",").map((step, index) => (
                            <li key={index}>{step.trim()}</li>
                        ))}
                    </ol>
                </section>

                <section className="recipe-ingredients">
                    <h2>Ingredients</h2>
                    <ul>
                        {recipe.ingredients.split(",").map((ingredient, index) => (
                            <li key={index}>{ingredient.trim()}</li>
                        ))}
                    </ul>
                </section>
            </div>

            <Rating recipeId={recipeId} />
            <Recommend recipeId={recipeId} />
        </div>
    );
}

function Rating({ recipeId }) {
    const [rating, setRating] = useState(0);
    const [submitted, setSubmitted] = useState(false);

    const handleRatingSubmit = async () => {
        try {
            await fetch("/api/rate-recipe", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ recipeId, rating }),
            });
            setSubmitted(true);
        } catch (error) {
            console.error("Failed to submit rating", error);
        }
    };

    return (
        <div className="rating-container">
            <h3>Rate this Recipe</h3>
            <div className="rating-buttons">
                {[1, 2, 3, 4, 5].map((value) => (
                    <button
                        key={value}
                        onClick={() => setRating(value)}
                        className={value === rating ? "selected" : ""}
                        disabled={submitted}
                    >
                        {value}
                    </button>
                ))}
            </div>
            <button
                className="submit-rating"
                onClick={handleRatingSubmit}
                disabled={submitted || rating === 0}
            >
                {submitted ? "Thank you for rating!" : "Submit Rating"}
            </button>
        </div>
    );
}

function Recommend({ recipeId }) {
    const [recipes, setRecipes] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        if (!recipeId) {
            console.error("No recipeId provided.");
            return;
        }

        console.log("Sending recipeId to server:", recipeId);

        const fetchRecommendations = async () => {
            try {
                const token = localStorage.getItem("token");

                const response = await fetch("/api/recipes/content-based", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${token}`, // ✅ 토큰 포함
                    },
                    body: JSON.stringify(recipeId),
                });

                if (!response.ok) {
                    if (response.status === 401 || response.status === 403) {
                        localStorage.clear();
                        navigate("/login");
                        return;
                    }
                    throw new Error(`Error fetching recommendations: ${response.statusText}`);
                }

                const result = await response.json();

                if (!response.ok) {
                    throw new Error(`Error fetching recommendations: ${response.statusText}`);
                }
                setRecipes(result.data);
            } catch (error) {
                console.error("Failed to fetch recommendations:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchRecommendations();
    }, [recipeId]); // recipeId가 변경될 때마다 호출

    if (loading) {
        return <div>Loading recommendations...</div>;
    }

    if (recipes.length === 0) {
        return <div>No recommendations found.</div>;
    }

    return (
        <div className="recommend-section">
            <h3>Recommended Recipes</h3>
            <div className="recommendations">
                {recipes.map((rec) => (
                    <Icon key={rec.recipeId} recipe={rec} navigate={navigate} />
                ))}
            </div>
        </div>
    );
}

function Icon({ recipe, navigate }) {
    return (
        <div
            className="recommend-icon"
            onClick={() => {
                navigate("/recipe", { state: { recipeId: recipe.recipeId } }); // recipeId 전달
                window.scrollTo(0, 0); // 페이지 최상단으로 스크롤
            }}
        >
            <h4>{recipe.name}</h4>
            <p>⏱ {recipe.minutes} minutes</p>
        </div>
    );
}

export default Recipe;
