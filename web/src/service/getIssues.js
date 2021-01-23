import axios from 'axios'

export const getIssues = async (page = 1, size = 3) => {
  const url = 'issues' + '?page=' + (page - 1) + '&size=' + size
  const response = await axios.get(url, {
    headers: { Authorization: localStorage.getItem('token') }
  })

  return response
}
