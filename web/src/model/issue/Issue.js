import React, { useState, useEffect } from 'react'
import { Badge, Button, Form, FormCheck, Table } from 'react-bootstrap'
import { getFilteredIssues } from '../../service/getFilteredIssues'
import { getSortedIssues } from '../../service/getSortedIssues'
import { deleteSelectedIssues } from '../../service/deleteSelectedIssues'
import './scss/issue.scss'
import { getLabels } from '../../service/getLabels'
import { getIssues } from '../../service/getIssues'
import { deleteIssue } from '../../service/deleteIssue'

export default function Issue (props) {
  let [issues, setIssues] = useState([])
  let [checkedIssues, setCheck] = useState([])

  let {
    issueFilter,
    checkStatus,
    sortParams,
    isShow,
    deleteSelections,
    getPages,
    page
  } = props

  const dateOptions = {
    day: '2-digit',
    year: '2-digit',
    month: 'short'
  }
  const timeOptions = {
    hour: '2-digit',
    minute: '2-digit'
  }

  useEffect(() => {
    let keyword = issueFilter
      .slice(0, issueFilter.indexOf(':'))
      .replace(/\s/gi, '')

    let filterKeyword = issueFilter
      .slice(issueFilter.indexOf(':') + 1)
      .replace(/\s/gi, '')

    if (keyword === 'label' && filterKeyword !== '') {
      getLabels().then(l => {
        l.data.map(label =>
          label.labelName === filterKeyword
            ? getFilteredIssues(keyword + ':' + label.id).then(issue =>
                issue.data.content === undefined
                  ? setIssues(issue.data)
                  : setIssues(issue.data.content)
              )
            : null
        )
      })
    } else {
      getFilteredIssues(issueFilter).then(issue =>
        issue.data.content === undefined
          ? setIssues(issue.data)
          : setIssues(issue.data.content)
      )
    }
  }, [issueFilter])

  useEffect(() => {
    if (page) {
      getIssues(page).then(i => setIssues(i.data.content))
    }
  }, [page])

  useEffect(() => {
    if (sortParams !== '' && sortParams !== 'My Issues') {
      getSortedIssues(sortParams).then(issue => {
        setIssues(issue.data.content)
      })
    } else if (sortParams === 'My Issues') {
      getSortedIssues(sortParams).then(issue => {
        setIssues(issue.data)
        getPages(issue.data.length)
      })
    }
  }, [sortParams])

  useEffect(() => {
    let tempArray = []
    if (issues.length !== 0) {
      issues.map(i => tempArray.push({ id: i.id, status: checkStatus }))
      setCheck(tempArray)
    }
  }, [checkStatus]) //after select all changed

  useEffect(() => {
    let selected = []
    let count = 0
    if (checkedIssues.length !== 0) {
      checkedIssues.map(c =>
        c.status === true ? selected.push(c.id) && count++ : null
      )
    }
    if (selected.length > 0 && count > 0) {
      isShow('visible', false)
    } else {
      isShow('hidden', false)
    }
  }, [checkedIssues])

  useEffect(() => {
    if (deleteSelections === true) {
      let selected = []
      checkedIssues.map(c => (c.status === true ? selected.push(c.id) : null))
      deleteSelectedIssues(selected).then(i => {
        setIssues(i.data.content)
      })

      isShow('hidden', true)
    }
  }, [deleteSelections])

  useEffect(() => {
    if (issues !== undefined || issues.length !== 0) {
      getIssues().then(i => getPages(i.data.totalElements))
    }
  }, [])

  function getStatus (id) {
    let status
    let tempArray = []
    if (issues.length !== 0) {
      if (checkedIssues.length === 0) {
        issues.map(i => tempArray.push({ id: i.id, status: false }))
        setCheck(tempArray)
        status = false
      } else {
        checkedIssues.map(i => (i.id === id ? (status = i.status) : null))
      }
    }
    return status
  }
  function handleCheckBoxes (event, id) {
    let tempArray = []
    checkedIssues.map(i => tempArray.push(i))
    tempArray.map((i, index) =>
      i.id === id ? (tempArray[index].status = event.target.checked) : null
    )
    setCheck(tempArray)
  }

  async function deleteIssueOne (id) {
    await deleteIssue(id)
    getIssues().then(i => {
      setIssues(i.data.content)
    })
  }
  return (
    <Table striped bordered hover variant='dark'>
      <tbody className='container'>
        {issues &&
          issues.map(issue => {
            return (
              <tr className='row' key={issue.id}>
                <td className='col-12'>
                  <div className='check-issue-container col-1'>
                    <Form className='check-issue'>
                      <FormCheck
                        type='checkbox'
                        checked={getStatus(issue.id) || false}
                        onChange={e => {
                          handleCheckBoxes(e, issue.id)
                        }}
                      ></FormCheck>
                    </Form>
                  </div>
                  <div className='issue-body col-6'>
                    <h4>
                      {issue.title}
                      <div className='edit-delete'>
                        <Button
                          variant='info'
                          href={'/issues/detail/' + issue.id}
                        >
                          <i className='edit-icon'></i>
                        </Button>
                        <Button
                          variant='danger'
                          onClick={() => deleteIssueOne(issue.id)}
                        >
                          <i className='delete-icon'></i>
                        </Button>
                      </div>
                    </h4>
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
