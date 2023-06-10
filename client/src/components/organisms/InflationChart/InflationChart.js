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
  const data = inflation.map(({ year, value }) => {
    return { year, Inflation: value }
  })

  const formatYAxis = (x) => `${x.toFixed(2)}%`

  const yDomain = [0, 'maxData']

  return (
    <LineChart
      width={500}
      height={300}
      margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
      data={data}
    >
      <CartesianGrid strokeDasharray="3 3" />
      <XAxis dataKey="year" />
      <YAxis
        tickFormatter={formatYAxis}
        domain={yDomain}
        // type="number"
        // allowDataOverflow={true}
      />
      <Tooltip />
      <Legend />
      <Line
        type="monotone"
        dataKey="Inflation"
        stroke="#FB6F92"
        // dot={true}
        // fill="#FB6F92"
      />
    </LineChart>
  )
}

export default InflationChart
