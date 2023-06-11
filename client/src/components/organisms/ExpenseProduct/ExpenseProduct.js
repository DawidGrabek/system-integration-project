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

const ExpenseProduct = ({ expenseProduct }) => {
  const data = expenseProduct.map(({ year, value }) => {
    return { year, Gross_Domestic_Product: value }
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
        dataKey="Gross_Domestic_Product"
        stroke="#568A67"
        dot={false}
        // fill="#568A67"
      />
    </LineChart>
  )
}

export default ExpenseProduct
