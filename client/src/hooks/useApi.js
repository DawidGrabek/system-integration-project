import React, { useContext, useEffect, useState } from 'react'

import AxiosApi from 'axios.config'

const ApiContext = React.createContext()

export const ApiProvider = ({ children }) => {
  const [user, setUser] = useState(null)
  const [inflation, setInflation] = useState([])
  const [error, setError] = useState('')

  useEffect(() => {
    const token = localStorage.getItem('token')
    if (token)
      (async () => {
        try {
          const response = await AxiosApi.get('/api/v1/users/login')
          setUser(response.data)
        } catch (e) {
          console.log(e)
        }
      })()
  }, [])

  const signIn = async (formData) => {
    try {
      const response = await AxiosApi.post('/api/v1/users/login', formData)
      setUser(response.data.user)
      localStorage.setItem('token', response.data.data)
    } catch (error) {
      if (
        error.response &&
        error.response.status >= 400 &&
        error.response.status <= 500
      ) {
        setError(error.response.data.message)
      }
    }
  }

  const signOut = () => {
    setUser(null)
    localStorage.removeItem('token')
  }

  const getInflation = async () => {
    try {
      const response = await AxiosApi.get('/api/v1/inflation')
      setInflation(response.data)
    } catch (error) {
      console.log(error)
    }
  }

  return (
    <ApiContext.Provider value={{ user, signIn, signOut, error, getInflation }}>
      {children}
    </ApiContext.Provider>
  )
}

export const useApi = () => {
  const auth = useContext(ApiContext)

  if (!auth) {
    throw Error('useAuth needs to be used inside AuthContext')
  }

  return auth
}
