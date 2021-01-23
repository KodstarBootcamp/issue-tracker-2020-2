import axios from 'axios'

export const getSortedIssues = async (sortKeyword, page = 1, size = 3) => {
  let url = 'issues'
  //orderType: "desc" for descending or "asc" for ascending
  //byWhichSort: "update" for update time or "createDate" for create time
  let params_string = ''

  switch (sortKeyword) {
    case 'Newest':
      params_string =
        '?orderType=desc&byWhichSort=createDate&page=' +
        (page - 1) +
        '&size=' +
        size
      break
    case 'Oldest':
      params_string =
        '?orderType=asc&byWhichSort=createDate&page=' +
        (page - 1) +
        '&size=' +
        size
      break
    case 'Recently Updated':
      params_string =
        '?orderType=desc&byWhichSort=update&page=' +
        (page - 1) +
        '&size=' +
        size
      break
    case 'Least Recently Updated':
      params_string =
        '?orderType=asc&byWhichSort=update&page=' + (page - 1) + '&size=' + size
      break
    case 'My Issues':
      url += '/user/issues'
      params_string = ''
      break

    default:
      params_string = ''

      break
  }

  let response = await axios.get(url + params_string, {
    headers: { Authorization: localStorage.getItem('token') }
  })
  
  return response
}
