import React, { useState, useEffect } from 'react'
import { Button, Form, Row, Dropdown, Badge, InputGroup } from 'react-bootstrap'
import { getLabels } from '../../service/getLabels'
import { createIssue } from '../../service/createIssue'
import './scss/create-issue.scss'

export default function CreateIssue (props) {
  let [labels, setLabels] = useState([])
  let [filterOfLabel, setLabelFilter] = useState('')
  let [filteredLabels, setFilteredLabels] = useState([])
  let [selectedLabels, setSelectedLabels] = useState([])
  let [title, setTitle] = useState('')
  let [description, setDescription] = useState('')

  const handleLabelSelection = (labelName, labelColor) => {
    let tempArray = selectedLabels || [] //if selectedLabels is undefined
    let allLabels = filteredLabels
    if (tempArray.length === 0) {
      allLabels.map(l =>
        l.labelName === labelName
          ? tempArray.push({ labelName: labelName, labelColor: labelColor })
          : null
      )
    } else {
      let founded = false
      tempArray.map(l => (labelName === l.labelName ? (founded = true) : null))
      founded
        ? alert('Label is already added')
        : tempArray.push({ labelName: labelName, labelColor: labelColor })
    }
    setSelectedLabels(tempArray)
  }
  const removeSelectedLabel = labelName => {
    let tempArray = []
    selectedLabels.map(a =>
      a.labelName !== labelName ? tempArray.push(a) : null
    )

    setSelectedLabels(tempArray)
  }

  const handleTitle = event => {
    setTitle(event.target.value)
  }
  const handleDescription = event => {
    setDescription(event.target.value)
  }
  const addIssue = () => {
    createIssue(title, description, selectedLabels)
    props.history.push('/issues')
  }

  useEffect(() => {
    let updatedLabels = []
    if (labels.length === 0) {
      getLabels().then(l => {
        setFilteredLabels(l.data)
        setLabels(l.data)
      })
    } else {
      labels.map(fl =>
        fl.labelName.search(filterOfLabel) !== -1
          ? updatedLabels.push(fl)
          : null
      )
      setFilteredLabels(updatedLabels)
    }
  }, [filterOfLabel])

  return (
    <div className='create-container container-fluid'>
      <Row>
        <div className='offset-1 col-7 issue-container'>
          <Form>
            <Form.Group>
              <Form.Label className='title-label'>Title : </Form.Label>
              <Form.Control
                type='text'
                className='add-title'
                onChange={e => handleTitle(e)}
                value={title}
              ></Form.Control>
              <Form.Label className='description-label'>
                Description :{' '}
              </Form.Label>
              <textarea
                className='add-description'
                onChange={e => handleDescription(e)}
                value={description}
              ></textarea>
              <Button
                className='submit-issue'
                type='button'
                variant='outline-success'
                onClick={() => addIssue()}
                block
              >
                Create Issue
              </Button>
            </Form.Group>
          </Form>
        </div>
        <div className='col-3 issue-props-container'>
          <Dropdown className='label-dropdown'>
            <Dropdown.Toggle id='dropdown-label' variant='outline-info' block>
              Labels
            </Dropdown.Toggle>
            <div className='selected-labels-container'>
              {selectedLabels &&
                selectedLabels.map((labels, i) => {
                  return (
                    <Badge
                      className='added-labels'
                      key={i++}
                      style={{ background: labels.labelColor }}
                      onClick={() => removeSelectedLabel(labels.labelName)}
                    >
                      {labels.labelName}
                    </Badge>
                  )
                })}
            </div>
            <Dropdown.Menu className='label-dropdown-container'>
              <Form>
                <InputGroup>
                  <Form.Control
                    type='text'
                    placeholder='type something..'
                    value={filterOfLabel}
                    onChange={e => setLabelFilter(e.target.value)}
                  ></Form.Control>
                  <InputGroup.Append>
                    <Button
                      onClick={() => {
                        setLabelFilter('')
                      }}
                      variant='outline-secondary'
                    >
                      <i className='clean-icon'></i>
                    </Button>
                  </InputGroup.Append>
                </InputGroup>
              </Form>
              {filteredLabels.map(label => {
                return (
                  <Dropdown.Item
                    onSelect={() => {
                      handleLabelSelection(label.labelName, label.labelColor)
                    }}
                    href=''
                    key={label.id}
                    onClick={() => {
                      setLabelFilter(label.labelName)
                    }}
                  >
                    <Badge style={{ backgroundColor: label.labelColor }}>
                      {label.labelName[0].toUpperCase()}
                    </Badge>
                    {label.labelName}
                  </Dropdown.Item>
                )
              })}
            </Dropdown.Menu>
          </Dropdown>
        </div>
      </Row>
    </div>
  )
}
