import axios from 'axios'

export const getLabels = async () => {
  const url = 'issues/labels'

  let response = await axios.get(url, {
    headers: { Authorization: localStorage.getItem('token') }
  })

  return response
}
