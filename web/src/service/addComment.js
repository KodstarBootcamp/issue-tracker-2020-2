import axios from 'axios'
import { barer_token } from '../custom/httpCustomValues'

export const addComment = async(commentText, issueId) => {
    const url = 'issue/' + issueId + '/comment'

    let comment = { comment: commentText }

    const response = await axios.post(url, comment, {
        headers: { Authorization: barer_token }
    })

    return response
}