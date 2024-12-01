import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './Recipe.css';
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
        <div>
            <div className="recipe-container">
                <h2>{recipe.name}</h2>
                <p>Time: {recipe.minutes} minutes</p>
                <p>Description: {recipe.description}</p>
                <p>Tags: {recipe.tags}</p>
                <p>Nutrition: {recipe.nutrition}</p>
                <p>Steps: {recipe.steps}</p>
                <p>Ingredients: {recipe.ingredients}</p>
                <p>Number of Ingredients: {recipe.nIngredients}</p>
            </div>
            <Rating recipeId={recipeId} />
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
            <button onClick={handleRatingSubmit} disabled={submitted || rating === 0}>
                {submitted ? "Thank you for rating!" : "Submit Rating"}
            </button>
        </div>
    );
}


function Recommend({ recipe }) {
    const [recipes, setRecipes] = useState([]);

    useEffect(() => {
        setRecipes(dummy2);
    }, []);

    return (
        <div>
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
        <span className="icon" 
            onClick={() => {
                navigate("/recipe", { state: { recipeId: recipe.recipe_id } });
            }}
        >
            <h4>{recipe.name}</h4>
        </span>
    );
}


export default Recipe;
