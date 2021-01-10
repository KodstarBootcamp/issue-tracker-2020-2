import axios from 'axios'
import { base_url } from '../custom/httpCustomValues'
import { getIssues } from './getIssues'

export const getFilteredIssues = async searchFilter => {
  const title_search_url = base_url + 'issues/search/title/'
  const description_search_url = base_url + 'issues/search/description/'
  const label_search_url = base_url + 'issues/search/label/'

  const header = {
    Authorization:
      'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYxMTA2NzY5Mn0.gusU3gRCFY3hZxv0ESw0yU5qYPK-KxuLcxsygK2EjXmtzR82OZPsYFS8qm8gNYNqWZTr265bnbp4_4O2nj6deg'
  }
  let filterKeyword = searchFilter
    .slice(searchFilter.indexOf(':') + 1)
    .replace(/\s/gi, '')

  let keyword = searchFilter
    .slice(0, searchFilter.indexOf(':'))
    .replace(/\s/gi, '')

  if (searchFilter === '' || filterKeyword === '') {
    return getIssues()
  } else if (searchFilter.indexOf(':') === -1 || keyword === 'title') {
    return await axios.get(title_search_url + filterKeyword, {
      headers: header
    })
  } else if (searchFilter.indexOf(':') !== -1 && keyword === 'description') {
    return await axios.get(description_search_url + filterKeyword, {
      headers: header
    })
  } else if (searchFilter.indexOf(':') !== -1 && keyword === 'label') {
    return await axios.get(label_search_url + filterKeyword, {
      headers: header
    })
  }
}
