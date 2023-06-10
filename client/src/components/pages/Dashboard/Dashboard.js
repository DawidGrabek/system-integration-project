import Navbar from 'components/molecules/Navbar/Navbar'
import DataExchange from 'components/organisms/DataExchange/DataExchange'
import { useApi } from 'hooks/useApi'
import InflationChart from 'components/organisms/InflationChart/InflationChart'
import React, { useEffect } from 'react'
import { ChartsWrapper } from './Dashboard.styles'

const Dashboard = () => {
  const { getInflation, inflation } = useApi()

  useEffect(() => {
    getInflation()
  }, [])

  return (
    <>
      <Navbar />
      <ChartsWrapper>
        <InflationChart inflation={inflation} />
        <span>2</span>
      </ChartsWrapper>
      <DataExchange />
    </>
  )
}

export default Dashboard
