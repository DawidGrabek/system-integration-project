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

const InflationChart = ({ inflation }) => {
  console.log(inflation)

  const data = inflation.map(({ year, value }) => {
    return { year, inflation: value }
  })

  const formatYAxis = (inflation) => `${inflation}%`
  return (
    <LineChart
      width={500}
      height={300}
      data={data}
      margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
    >
      <CartesianGrid strokeDasharray="3 3" />
      <XAxis dataKey="year" />
      <YAxis format={formatYAxis} />
      <Tooltip />
      <Legend />
      <Line
        type="monotone"
        dataKey="inflation"
        stroke="#FB6F92"
        // activeDot={{ r: 8 }}
        dot={false}
      />
    </LineChart>
  )
}

export default InflationChart
