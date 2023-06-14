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
import { Wrapper } from './ExpenseExpenditureChart.styles'
import { exportToFileJSON } from 'helpers/exportData'
import AxiosApi from 'axios.config'
import DataExchangeItem from 'components/molecules/DataExchangeItem/DataExchangeItem'

const ExpenseChart = ({ expenseExpenditure }) => {
  const data = expenseExpenditure.map(({ year, value }) => {
    return { year, Total_General_Government_Expenditure: value }
  })

  const handleExportJSON = async () => {
    const { data } = await AxiosApi.get(`/api/v1/expense/expenditure`)
    exportToFileJSON(data, `GrossExpenditure`)
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
          dataKey="Total_General_Government_Expenditure"
          stroke="#723d46"
          dot={false}
          name="Total General Government Expenditure"
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

export default ExpenseChart
