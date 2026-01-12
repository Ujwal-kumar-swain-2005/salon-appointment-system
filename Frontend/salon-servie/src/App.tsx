
import React from 'react'
import './App.css'
import { ThemeProvider } from '@mui/material/styles';
import greenTheme from './theme/greenTheme';
import Home from './Customer/Home/Home';
function App() {
 

return (
   
    <ThemeProvider theme={greenTheme}>
        <Home />
    </ThemeProvider>
  );
}

export default App
