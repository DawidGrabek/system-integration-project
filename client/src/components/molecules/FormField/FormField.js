import React from 'react'

import { Input } from 'components/atoms/Input/Input.styles'
import { Label } from 'components/atoms/Label/Label.styles'
import PropTypes from 'prop-types'

import { Wrapper } from './FormField.styles'

const FormField = React.forwardRef((props, ref) => {
  const { id, labelText, type, placeholder, value, onChange, error, ...rest } =
    props

  // const renderError = () => {
  //   if (typeof error === 'string') {
  //     return <span>{error}</span>
  //   }

  //   if (error && 'message' in error) {
  //     return <span>{error.message}</span>
  //   }

  //   return null
  // }

  return (
    <Wrapper>
      <Label htmlFor={id}>{labelText}</Label>
      <Input
        type={type}
        id={id}
        placeholder={placeholder}
        value={value}
        onChange={onChange}
        ref={ref}
        {...rest}
      />
      <span>{error?.message}</span>
    </Wrapper>
  )
})

FormField.displayName = 'FormField'

FormField.propTypes = {
  id: PropTypes.string.isRequired,
  labelText: PropTypes.string.isRequired,
  error: PropTypes.oneOfType([PropTypes.string, PropTypes.object]),
}

export default FormField
