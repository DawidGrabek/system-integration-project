import React from 'react'
import { Wrapper } from './AuthorizedApp.styles'
import { Navigate, Route, Routes } from 'react-router-dom'
import Dashboard from 'components/pages/Dashboard/Dashboard'

const AuthorizedApp = () => {
  return (
    <Wrapper>
      <Routes>
        <Route path="/" element={<Dashboard />} />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </Wrapper>
  )
}

export default AuthorizedApp
