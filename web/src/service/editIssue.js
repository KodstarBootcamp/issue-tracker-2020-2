import axios from 'axios'
import { barer_token } from '../custom/httpCustomValues'

export const editIssue = async (id, title, description) => {
  const url = 'issue/' + id

  let issue = {
    title: title,
    description: description
  }

  const headers = {
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Headers': '*',
    Authorization: barer_token,
    'Content-Type': 'application/json'
  }

  const response = await axios
    .put(url, issue, {
      headers: headers
    })
    .catch(e => console.log(e))

  return response
}
