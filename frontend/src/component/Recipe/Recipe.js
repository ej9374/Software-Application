import React, { useEffect, useState } from 'react'; // useState 추가
import { useLocation, useNavigate } from 'react-router-dom'; // useNavigate 추가
import dummy from "../../DB/data.json";
import dummy2 from "../../DB/recommend.json";
import './Recipe.css'; 

function Recipe() {
    const { state } = useLocation();
    const { recipeId } = state || {}; // 클릭한 아이콘의 recipe_id를 가져옴

    if (!recipeId) {
        return <div>Loading...</div>;
    }

    // recipeId에 해당하는 레시피 찾기
    const recipe = dummy.find(item => item.recipe_id === recipeId);

    if (!recipe) {
        return <div>레시피를 찾을 수 없습니다.</div>;
    }

    return (
        <div>
            <div className="recipe-container">
                <h2>{recipe.name}</h2>
                <p>Time: {recipe.minutes} minutes</p>
                <p>Description: {recipe.description}</p>
                <p>Tags: {recipe.tags}</p>
                <p>Cooking Steps: {recipe.steps}</p>
                <p>Ingredients: {recipe.ingredients}</p>
            </div>
            <Recommend recipe={recipe} />
        </div>
    );
}

function Recommend({ recipe }) {
    const [recipes, setRecipes] = useState([]);

    useEffect(() => {
        setRecipes(dummy2); // 추천 데이터 설정
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
                navigate("./recipe", { state: { recipeId: recipe.recipe_id } }); // recipeId만 전달
            }}
        >
            <h4>{recipe.name}</h4>
        </span>
    );
}

export default Recipe;
