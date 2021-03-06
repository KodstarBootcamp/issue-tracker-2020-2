import axios from 'axios'

export const editIssue = async (id, title, description) => {
  const url = 'issue/' + id

  let issue = {
    title: title,
    description: description
  }

  const headers = {
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Headers': '*',
    Authorization: localStorage.getItem('token'),
    'Content-Type': 'application/json'
  }

  const response = await axios
    .put(url, issue, {
      headers: headers
    })
    .catch(e => console.log(e))

  return response
}
