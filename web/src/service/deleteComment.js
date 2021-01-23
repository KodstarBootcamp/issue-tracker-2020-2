import axios from 'axios'

export const deleteComment = async (issueId, commentId) => {
  const url = 'issue/' + issueId + '/comment/' + commentId

  console.log(url)
  const response = await axios.delete(url, {
    headers: { Authorization: localStorage.getItem('token') }
  })

  return response
}
