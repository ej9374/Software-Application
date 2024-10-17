import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import dummy from "../../DB/data.json";
import './Home.css';

function Home() {
    const [recipes, setRecipes] = useState([]);

    useEffect(() => {
        // JSON 데이터에서 레시피를 가져와 상태에 설정
        setRecipes(dummy);
    }, []);

    return (
        <div className="container"> {/* container 클래스 추가 */}
            {recipes.map((recipe) => (
                <Icon key={recipe.recipe_id} recipe={recipe} />
            ))}
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

export default Home;
