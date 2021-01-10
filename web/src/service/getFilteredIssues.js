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

  let res

  if (searchFilter === '' || filterKeyword === '') {
    res = getIssues()
  } else {
    let full_url = search_url + keyword + '/' + filterKeyword
    res = await axios.get(full_url, {
      headers: { Authorization: barer_token }
    })
  }
  return res
}
