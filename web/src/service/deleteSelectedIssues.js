import axios from 'axios'
import { base_url } from '../custom/httpCustomValues'
import { getIssues } from './getIssues'

export const deleteSelectedIssues = async issueList => {
  const url = base_url + 'issues/' + issueList

  const header = {
    Authorization:
      'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYxMTA2NzY5Mn0.gusU3gRCFY3hZxv0ESw0yU5qYPK-KxuLcxsygK2EjXmtzR82OZPsYFS8qm8gNYNqWZTr265bnbp4_4O2nj6deg'
  }

  await axios.delete(url, { headers: header })
  const response = getIssues()

  return response
}
