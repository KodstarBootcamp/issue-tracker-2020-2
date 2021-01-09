import axios from 'axios'

export const editIssue = async (id, title, description) => {
  const url = 'localhost:8080/issue/' + id

  let issue = {
    title: title,
    description: description
  }
  console.log(url)
  const header = {
    Authorization:
      'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYxMTA2NzY5Mn0.gusU3gRCFY3hZxv0ESw0yU5qYPK-KxuLcxsygK2EjXmtzR82OZPsYFS8qm8gNYNqWZTr265bnbp4_4O2nj6deg'
  }

  const response = await axios
    .put(url, issue, {
      headers: header
    })
    .catch(e => console.log(e))

  return response
}
