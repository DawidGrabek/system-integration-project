import styled from 'styled-components'
import { Input } from './Input.styles'

export const FileInput = styled(Input).attrs({ type: 'file' })`
  border: none;
  font-size: 14px;
  border-color: ${({ theme }) => theme.colors.pink2};
  background-color: ${({ theme }) => theme.colors.pink6};
  color: ${({ theme }) => theme.colors.darkGrey};
  cursor: pointer;

  /* Stylizacja przycisku "Wybierz plik" */
  &::-webkit-file-upload-button {
    font-weight: 700;
    /* Dodaj style dla przycisku na przeglądarkach opartych na silniku WebKit, takich jak Chrome i Safari */
    background-color: ${({ theme }) => theme.colors.pink2};
    color: ${({ theme }) => theme.colors.white};
    border: none;
    cursor: pointer;
  }

  /* Stylizacja przycisku "Wybierz plik" dla innych przeglądarek */
  &::file-selector-button {
    padding: 8px 16px;
    background-color: ${({ theme }) => theme.colors.pink2};
    color: ${({ theme }) => theme.colors.white};
    border: none;
    border-radius: 10px;
    cursor: pointer;
  }
`
