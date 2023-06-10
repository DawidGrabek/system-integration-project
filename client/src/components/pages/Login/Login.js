import React from 'react'
import { useForm } from 'react-hook-form'

import { yupResolver } from '@hookform/resolvers/yup'
import { Wrapper } from './Login.styles'
import FormField from 'components/molecules/FormField/FormField'
import Button from 'components/atoms/Button/Button'

import * as yup from 'yup'
import { useApi } from 'hooks/useApi'

const loginSchema = yup.object().shape({
  login: yup.string().required('Login is required'),
  password: yup.string().required('Password is required'),
})

const Login = () => {
  const { signIn, error } = useApi()
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({ resolver: yupResolver(loginSchema) })

  const onSubmit = (data) => {
    signIn(data)
  }

  return (
    <Wrapper action="post" onSubmit={handleSubmit(onSubmit)}>
      <h1>Log in</h1>
      <FormField
        id="login"
        labelText="Login"
        type={'login'}
        placeholder={'login'}
        {...register('login')}
        error={errors.login?.message}
        required
      />
      <FormField
        id="password"
        labelText="Password"
        type={'password'}
        placeholder={'Password'}
        {...register('password')}
        error={errors.password?.message}
        required
      />
      {error && <span>{error}</span>}
      <Button isBig type="submit">
        Submit
      </Button>
    </Wrapper>
  )
}

export default Login
