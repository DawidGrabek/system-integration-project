import styled from 'styled-components'

export const Wrapper = styled.div`
  display: flex;
  text-align: center;
  align-items: center;
  justify-content: center;

  & > span {
    min-width: fit-content;
    margin-right: 20px;
  }

  select {
    padding: 8px;
    font-size: 14px;
    border-radius: 4px;
    margin-right: 20px;
    border: none;
    background-color: ${({ theme }) => theme.colors.pink5};
    color: ${({ theme }) => theme.colors.gray4};

    &:focus {
      outline: none;
      box-shadow: 0 0 0 2px ${({ theme }) => theme.colors.pink2};
    }
  }
`
