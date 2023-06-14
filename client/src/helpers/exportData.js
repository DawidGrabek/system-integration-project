export const exportToFileJSON = (data, fileName) => {
  const jsonString = `data:text/json;chatset=utf-8,${encodeURIComponent(
    JSON.stringify(data, fileName)
  )}`
  const link = document.createElement('a')
  link.href = jsonString
  link.download = `${fileName}.json`

  link.click()
}

export const exportToFileXML = (data, fileName) => {
  const xmlString = `data:text/xml;chatset=utf-8,${encodeURIComponent(data)}`
  const link = document.createElement('a')
  link.href = xmlString
  link.download = `${fileName}.xml`

  link.click()
}
