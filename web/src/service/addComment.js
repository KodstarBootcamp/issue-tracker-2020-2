import axios from 'axios'

export const addComment = async (commentText, issueId) => {
  const url = 'http://localhost:8080/issue/' + issueId + '/comment'

  console.log(url)
  const header = {
    Authorization:
      'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYxMTA2NzY5Mn0.gusU3gRCFY3hZxv0ESw0yU5qYPK-KxuLcxsygK2EjXmtzR82OZPsYFS8qm8gNYNqWZTr265bnbp4_4O2nj6deg'
  }

  let comment = { comment: commentText }

  const response = await axios.post(url, comment, {
    headers: header
  })
  
  return response
}
