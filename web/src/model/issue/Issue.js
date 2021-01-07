import React, { useState, useEffect } from 'react'
import { Badge, Form, FormCheck, Table } from 'react-bootstrap'
import { getIssues } from '../../service/getIssues'
import { getFilteredIssues } from '../../service/getFilteredIssues'
import { getSortedIssues } from '../../service/getSortedIssues'
import './scss/issue.scss'

export default function Issue (props) {
  let [issues, setIssues] = useState([])
  let [checkedIssues, setCheck] = useState([])

  const { issueFilter, checkStatus, sortParams } = props

  const dateOptions = {
    day: '2-digit',
    year: '2-digit',
    month: 'short'
  }
  const timeOptions = {
    hour: '2-digit',
    minute: '2-digit'
  }

  const handleChanges = (e, id) => {}

  useEffect(() => {
    getFilteredIssues(issueFilter).then(issue => {
      setIssues(issue.data)
    })
  }, [issueFilter])

  useEffect(() => {
    if (sortParams !== '') {
      getSortedIssues(sortParams).then(issue => {
        setIssues(issue.data)
      })
    }
  }, [sortParams])

  useEffect(() => {
    let tempArray = []
    if (issues.length !== 0) {
      issues.map(i => {
        tempArray.push({ id: i.id, status: checkStatus })
      })
      setCheck(tempArray)
    }
  }, [checkStatus]) //after select all changed

  function getStatus (id) {
    if (checkedIssues === undefined || checkedIssues.length === 0) {
      return false
    } else {
      checkedIssues.map(i => {
        if (i.id === id) {
          return i.status
        }
      })
    }
  }

  return (
    <Table striped bordered hover variant='dark'>
      <tbody className='container'>
        {issues.map(issue => {
          return (
            <tr className='row' key={issue.id}>
              <td className='col-12'>
                <div className='check-issue-container col-1'>
                  <Form className='check-issue'>
                    <FormCheck
                      type='checkbox'
                      checked={getStatus(issue.id)}
                      onChange={() => {}}
                    ></FormCheck>
                  </Form>
                </div>
                <div className='issue-body col-6'>
                  <h4>{issue.title}</h4>
                  <p className='description-text'>{issue.description}</p>
                </div>
                <div className='offset-1 col-2 times'>
                  <div className='date-container'>
                    created:{' '}
                    <p className='date'>
                      {new Date(issue.createTime).toLocaleDateString(
                        'en-US',
                        dateOptions
                      )}
                    </p>
                    <p className='hour'>
                      {new Date(issue.createTime).toLocaleTimeString(
                        [],
                        timeOptions
                      )}
                    </p>
                  </div>
                  <div className='date-container'>
                    updated:{' '}
                    <p className='date'>
                      {new Date(issue.updateTime).toLocaleDateString(
                        'en-US',
                        dateOptions
                      )}
                    </p>
                    <p className='hour'>
                      {new Date(issue.updateTime).toLocaleTimeString(
                        [],
                        timeOptions
                      )}
                    </p>
                  </div>
                </div>
                <div className='labels-container col-2'>
                  <div className='labels'>
                    {issue.labels.map(label => {
                      return (
                        <Badge
                          pill
                          style={{ backgroundColor: label.labelColor }}
                          key={label.id}
                        >
                          #{label.labelName}
                        </Badge>
                      )
                    })}
                  </div>
                </div>
              </td>
            </tr>
          )
        })}
      </tbody>
    </Table>
  )
}
