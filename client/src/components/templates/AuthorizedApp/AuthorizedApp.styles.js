import styled from 'styled-components'

export const Wrapper = styled.div`
  width: 100vw;
  min-height: 100vh;
  background-color: ${({ theme }) => theme.colors.pink6};
  display: flex;
  flex-direction: column;
  align-items: center;
  /* justify-content: center; */
`
