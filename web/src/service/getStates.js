import axios from 'axios'

export const getStatus = async () => {
  const url = 'state'

  let response = await axios.get(url, {
    headers: { Authorization: localStorage.getItem('token') }
  })

  return response
}