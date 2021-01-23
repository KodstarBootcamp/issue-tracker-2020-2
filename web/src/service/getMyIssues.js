import axios from 'axios'

export const getMyIssues = async () => {
  const url = 'issues/user/issues'
  const response = await axios.get(url, {
    headers: { Authorization: localStorage.getItem('token') }
  })

  return response
}
