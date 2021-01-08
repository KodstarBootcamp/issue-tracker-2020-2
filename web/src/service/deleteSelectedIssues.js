import axios from 'axios'
import { getIssues } from './getIssues'

export const deleteSelectedIssues = async issueList => {
  const url = 'http://localhost:8080/issues/' + issueList

  await axios.delete(url)
  const response = getIssues()

  return response
}
