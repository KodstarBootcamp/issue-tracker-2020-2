import React, { useEffect, useState } from 'react'
import { Form, InputGroup, Row, Button } from 'react-bootstrap'
import { getIssues } from '../../service/getIssues'
import { getStatus } from '../../service/getStates'
import { changeState } from '../../service/changeState'
import { addColumn } from '../../service/addColumn'
import IssueWorkflowCard from '../../model/issue/IssueWorkflowCard'

import './scss/issue-workflow.scss'

export default function IssueWorkflow (props) {
  let [issues, setIssues] = useState([])
  let [status, setStatus] = useState([])
  let [show, setShow] = useState('none')
  let [stateName, setStateName] = useState('')

  useEffect(() => {
    if (issues.length === 0) {
      getIssues(1, 10).then(i => setIssues(i.data.content))
      getStatus().then(s => setStatus(s.data))
    }
  }, [])

  const drop = async (e, stateId) => {
    const card_id = localStorage.getItem('dragId')

    const card = document.getElementById(card_id)
    card.style.display = 'block'
    e.target.appendChild(card)
    await changeState(card_id, stateId)
    getIssues(1, 10).then(i => setIssues(i.data.content))
  }

  const dragOver = e => {
    e.preventDefault()
  }

  const addNewColumn = async () => {
    if (stateName !== '') {
      setStateName('')
      setShow('none')
      await addColumn(stateName)
      await getStatus().then(s => setStatus(s.data))
    }
  }

  const isVisible = () => {
    setShow('')
  }

  const handleStateName = e => {
    setStateName(e.target.value)
  }
  return (
    <div className='container-fluid workflow-body'>
      <Row>
        <div className='flow-container'>
          {status &&
            status.sort(function (a, b) {
              return a.id - b.id
            }) &&
            status.map(s => {
              return (
                <div
                  key={s.id}
                  className='flow-item'
                  onDrop={e => drop(e, s.id)}
                  onDragOver={dragOver}
                >
                  <div className='flow-header'>{s.name}</div>
                  <div className='flow-body'>
                    {issues &&
                      issues.map(i =>
                        i.state.stateName === s.name ? (
                          <IssueWorkflowCard
                            key={i.id}
                            issueTitle={i.title}
                            id={i.id}
                          />
                        ) : null
                      )}
                  </div>
                </div>
              )
            })}
          <div className='empty-container'>
            <InputGroup className='state-form' style={{ display: show }}>
              <Form.Control
                id='inlineFormInputGroupUsername'
                placeholder='Column Name'
                value={stateName}
                onChange={e => handleStateName(e)}
              />
              <InputGroup.Append>
                <Button variant='outline-success' onClick={addNewColumn}>
                  +
                </Button>
              </InputGroup.Append>
            </InputGroup>
            <span className='empty-container-text' onClick={isVisible}>
              Add New Column
            </span>
          </div>
        </div>
      </Row>
    </div>
  )
}
