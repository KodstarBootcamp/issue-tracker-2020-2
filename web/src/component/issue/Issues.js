import React, { useEffect, useState } from 'react'
import {
  Container,
  Form,
  Navbar,
  Nav,
  Dropdown,
  Badge,
  InputGroup,
  Button
} from 'react-bootstrap'
import Issue from '../../model/issue/Issue'
import { getLabels } from '../../service/getLabels'

import './scss/issues.scss'

export default function Issues () {
  let [labels, setItem] = useState([])
  let [filteredLabels, setFilteredLabels] = useState([])
  let [filterOfLabel, setLabelFilter] = useState('')
  let [filterIssue, setIssueFilter] = useState('')
  let [check, setCheck] = useState(false)
  let [sort, setSort] = useState('')
  let [showDeleteButton, setVisibility] = useState('hidden')
  let [deleteSelected, setDelete] = useState(false)

  useEffect(() => {
    getLabels().then(labels => {
      setItem(labels.data)
      setFilteredLabels(labels.data)
    })
  }, [])

  useEffect(() => {
    let updatedLabels = []
    labels.forEach(element =>
      element.labelName.search(filterOfLabel) !== -1
        ? updatedLabels.push(element)
        : null
    )
    setFilteredLabels([...updatedLabels])
  }, [filterOfLabel])

  const handleFilterIssue = e => {
    setIssueFilter(e.target.value)
  }

  const handleLabelSelection = e => {
    setIssueFilter('label:' + e)
  }

  const handleCheckBox = e => {
    setCheck(e.target.checked)
  }
  const askDelete = (willShow, isDeleted) => {
    setVisibility(willShow)
  }

  const sendDelete = () => {
    if (showDeleteButton === 'visible') {
      setDelete(true)
    }
  }

  const sortSelections = [
    'Newest',
    'Oldest',
    'Recently Updated',
    'Least Recently Updated'
  ]

  return (
    <div className='issues'>
      <Container className='issue-container'>
        <Navbar collapseOnSelect expand='lg' bg='dark' variant='dark'>
          <Form>
            <Form.Check
              checked={check}
              onChange={e => {
                handleCheckBox(e)
              }}
            ></Form.Check>
            <button
              type='button'
              className='delete-all'
              style={{ visibility: showDeleteButton }}
              onClick={() => sendDelete()}
            >
              <i className='delete-all-icon'></i>
              <span className='delete-all-text'>{'(delete all)'}</span>
            </button>
            <div className='search-container'>
              <Form.Control
                className='search'
                placeholder='title:  description:  label:'
                onChange={handleFilterIssue.bind(this)}
                value={filterIssue}
              ></Form.Control>
            </div>
          </Form>
          <Navbar.Toggle aria-controls='responsive-navbar-nav' />
          <Navbar.Collapse id='responsive-navbar-nav'>
            <Nav className='mr-auto'></Nav>
            <Nav>
              <Dropdown className='sort-dropdown'>
                <Dropdown.Toggle id='dropdown-sort' variant='outline-info'>
                  Sort
                </Dropdown.Toggle>
                <Dropdown.Menu className='sort-dropdown-container'>
                  {sortSelections.map((s, i) => (
                    <Dropdown.Item
                      key={i}
                      onClick={() => {
                        setSort(s)
                      }}
                    >
                      {s}
                    </Dropdown.Item>
                  ))}
                </Dropdown.Menu>
              </Dropdown>
              <Dropdown className='label-dropdown'>
                <Dropdown.Toggle id='dropdown-label' variant='outline-info'>
                  Label
                </Dropdown.Toggle>
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
                  <Dropdown.Item
                    onSelect={() => {
                      handleLabelSelection('')
                    }}
                    onClick={() => {
                      setLabelFilter('unlabeled')
                    }}
                  >
                    unlabeled
                  </Dropdown.Item>
                  {filteredLabels.map(label => {
                    return (
                      <Dropdown.Item
                        onSelect={() => {
                          handleLabelSelection(label.labelName)
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
              <Button
                href='/issues/new'
                type='button'
                className='add-issue-btn'
                variant='outline-success'
              >
                Add Issue
              </Button>
            </Nav>
          </Navbar.Collapse>
        </Navbar>
        <Issue
          issueFilter={filterIssue}
          checkStatus={check}
          sortParams={sort}
          isShow={askDelete}
          deleteSelections={deleteSelected}
        />
      </Container>
    </div>
  )
}
