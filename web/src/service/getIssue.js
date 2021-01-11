import axios from 'axios'
import { barer_token } from '../custom/httpCustomValues'

export async function getIssue (id) {
  const url = 'issue/' + id


  const response = await axios.get(url, { headers: { Authorization: localStorage.getItem('token') }})
  return response
}
