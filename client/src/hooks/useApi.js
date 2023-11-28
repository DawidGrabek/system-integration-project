import React, { useContext, useEffect, useState } from 'react'

import AxiosApi from 'axios.config'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'

const ApiContext = React.createContext()

export const ApiProvider = ({ children }) => {
  const [user, setUser] = useState(null)
  const [inflation, setInflation] = useState([])
  const [expenseExpenditure, setExpenseExpenditure] = useState([])
  const [expenseProduct, setExpenseProduct] = useState([])
  const [error, setError] = useState('')
  const navigate = useNavigate()

  useEffect(() => {
    const token = localStorage.getItem('token')
    if (token) setUser(token)
  }, [])

  useEffect(() => {
    setError('')
  }, [navigate])

  const signIn = async (formData) => {
    try {
      console.log(formData)
      const response = await AxiosApi.post('/api/v1/auth/login', formData)
      setUser(response.data)
      localStorage.setItem('token', response.data)
    } catch (error) {
      if (
        error.response &&
        error.response.status >= 400 &&
        error.response.status <= 500
      ) {
        setError('Invalid login or password')
      }
    }
  }
  const signUp = async (form) => {
    try {
        const formData = {login: form.login, password: form.password, role: 'USER'}
        await axios.post('http://localhost:8080/api/v1/auth/register', formData)

        navigate('/login')
      } catch (error) {
        setError('Dany login jest już zajęty')
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

  const getExpenseExpenditure = async () => {
    try {
      const response = await AxiosApi.get('/api/v1/expense/expenditure')
      setExpenseExpenditure(response.data)
      return response.data
    } catch (error) {
      console.log(error)
    }
  }

  const getExpenseProduct = async () => {
    try {
      const response = await AxiosApi.get('/api/v1/expense/product')
      setExpenseProduct(response.data)

      return response.data
    } catch (error) {
      console.log(error)
    }
  }

  return (
    <ApiContext.Provider
      value={{
        user,
        signIn,
        signUp,
        signOut,
        error,
        getInflation,
        inflation,
        getExpenseExpenditure,
        getExpenseProduct,
        expenseExpenditure,
        expenseProduct,
        setInflation,
      }}
    >
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
