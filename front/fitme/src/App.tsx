import React from 'react';
import logo from './logo.svg';
import './App.css';
import { Button } from 'flowbite-react';
import Cart from './pages/cart';

function App() {
  return (
    <div className='App max-w-[400px] mx-auto'>
      <Cart></Cart>
    </div>
  );
}

export default App;
