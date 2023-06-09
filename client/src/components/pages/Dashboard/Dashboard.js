import Navbar from 'components/molecules/Navbar/Navbar'
import DataExchange from 'components/organisms/DataExchange/DataExchange'
import React from 'react'
import styled from 'styled-components'

export const ChartsWrapper = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
`

const Dashboard = () => {
  return (
    <>
      <Navbar />
      <ChartsWrapper>
        <span>1</span>
        <span>2</span>
      </ChartsWrapper>
      <DataExchange />
    </>
  )
}

export default Dashboard
