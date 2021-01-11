import axios from 'axios'
import { barer_token } from '../custom/httpCustomValues'

export const deleteIssue = async issueId => {
  const url = 'issue/' + issueId

  const response = await axios.delete(url, {
    headers: { Authorization: localStorage.getItem('token') }
  })

  return response
}
