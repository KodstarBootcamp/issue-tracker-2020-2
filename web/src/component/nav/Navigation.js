import React from 'react'
import { Nav, Navbar } from 'react-bootstrap'

export function Navigation () {
  return (
    <Navbar expand='lg' variant='dart' bg='dark'>
      <Navbar.Brand variant='light' href='#'>
        Issue Tracker
      </Navbar.Brand>
      <Navbar.Toggle
        aria-controls='basic-navbar-nav'
        style={{ background: '#007bff' }}
      />
      <Navbar.Collapse id='basic-navbar-nav'>
        <Nav className='mr-auto'>
          <Nav.Link href='/home'>Home</Nav.Link>
          <Nav.Link href='/issues'>Issues</Nav.Link>
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  )
}
