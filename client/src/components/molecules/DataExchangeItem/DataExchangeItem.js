import React from 'react'
import { Wrapper } from './DataExchangeItem.styles'
import Button from 'components/atoms/Button/Button'

const DataExchangeItem = ({
  text1 = '',
  text2 = '',
  handleClick,
  onlyYear = false,
  selectedYear,
  setSelectedYear,
  data,
}) => {
  return (
    <Wrapper>
      <span>{text1}</span>
      {onlyYear && (
        <select
          name="selectedYear"
          id="selectedYear"
          value={selectedYear}
          onChange={(e) => setSelectedYear(e.target.value)}
        >
          {data.map(({ year }, i) => (
            <option key={i} value={year}>
              {year}
            </option>
          ))}
        </select>
      )}
      <Button onClick={handleClick}>{text2}</Button>
    </Wrapper>
  )
}

export default DataExchangeItem
