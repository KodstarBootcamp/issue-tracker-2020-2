import axios from 'axios'
import { barer_token } from '../custom/httpCustomValues'

export const getIssues = async () => {
  const url = 'issues'

  const response = await axios.get(url, {
    headers: { Authorization: barer_token }
  })

  return response
}
