import axios from 'axios'

export const createIssue = async (title, description, labels) => {
  const url = 'http://localhost:8080/issue'

  let issue = {
    title: title,
    description: description,
    labels: labels
  }

  const response = await axios.post(url, issue, {
    headers: { 'content-type': 'application/json' }
  })
  return response
}
