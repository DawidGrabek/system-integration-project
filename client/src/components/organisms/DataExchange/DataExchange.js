import React, { useState } from 'react'
import { InputFileWrapper, Wrapper } from './DataExchange.styles'
import AxiosApi from 'axios.config'
import { FileInput } from 'components/atoms/Input/InputFile.style'
import { exportToFileJSON, exportToFileXML } from 'helpers/exportData'
import DataExchangeItem from 'components/molecules/DataExchangeItem/DataExchangeItem'
import { readJsonFile as readJSONFile, readXMLFile } from 'helpers/readFile'

const DataExchange = ({ inflation, expenseExpenditure, setInflation }) => {
  const [selectedYear, setSelectedYear] = useState('1995')
  const [selectedYearJSON, setSelectedYearJSON] = useState('1995')
  const [selectedYearInflation, setSelectedYearInflation] = useState('1995')

  // DONE
  const handleImportXML = async (event) => {
    if (event.target.files) {
      const parsedData = await readXMLFile(event.target.files[0])

      await AxiosApi.post('/api/v1/ws', parsedData, {
        headers: {
          'content-type': 'text/xml',
        },
      })
    }
  }

  // DONE
  const handleExportJSON = async () => {
    const { data } = await AxiosApi.get(`/api/v1/inflation/${selectedYearJSON}`)
    exportToFileJSON(data, `inflation${selectedYearJSON}`)
  }

  // DONE
  const handleImportJSON = async (event) => {
    if (event.target.files) {
      const parsedData = await readJSONFile(event.target.files[0])
      const mergedData = [...inflation, parsedData]

      try {
        await AxiosApi.post('/api/v1/inflation', parsedData)
        setInflation(mergedData)
      }
      catch (error) {
        alert('Brak dostępu do takiej funkcjonalności, pozdrawiam cieplutko')
      }
      
    }
  }

  // DONE
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

    const modifiedXmlData = `<?xml version="1.0" encoding="UTF-8"?>
    <root>${
      response.data
        .replace(/<\/?SOAP-ENV:Envelope[^>]*>/g, '') // Usuwamy znacznik 'SOAP-ENV:Envelope'
        .replace(/<\/?SOAP-ENV:Header[^>]*>/g, '') // Usuwamy znacznik 'SOAP-ENV:Header'
        .replace(/<\/?SOAP-ENV:Body[^>]*>/g, '') // Usuwamy znacznik 'SOAP-ENV:Body'
        .replace(/<\/?ns3:getExpenseResponse[^>]*>/g, '') // Usuwamy znacznik 'ns3:getExpenseResponse'
    }</root>`

    exportToFileXML(modifiedXmlData.trim(), `GrossExpenditure${selectedYear}`)
  }

  const handleExportXMLInflation = async () => {
    const { data } = await AxiosApi.get(
      `/api/v1/inflation/xml/${selectedYearInflation}`
    )

    exportToFileXML(data, `GrossExpenditure${selectedYearInflation}`)
  }

  return (
    <Wrapper>
      <DataExchangeItem
        text1="Export JSON inflation:"
        text2="Export"
        onlyYear
        handleClick={handleExportJSON}
        selectedYear={selectedYearJSON}
        setSelectedYear={setSelectedYearJSON}
        data={inflation}
      />
      <DataExchangeItem
        text1="Export XML gross expenditure:"
        text2="Export"
        onlyYear
        handleClick={handleExportXMLYear}
        selectedYear={selectedYear}
        setSelectedYear={setSelectedYear}
        data={expenseExpenditure}
      />
      <DataExchangeItem
        text1="Export XML inflation expenditure:"
        text2="Export"
        onlyYear
        handleClick={handleExportXMLInflation}
        selectedYear={selectedYearInflation}
        setSelectedYear={setSelectedYearInflation}
        data={expenseExpenditure}
      />
      <InputFileWrapper>
        <span>Import JSON:</span>
        <FileInput
          type="file"
          name="ImportJSON"
          id="ImportJSON"
          accept=".json"
          onChange={handleImportJSON}
        />
      </InputFileWrapper>
      <InputFileWrapper>
        <span>Import XML:</span>
        <FileInput
          type="file"
          name="ImportXML"
          id="ImportXML"
          accept=".xml"
          onChange={handleImportXML}
        />
      </InputFileWrapper>
    </Wrapper>
  )
}

export default DataExchange
