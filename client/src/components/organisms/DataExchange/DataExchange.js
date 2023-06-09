import Button from 'components/atoms/Button/Button'
import React, { useState } from 'react'
import styled from 'styled-components'

export const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin: 30px auto;
  gap: 10px;
  text-align: center;
  align-items: center;
  justify-content: center;

  button {
    background-color: ${({ theme }) => theme.colors.pink2};
    max-width: 200px;
    padding: 10px 30px;
  }
`

export const DataExchangeItem = styled.div`
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

const DataExchange = () => {
  const [selectedYear, setSelectedYear] = useState('1995')

  const handleYearChange = (event) => {
    setSelectedYear(event.target.value)
  }

  const handleExportXML = () => {
    console.log('handleExportXML')
  }

  const handleImportXML = () => {
    console.log('handleImportXML')
  }

  const handleExportJSON = () => {
    console.log('handleExportJSON')
  }

  const handleImportJSON = () => {
    console.log('handleImportJSON')
  }

  const handleExportXMLYear = () => {
    console.log('handleExportXMLYear', selectedYear)
  }

  return (
    <Wrapper>
      <DataExchangeItem>
        <span>Export XML:</span>{' '}
        <Button onClick={handleExportXML}>Export</Button>
      </DataExchangeItem>
      <DataExchangeItem>
        <span>Import XML:</span>{' '}
        <Button onClick={handleImportXML}>Import</Button>
      </DataExchangeItem>
      <DataExchangeItem>
        <span>Export JSON:</span>{' '}
        <Button onClick={handleExportJSON}>Export</Button>
      </DataExchangeItem>
      <DataExchangeItem>
        <span>Import JSON:</span>{' '}
        <Button onClick={handleImportJSON}>Import</Button>
      </DataExchangeItem>

      <DataExchangeItem>
        <span>Import JSON:</span>
        <select
          name="xmlYear"
          id="xmlYear"
          value={selectedYear}
          onChange={handleYearChange}
        >
          <option value="1995">1995</option>
          <option value="1996">1996</option>
          <option value="1997">1997</option>
          <option value="1998">1998</option>
        </select>
        <Button onClick={handleExportXMLYear}>Import</Button>
      </DataExchangeItem>
    </Wrapper>
  )
}

export default DataExchange
