import axios from 'axios'

export const createIssue = async (title, description, labels) => {
  const url = 'issue'

  let issue = {
    title: title,
    description: description,
    labels: labels
  }
  const header = {
    Authorization:
      'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYxMTA5ODE5M30.X2EHZI1CCYsmw4yekWr1ePIQ6mEWBhGIZ-JeVZzazfdN6pr319LJjO31bYbdY1fyBD4IsvsVHl_VWGZuMCsluA'
  }

  const response = await axios.post(url, issue, {
    headers: { 'content-type': 'application/json', header }
  })
  return response
}
