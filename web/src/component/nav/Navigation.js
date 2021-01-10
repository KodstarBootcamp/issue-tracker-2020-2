import React from 'react'
import { Button, Nav, Navbar } from 'react-bootstrap'

export function Navigation () {

  const logout = () => {
    localStorage.removeItem('token')
    window.location.reload()
  }

  return (
    <Navbar expand='lg' variant='dart' bg='dark' style={{ zIndex: '100' }}>
      <Navbar.Brand variant='light' href='/'>
        Issue Tracker
      </Navbar.Brand>
      <Navbar.Toggle
        aria-controls='basic-navbar-nav'
        style={{ background: '#007bff' }}
      />
      <Navbar.Collapse id='basic-navbar-nav'>
        <Nav className='mr-auto'>
          <Nav.Link href='/'>Home</Nav.Link>
          <Nav.Link href='/issues'>Issues</Nav.Link>
        </Nav>
        <Button onClick={() => logout()}>Log out</Button>
      </Navbar.Collapse>
    </Navbar>
  )
}
