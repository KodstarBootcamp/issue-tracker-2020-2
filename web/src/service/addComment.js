import axios from 'axios'

export const addComment = async (commentText, issueId) => {
  const url = 'issue/' + issueId + '/comment'

  let comment = { comment: commentText }

  const response = await axios.post(url, comment, {
    headers: { Authorization: localStorage.getItem('token') }
  })

  return response
}
