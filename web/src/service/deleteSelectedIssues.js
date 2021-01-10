import axios from 'axios'
import { barer_token } from '../custom/httpCustomValues'
import { getIssues } from './getIssues'

export const deleteSelectedIssues = async issueList => {
  const url = 'issues/' + issueList

  await axios.delete(url, { headers: { Authorization: barer_token } })
  const response = getIssues()

  return response
}
