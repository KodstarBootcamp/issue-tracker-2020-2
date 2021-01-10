import axios from 'axios'
import { base_url } from '../custom/httpCustomValues'

export const getSortedIssues = async sortKeyword => {
  const url = base_url + 'issues'
  //orderType: "desc" for descending or "asc" for ascending
  //byWhichSort: "update" for update time or "createDate" for create time
  let params_string = ''

  const header = {
    Authorization:
      'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYxMTA2NzY5Mn0.gusU3gRCFY3hZxv0ESw0yU5qYPK-KxuLcxsygK2EjXmtzR82OZPsYFS8qm8gNYNqWZTr265bnbp4_4O2nj6deg'
  }

  switch (sortKeyword) {
    case 'Newest':
      params_string = '?orderType=desc&byWhichSort=createDate'
      break
    case 'Oldest':
      params_string = '?orderType=asc&byWhichSort=createDate'
      break
    case 'Recently Updated':
      params_string = '?orderType=desc&byWhichSort=update'
      break
    case 'Least Recently Updated':
      params_string = '?orderType=asc&byWhichSort=update'
      break

    default:
      params_string = ''

      break
  }

  let response = await axios.get(url + params_string, { headers: header })

  return response
}
