import axios from 'axios'
import { barer_token } from '../custom/httpCustomValues'

export const createIssue = async (title, description, labels) => {
  const url = 'issue'

  let issue = {
    title: title,
    description: description,
    labels: labels
  }

  const response = await axios.post(url, issue, {
    headers: { Authorization: localStorage.getItem('token') }
  })
  return response
}
