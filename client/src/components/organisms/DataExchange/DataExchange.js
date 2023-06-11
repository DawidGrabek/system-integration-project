import Button from 'components/atoms/Button/Button'
import React, { useState } from 'react'
import { DataExchangeItem, Wrapper } from './DataExchange.styles'
import AxiosApi from 'axios.config'

const exportToFileJSON = (data, fileName) => {
  const jsonString = `data:text/json;chatset=utf-8,${encodeURIComponent(
    JSON.stringify(data, fileName)
  )}`
  const link = document.createElement('a')
  link.href = jsonString
  link.download = `${fileName}.json`

  link.click()
}

const exportToFileXML = (data, fileName) => {
  const xmlString = `data:text/xml;chatset=utf-8,${encodeURIComponent(data)}`
  const link = document.createElement('a')
  link.href = xmlString
  link.download = `${fileName}.xml`

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

const readXMLFile = (file) =>
  new Promise((resolve, reject) => {
    const fileReader = new FileReader()

    fileReader.onload = (event) => {
      if (event.target) {
        resolve(event.target.result)
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
  const [selectedYearJSON, setSelectedYearJSON] = useState('1995')
  const handleYearChange = (event) => {
    setSelectedYear(event.target.value)
  }

  // ZROBIONE
  const handleImportXML = async (event) => {
    if (event.target.files) {
      const parsedData = await readXMLFile(event.target.files[0])

      const response = await AxiosApi.post('/api/v1/ws', parsedData, {
        headers: {
          'content-type': 'text/xml',
        },
      })
    }
  }

  // ZROBIONE
  const handleExportJSON = async () => {
    const { data } = await AxiosApi.get(`/api/v1/inflation/${selectedYearJSON}`)
    exportToFileJSON(data, `inflation${selectedYearJSON}`)

    // exportToFileJSON(inflation, 'inflation')
    // exportToFileJSON(expenseExpenditure, 'expenseExpenditure')
    // exportToFileJSON(expenseProduct, 'expenseProduct')
  }

  // ZROBIONE
  const handleImportJSON = async (event) => {
    if (event.target.files) {
      const parsedData = await readJsonFile(event.target.files[0])
      const mergedData = [...inflation, parsedData]

      await AxiosApi.post('/api/v1/inflation', parsedData)
      setInflation(mergedData)
    }
  }

  // ZROBIONE
  const handleExportXMLYear = async () => {
    const stringRequest = `<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tns="http://system_integration.pl/soap_service">
    <soap:Header/>
    <soap:Body>
      <tns:getExpense>
        <year>${selectedYear}</year>
        <unitTitle>Total General Government Expenditure</unitTitle>
      </tns:getExpense>
    </soap:Body>
  </soap:Envelope>
  `
    const response = await AxiosApi.post('/api/v1/ws', stringRequest, {
      headers: {
        'content-type': 'text/xml',
      },
    })

    exportToFileXML(response.data, `GrossExpenditure${selectedYear}`)
  }

  return (
    <Wrapper>
      <DataExchangeItem>
        <span>Export JSON:</span>{' '}
        <select
          name="jsonYear"
          id="jsonYear"
          value={selectedYearJSON}
          onChange={(e) => setSelectedYearJSON(e.target.value)}
        >
          {inflation.map(({ year }, i) => (
            <option key={i} value={year}>
              {year}
            </option>
          ))}
        </select>
        <Button onClick={handleExportJSON}>Export</Button>
      </DataExchangeItem>
      <DataExchangeItem>
        <span>Export XML:</span>
        <select
          name="xmlYear"
          id="xmlYear"
          value={selectedYear}
          onChange={handleYearChange}
        >
          {expenseExpenditure.map(({ year }, i) => (
            <option key={i} value={year}>
              {year}
            </option>
          ))}
        </select>
        <Button onClick={handleExportXMLYear}>Export</Button>
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
        <span>Import XML:</span>{' '}
        {/* <Button onClick={handleImportXML}>Import</Button> */}
        <input
          type="file"
          name="ImportXML"
          id="ImportXML"
          accept=".xml"
          onChange={handleImportXML}
        />
      </DataExchangeItem>
    </Wrapper>
  )
}

export default DataExchange
