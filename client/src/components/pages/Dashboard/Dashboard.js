import Navbar from 'components/molecules/Navbar/Navbar'
import DataExchange from 'components/organisms/DataExchange/DataExchange'
import { useApi } from 'hooks/useApi'
import InflationChart from 'components/organisms/InflationChart/InflationChart'
import React, { useEffect } from 'react'
import { ChartsWrapper } from './Dashboard.styles'
import ExpenseExpenditureChart from 'components/organisms/ExpenseExpenditureChart/ExpenseExpenditureChart'
import ExpenseChart from 'components/organisms/ExpenseExpenditureChart/ExpenseExpenditureChart'
import ExpenseProduct from 'components/organisms/ExpenseProduct/ExpenseProduct'

const Dashboard = () => {
  const {
    getInflation,
    inflation,
    getExpenseExpenditure,
    getExpenseProduct,
    expenseExpenditure,
    expenseProduct,
  } = useApi()

  useEffect(() => {
    getInflation()
    getExpenseExpenditure()
    getExpenseProduct()
  }, [])

  return (
    <>
      <Navbar />
      <ChartsWrapper>
        <InflationChart inflation={inflation} />
        <ExpenseExpenditureChart expenseExpenditure={expenseExpenditure} />
        <ExpenseProduct expenseProduct={expenseProduct} />
      </ChartsWrapper>
      <DataExchange />
    </>
  )
}

export default Dashboard
