import axios from 'axios'

export const changeState = async (issueId, stateId) => {
  const url = 'issue/' + issueId + '/state/' + stateId

  const response = await axios.put(url, {
    headers: { Authorization: localStorage.getItem('token') }
  })

  return response
}
