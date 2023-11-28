import React from 'react'
import { Wrapper } from './Register.styles'
import FormField from 'components/molecules/FormField/FormField'
import Button from 'components/atoms/Button/Button'
import { Link } from 'react-router-dom'
import { useApi } from 'hooks/useApi'
import { useForm } from 'react-hook-form'
import { yupResolver } from '@hookform/resolvers/yup'
import * as yup from 'yup'

const registerSchema = yup.object().shape({
  login: yup.string().required('Login is required'),
  password: yup.string().required('Password is required'),
  repeatPassword: yup
    .string()
    .required('Repeat password is required')
    .oneOf([yup.ref('password')], 'Passwords must match'),
})

const Register = () => {
  const { signUp, error } = useApi()
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({ resolver: yupResolver(registerSchema) })

  const onSubmit = (data) => {
    signUp(data)
    console.log(data)
  }

  return (
    <Wrapper action="post" onSubmit={handleSubmit(onSubmit)}>
      <h1>Register</h1>
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
      <FormField
        id="repeatPassword"
        labelText="Repeat password"
        type={'password'}
        placeholder={'Repeat password'}
        {...register('repeatPassword')}
        error={errors.repeatPassword?.message}
        required
      />
      {/* {errors.repeatPassword?.message && <span>{errors.repeatPassword?.message}</span>} */}
      {error && <span>{error}</span>}
      <Button isBig type="submit">
        Submit
      </Button>
      <Link to="/login">You have an account?</Link>
    </Wrapper>
  )
}

export default Register
