import axios from 'axios'
import { barer_token } from '../custom/httpCustomValues'

export const deleteComment = async (issueId, commentId) => {
  const url = 'issue/' + issueId + '/comment/' + commentId

  console.log(url)
  const response = await axios.delete(url, {
    headers: { Authorization: barer_token }
  })

  return response
}
