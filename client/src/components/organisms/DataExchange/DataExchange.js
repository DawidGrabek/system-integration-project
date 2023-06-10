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

const readJsonFile = (file) =>
  new Promise((resolve, reject) => {
    const fileReader = new FileReader()

    fileReader.onload = (event) => {
      if (event.target) {
        resolve(JSON.parse(event.target.result))
      }
    }

    fileReader.onerror = (error) => reject(error)
    fileReader.readAsText(file)
  })

const DataExchange = ({
  inflation,
  expenseExpenditure,
  expenseProduct,
  setInflation,
}) => {
  const [selectedYear, setSelectedYear] = useState('1995')
  const [JSONFile, setJSONFile] = useState(null)

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

  const handleImportJSON = async (event) => {
    console.log('handleImportJSON')
    if (event.target.files) {
      const parsedData = await readJsonFile(event.target.files[0])
      const mergedData = [...inflation, ...parsedData]
      // console.log(mergedData)
      setInflation(mergedData)
      // console.log('inflation', inflation)
    }
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
        {/* <Button onClick={handleImportJSON}>Import</Button> */}
        <input
          type="file"
          name="ImportJSON"
          id="ImportJSON"
          accept=".json"
          onChange={handleImportJSON}
        />
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
