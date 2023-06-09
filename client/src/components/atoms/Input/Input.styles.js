import styled from 'styled-components'

export const Input = styled.input`
  width: 100%;
  max-height: 70px;
  padding: ${({ isBig }) => (isBig ? '12px 20px' : '5px 10px')};
  background-color: transparent;
  font-size: 18px;
  border-radius: 20px;
  border: 3px solid ${({ theme }) => theme.colors.pink4};
`
