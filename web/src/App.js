import React, { lazy, Suspense } from 'react'
import { Router, Redirect, Route, Switch } from 'react-router-dom'
import history from './service/history'
import './app.scss'

function App () {
  const Home = lazy(() => import('./App'))
  const Issues = lazy(() => import('./component/issue/Issues'))
  const CreateIssue = lazy(() => import('./component/issue/CreateIssue'))
  const IssueDetail = lazy(() => import('./component/issue/IssueDetail'))
  const Login = lazy(() => import('./component/login/Login'))
  const IssueWorkflow = lazy(() => import('./component/issue/IssueWorkflow'))

  const getRoutes = () => {
    let token = localStorage.getItem('token')

    const routesAfterLogin = [
      <Route key={1} path='/' exact component={Home} />,
      <Route key={2} path='/issues' exact component={Issues} />,
      <Route key={3} path='/issues/new' exact component={CreateIssue} />,
      <Route key={4} path='/issues/detail/:id' exact component={IssueDetail} />,
      <Route key={10} path='/flow' exact component={IssueWorkflow} />,
      <Route
        key={0}
        path='/login'
        exact
        component={() => <Redirect to='/' />}
      />
    ]

    const routesBeforeLogin = [
      <Route key={5} path='/login' component={Login} />,
      <Route
        key={6}
        path='/'
        exact
        component={() => <Redirect to='login' />}
      />,
      <Route
        key={7}
        path='/issues'
        exact
        component={() => <Redirect to='login' />}
      />,
      <Route
        key={8}
        path='/issues/new'
        exact
        component={() => <Redirect to='login' />}
      />,
      <Route
        key={9}
        path='/issues/detail/:id'
        exact
        component={() => <Redirect to='login' />}
      />,
      <Route
        key={10}
        path='/flow'
        exact
        component={() => <Redirect to='login' />}
      />
    ]

    if (token === '' || token === null) {
      return routesBeforeLogin
    } else {
      return routesAfterLogin
    }
  }

  return (
    <Router history={history}>
      <Suspense fallback={<div> Loading... </div>}>
        {' '}
        <Switch> {getRoutes().map(r => r)} </Switch>{' '}
      </Suspense>{' '}
    </Router>
  )
}

export default App
