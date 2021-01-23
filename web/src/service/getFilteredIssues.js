import axios from 'axios'

import { getIssues } from './getIssues'

export const getFilteredIssues = async (searchFilter, page = 1, size = 3) => {
  const search_url = 'issues/search/'

  let filterKeyword = searchFilter
    .slice(searchFilter.indexOf(':') + 1)
    .replace(/\s/gi, '')

  let keyword = searchFilter
    .slice(0, searchFilter.indexOf(':'))
    .replace(/\s/gi, '')

  let res
  if (searchFilter === '') {
    res = getIssues()
  } else {
    let full_url =
      search_url +
      keyword +
      '/' +
      filterKeyword +
      '?page=' +
      (page - 1) +
      '&size=' +
      size
    res = await axios.get(full_url, {
      headers: { Authorization: localStorage.getItem('token') }
    })
  }
  return res
}
