import React, { useState } from 'react'
import { Form, Row, Button } from 'react-bootstrap'
import axios from 'axios'

import './scss/login.scss'

export default function Login () {
  let [userName, setUserName] = useState('')
  let [password, setPassword] = useState('')

  const handleUserName = e => {
    setUserName(e.target.value)
  }
  const handlePassword = e => {
    setPassword(e.target.value)
  }
  const login = async () => {
    await axios
      .post('/login', {
        username: userName,
        password: password
      })
      .then(
        r => (
          localStorage.setItem('token', r.headers.authorization),
          window.location.reload()
        )
      ).catch(e=> alert("Check your credentials!"))
  }
  return (
    <div className='container-fluid'>
      <Row>
        <div className='offset-4 col-4 offset-4 form-container'>
          <Form>
            <Form.Group controlId='formBasicUserName'>
              <Form.Control
                type='text'
                placeholder='Enter username'
                value={userName}
                onChange={e => handleUserName(e)}
                className='username-input'
              />
            </Form.Group>
            <Form.Group controlId='formBasicPassword'>
              <Form.Control
                type='password'
                placeholder='Password'
                value={password}
                onChange={e => handlePassword(e)}
                className='password-input'
              />
            </Form.Group>
            <Button
              variant='primary'
              type='button'
              onClick={() => login()}
              block
            >
              Submit
            </Button>
          </Form>
        </div>
      </Row>
    </div>
  )
}
