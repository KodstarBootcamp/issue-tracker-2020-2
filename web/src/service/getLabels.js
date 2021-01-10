import axios from 'axios'
import { barer_token } from '../custom/httpCustomValues'

export const getLabels = async () => {
  const url = 'issues/labels'

  let response = await axios.get(url, {
    headers: { Authorization: barer_token }
  })

  return response
}
