import axios from 'axios'

export const addColumn = async name => {
  const url = 'state'

  let issue = {
    name: name
  }

  const response = await axios.post(url, issue, {
    headers: { Authorization: localStorage.getItem('token') }
  })
  return response
}
