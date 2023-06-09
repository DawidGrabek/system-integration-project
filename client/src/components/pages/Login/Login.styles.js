import styled from 'styled-components'

export const Wrapper = styled.form`
  display: flex;
  align-items: center;
  margin: auto auto;
  justify-content: center;
  flex-direction: column;
  max-width: fit-content;
  padding: 30px;
  border: 3px solid ${({ theme }) => theme.colors.pink4};
  border-radius: 15px;

  & > button {
    margin: 40px auto 20px;
    max-width: 150px;
    padding: 16px 32px;
  }
`
