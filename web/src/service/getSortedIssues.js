import axios from 'axios'
import { barer_token } from '../custom/httpCustomValues'

export const getSortedIssues = async sortKeyword => {
  const url = 'issues'
  //orderType: "desc" for descending or "asc" for ascending
  //byWhichSort: "update" for update time or "createDate" for create time
  let params_string = ''

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

  let response = await axios.get(url + params_string, {
    headers: { Authorization: barer_token }
  })

  return response
}
