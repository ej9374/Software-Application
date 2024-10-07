import React, {useState, useEffect, useRef } from 'react';
import './Home.css';

function Home(){

    return (
        <div>
            <Icon name="요리1" time="시간1" explanation="설명1"/>
            <Icon name="요리2" time="시간2" explanation="설명2"/>
        </div>
    );
}

function Icon({name, time, explanation}){
    return(
        <div className="icon">
            <h4>{name}</h4>
            <p>{time}</p>
            <p>{explanation}</p>
        </div>
    );
}

export default Home;