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
import AxiosApi from 'axios.config'
import { exportToFileJSON } from 'helpers/exportData'
import { Wrapper } from './InflationChart.styles'
import DataExchangeItem from 'components/molecules/DataExchangeItem/DataExchangeItem'

const InflationChart = ({ inflation }) => {
  const data = inflation.map(({ year, value }) => {
    return { year, Inflation: value }
  })

  const handleExportJSON = async () => {
    const { data } = await AxiosApi.get(`/api/v1/inflation`)
    exportToFileJSON(data, `Inflation`)
  }

  const formatYAxis = (x) => `${x.toFixed(2)}%`

  const yDomain = [0, 'maxData']

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
          dataKey="Inflation"
          stroke="#FB6F92"
          dot={false}
          name="Inflation"
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

export default InflationChart
