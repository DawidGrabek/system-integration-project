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

const ExpenseChart = ({ expenseExpenditure }) => {
  const data = expenseExpenditure.map(({ year, value }) => {
    return { year, Total_General_Government_Expenditure: value }
  })

  const formatYAxis = (x) => `${x.toFixed(2)}%`

  const yDomain = ['dataMin', 'dataMax']

  return (
    <LineChart
      width={500}
      height={300}
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
        dot={true}
        fill="#723d46"
      />
    </LineChart>
  )
}

export default ExpenseChart
