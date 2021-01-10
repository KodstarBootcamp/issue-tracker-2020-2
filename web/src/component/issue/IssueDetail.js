import React, { useEffect, useState } from 'react'
import { Badge, Container, Row, Form, Button } from 'react-bootstrap'
import { getIssue } from '../../service/getIssue'
import { addComment } from '../../service/addComment'
import { editIssue } from '../../service/editIssue'
import { deleteComment } from '../../service/deleteComment'
import './scss/issue-detail.scss'

export default function IssueDetail (props) {
  let [issue, setIssue] = useState({})
  let [title, setTitle] = useState(issue.title || '')
  let [description, setDescription] = useState(issue.description || '')
  let [comment, setComment] = useState('')
  let [showForm, setShowForm] = useState('none')
  let [showIssue, setShowIssue] = useState('')

  const { id } = props.match.params

  useEffect(() => {
    getIssue(id).then(i => {
      setIssue(i.data)
      setTitle(i.data.title)
      setDescription(i.data.description)
    })
  }, [id])

  const getIssues = async () => {
    getIssue(id).then(i => {
      setIssue(i.data)
    })
  }

  const getDaySub = c => {
    let date = new Date()
    let currentDay = date.getDate()
    let modifyDay = new Date(c.modifyTime).getDate()
    return currentDay - modifyDay
  }

  const handleComment = e => {
    setComment(e.target.value)
  }
  const handleTitle = e => {
    setTitle(e.target.value)
  }
  const handleDescription = e => {
    setDescription(e.target.value)
  }

  const showEdit = () => {
    setShowIssue('none')
    setShowForm('')
  }
  const hideEdit = () => {
    setShowForm('none')
    setShowIssue('')
  }
  const saveIssue = async () => {
    await editIssue(id, title, description)
    getIssues()
    hideEdit()
  }
  const sendComment = async () => {
    await addComment(comment, id)
    getIssues()
  }

  const deleteThisComment = async comment => {
    await deleteComment(id, comment.id)
    getIssues()
  }

  return (
    <Container className='issue-detail-container'>
      <Row>
        <div className='issue-card offset-1 col-10 offset-1 row'>
          <div className='issue-body col-12'>
            <div className='title-description' style={{ display: showIssue }}>
              <h4>
                {issue.title}
                <span className='offset-1 col-1 icon-container'>
                  <Button
                    variant='outline-info'
                    className='edit-comment'
                    onClick={() => showEdit()}
                  >
                    <i className='edit-comment-icon'></i>
                  </Button>
                  <Button variant='outline-danger' className='delete-comment'>
                    <i className='delete-comment-icon'></i>
                  </Button>
                </span>
              </h4>
              <p>{issue.description}</p>
            </div>
            <Row className='edit-container' style={{ display: showForm }}>
              <div className='title-description-edit col-8'>
                <Form style={{ display: 'block' }}>
                  <Form.Control
                    className='title-edit'
                    value={title}
                    onChange={e => handleTitle(e)}
                  ></Form.Control>
                  <Form.Control
                    className='description-edit'
                    value={description}
                    onChange={e => handleDescription(e)}
                  ></Form.Control>
                </Form>
              </div>
              <Button
                className='col-1 save-btn'
                onClick={() => saveIssue()}
                variant='outline-success'
              >
                Save
              </Button>
              <Button
                className='col-1 cancel-btn'
                variant='outline-danger'
                onClick={() => hideEdit()}
              >
                Cancel
              </Button>
            </Row>
            {issue.labels &&
              issue.labels.map(l => {
                return (
                  <Badge
                    key={l.id}
                    style={{ background: l.labelColor }}
                    className='labels'
                  >
                    {l.labelName}
                  </Badge>
                )
              })}
          </div>
        </div>
        <div className='issue-comments col-12'>
          {issue.comments &&
            issue.comments.map(c => {
              return (
                <Row style={{ height: '110px' }}>
                  <Container>
                    <div className='timeline'>
                      <i className='comment-icon'></i>
                    </div>
                    <div className='comment-body-container offset-1 col-10'>
                      <div className='comment-body '>
                        <div className='comment-header'>
                          <h6 className='col-10'>
                            Comment was{' '}
                            <span>
                              {'created ' +
                                (getDaySub(c) === 0
                                  ? 'today'
                                  : getDaySub(c) + ' days before')}
                            </span>
                          </h6>
                          <span className='offset-1 col-1 icon-container'>
                            <Button
                              variant='outline-danger'
                              className='delete-comment'
                              onClick={() => {
                                deleteThisComment(c)
                              }}
                            >
                              <i className='delete-comment-icon'></i>
                            </Button>
                          </span>
                        </div>
                        <div className='comment-text'>
                          <p>{c.comment}</p>
                        </div>
                      </div>
                    </div>
                  </Container>
                </Row>
              )
            })}
          <Form className='comment-form row'>
            <Form.Control
              value={comment}
              onChange={e => handleComment(e)}
              className='comment-textarea col-10'
              as='textarea'
              placeholder='Add your comment'
            ></Form.Control>
            <Button
              onClick={() => {
                if (comment !== undefined || comment === '') {
                  sendComment()
                }
              }}
              className='add-comment-btn col-2'
              variant='success'
              type='button'
            >
              Add Comment
            </Button>
          </Form>
        </div>
      </Row>
    </Container>
  )
}
