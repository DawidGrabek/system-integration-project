import { GlobalStyle } from 'assets/styles/GlobalStyle'
import { theme } from 'assets/styles/theme'
import React from 'react'
import { ThemeProvider } from 'styled-components'
import { BrowserRouter as Router } from 'react-router-dom'
import { ApiProvider } from 'hooks/useApi'

const AppProviders = ({ children }) => {
  return (
    <Router>
      <ThemeProvider theme={theme}>
        <GlobalStyle />
        <ApiProvider>{children}</ApiProvider>
      </ThemeProvider>
    </Router>
  )
}

export default AppProviders
