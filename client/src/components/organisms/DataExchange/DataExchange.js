import Button from 'components/atoms/Button/Button'
import React, { useState } from 'react'
import { DataExchangeItem, Wrapper } from './DataExchange.styles'

const exportToFileJSON = (data, fileName) => {
  const jsonString = `data:text/json;chatset=utf-8,${encodeURIComponent(
    JSON.stringify(data, fileName)
  )}`
  const link = document.createElement('a')
  link.href = jsonString
  link.download = `${fileName}.json`

  link.click()
}

const DataExchange = ({ inflation, expenseExpenditure, expenseProduct }) => {
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
    exportToFileJSON(inflation, 'inflation')
    exportToFileJSON(expenseExpenditure, 'expenseExpenditure')
    exportToFileJSON(expenseProduct, 'expenseProduct')
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
