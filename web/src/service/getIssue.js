import axios from 'axios'

export async function getIssue (id) {
  const url = 'http://localhost:8080/issue/' + id

  const response = await axios.get(url)
  return response
}
