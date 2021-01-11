import axios from 'axios'

export const getIssues = async () => {
  const url = 'issues'

  const response = await axios.get(url, {
    headers: { Authorization: localStorage.getItem('token') }
  })

  return response
}
