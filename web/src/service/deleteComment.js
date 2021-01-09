import axios from 'axios'

export const deleteComment = async (issueId, commentId) => {
  const url = 'http://localhost:8080/issue/' + issueId + '/comment/' + commentId

  const header = {
    Authorization:
      'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYxMTA2NzY5Mn0.gusU3gRCFY3hZxv0ESw0yU5qYPK-KxuLcxsygK2EjXmtzR82OZPsYFS8qm8gNYNqWZTr265bnbp4_4O2nj6deg'
  }

  console.log(url)
  const response = await axios
    .delete(url, { headers: header })
    .catch(e => console.log(e))

  return response
}
