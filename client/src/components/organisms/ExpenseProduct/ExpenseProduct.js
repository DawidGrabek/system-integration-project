import AxiosApi from 'axios.config'
import { exportToFileJSON } from 'helpers/exportData'
import React from 'react'
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
} from 'recharts'
import { Wrapper } from './ExpenseProduct.styles'
import DataExchangeItem from 'components/molecules/DataExchangeItem/DataExchangeItem'

const ExpenseProduct = ({ expenseProduct }) => {
  const data = expenseProduct.map(({ year, value }) => {
    return { year, Gross_Domestic_Product: value }
  })

  const handleExportJSON = async () => {
    const { data } = await AxiosApi.get(`/api/v1/expense/product`)
    exportToFileJSON(data, `ExpenseProduct`)
  }

  const formatYAxis = (x) => `${x.toFixed(2)}%`

  const yDomain = ['dataMin', 'dataMax']

  return (
    <Wrapper>
      <LineChart
        width={600}
        height={350}
        margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
        data={data}
      >
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="year" />
        <YAxis tickFormatter={formatYAxis} domain={yDomain} />
        <Tooltip />
        <Legend />
        <Line
          type="monotone"
          dataKey="Gross_Domestic_Product"
          stroke="#568A67"
          dot={false}
          name="Gross Domestic Product"
        />
      </LineChart>
      <DataExchangeItem
        text1="Export JSON:"
        text2="Export"
        handleClick={handleExportJSON}
      />
    </Wrapper>
  )
}

export default ExpenseProduct
