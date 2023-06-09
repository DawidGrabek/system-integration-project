import { Input } from 'components/atoms/Input/Input.styles'
import { Label } from 'components/atoms/Label/Label.styles'
import styled from 'styled-components'

export const Wrapper = styled.div`
  position: relative;
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  flex-direction: column;
  margin: 20px auto 0px auto;

  ${Label} {
    margin: 5px 0;
    padding-left: 8px;
  }

  ${Input} {
    padding-top: 10px;
    padding-bottom: 10px;
    color: ${({ theme }) => theme.colors.black};
    max-width: 400px;
    background-color: transparent;
    border-radius: 10px;
  }

  & > span {
    color: ${({ theme }) => theme.colors.negative};
    padding-left: 8px;
    margin-top: 3px;
  }
`
