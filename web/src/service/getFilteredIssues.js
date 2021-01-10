import axios from 'axios'
import { barer_token } from '../custom/httpCustomValues'
import { getIssues } from './getIssues'

export const getFilteredIssues = async searchFilter => {
  const search_url = 'issues/search/'

  let filterKeyword = searchFilter
    .slice(searchFilter.indexOf(':') + 1)
    .replace(/\s/gi, '')

  let keyword = searchFilter
    .slice(0, searchFilter.indexOf(':'))
    .replace(/\s/gi, '')

  let response

  if (searchFilter === '' || filterKeyword === '') {
    response = getIssues()
  } else {
    response = await axios.get(search_url + keyword + '/' + filterKeyword, {
      headers: { Authorization: barer_token }
    })
  }
  return response
}
