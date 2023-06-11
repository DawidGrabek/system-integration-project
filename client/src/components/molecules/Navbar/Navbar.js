import { useApi } from 'hooks/useApi'
import React from 'react'
import { Link } from 'react-router-dom'
import { Wrapper } from './Navbar.styles'

const Navbar = () => {
  const { signOut } = useApi()

  return (
    <Wrapper>
      <span>Jan Gryta, Dawid Grabek</span>
      <Link onClick={signOut}>Log out</Link>
    </Wrapper>
  )
}

export default Navbar
