import logo from './logo.svg';
import './App.css';
import Header from './component/Header/Header';
import Home from './component/Home/Home';
import SignUp from './component/SignUp/SignUp';
import LogIn from './component/LogIn/LogIn';
import Recipe from './component/Recipe/Recipe';

import React, { useState, useEffect } from 'react';
import {BrowserRouter, Route, Routes} from 'react-router-dom'

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  return (
    <BrowserRouter>
      <div className="App">
        <Header isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn}/>
        <Routes>
          <Route exact path="/" element={<Home/>}/>
          <Route path="/signup" element={<SignUp/>}/>
          <Route path="/login" element={<LogIn setIsLoggedIn={setIsLoggedIn}/>}/>
          <Route path="/recipe" element={<Recipe/>}/>
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
