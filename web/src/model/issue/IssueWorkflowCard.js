import React from 'react'
import { Button } from 'react-bootstrap'

import './scss/issue-workflow-card.scss'

export default function IssueWorkflowCard (props) {
  const { issueTitle, id } = props

  const dragStart = e => {
    localStorage.setItem('dragId', id)
    setTimeout(() => {
      document.getElementById(id).style.display = 'none'
    }, 0)
  }

  const dragOver = e => {
    e.preventDefault()
  }
  return (
    <div
      className='card-container'
      id={id}
      draggable='true'
      onDragStart={e => dragStart(e)}
      onDragOver={e => dragOver(e)}
    >
      <div className='card-body'>
        <Button
          type='button'
          className='card-title'
          href={'/issues/detail/' + id}
        >
          {issueTitle}
        </Button>
      </div>
    </div>
  )
}
