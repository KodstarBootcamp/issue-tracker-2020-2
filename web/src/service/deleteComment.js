import axios from 'axios'
import { base_url, barer_token } from '../custom/httpCustomValues'

export const deleteComment = async (issueId, commentId) => {
  const url = base_url + 'issue/' + issueId + '/comment/' + commentId

  console.log(url)
  const response = await axios.delete(url, {
    headers: { Authorization: barer_token }
  })

  return response
}
