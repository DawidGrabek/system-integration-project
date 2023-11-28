import Login from 'components/pages/Login/Login'
import React from 'react'
import { Navigate, Route, Routes } from 'react-router-dom'
import { Wrapper } from './UnauthorizedApp.styles'
import Register from 'components/pages/Register/Register'

const UnauthorizedApp = () => {
  return (
    <Wrapper>
      <Routes>
        <Route path="login" element={<Login />} />
        <Route path="register" element={<Register />} />
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </Wrapper>
  )
}

export default UnauthorizedApp
