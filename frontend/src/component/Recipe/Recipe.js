import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "./Recipe.css";
import dummy2 from "../../DB/recommend.json";

function Recipe() {
    const { state } = useLocation();
    const { recipeId } = state || {};
    const [recipe, setRecipe] = useState(null);

    useEffect(() => {
        if (!recipeId) return;

        const fetchRecipeDetails = async () => {
            try {
                const response = await fetch(`/api/recipes/${recipeId}`);
                const data = await response.json();
                setRecipe(data);
            } catch (error) {
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
            <Recommend />
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


function Recommend() {
    const [recipes, setRecipes] = useState([]);

    useEffect(() => {
        // dummy2 데이터를 상태로 설정
        setRecipes(dummy2);
    }, []);

    if (recipes.length === 0) {
        return <div>Loading recommendations...</div>;
    }

    return (
        <div className="recommend-section">
            <h3>유사한 레시피</h3>
            <div className="recommendations">
                {recipes.map((rec) => (
                    <Icon key={rec.recipe_id} recipe={rec} />
                ))}
            </div>
        </div>
    );
}

function Icon({ recipe }) {
    const navigate = useNavigate();

    return (
        <div
            className="recommend-icon"
            onClick={() => {
                navigate("/recipe", { state: { recipeId: recipe.recipe_id } });
            }}
        >
            <h4>{recipe.name}</h4>
            <p>⏱ {recipe.minutes} minutes</p>
        </div>
    );
}

export default Recipe;
