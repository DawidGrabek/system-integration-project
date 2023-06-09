import { createGlobalStyle } from 'styled-components'

export const GlobalStyle = createGlobalStyle`
  @import url('https://fonts.googleapis.com/css2?family=Quicksand:wght@400;700&display=swap');

  html {
    box-sizing: border-box;
    scroll-behavior: smooth;
  }
  
  *, *::after, *::before {
    box-sizing: inherit;
    margin: 0;
    padding: 0;
  }
  
  body {
    font-family: 'Quicksand', sans-serif;
    overflow-x: hidden;
    max-width: 100vw;
    min-height: 100vh;
  }

  a, button {
    font-family: 'Quicksand', sans-serif;
    text-decoration: none;
    color: inherit;
    font-size: inherit;
  }

`
