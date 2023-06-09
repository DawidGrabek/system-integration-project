import { useApi } from 'hooks/useApi'
import React from 'react'
import { Link } from 'react-router-dom'

import styled from 'styled-components'

export const Wrapper = styled.div`
  display: flex;
  width: 100vw;
  background-color: ${({ theme }) => theme.colors.pink2};
  padding: 15px 30px;
  justify-content: space-between;

  & > span,
  & > a {
    color: ${({ theme }) => theme.colors.pink6};
  }

  & > a {
    cursor: pointer;
  }
`

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
