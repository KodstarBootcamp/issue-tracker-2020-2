import React, { lazy, Suspense } from 'react'
import {
  BrowserRouter as Router,
  Redirect,
  Route,
  Switch
} from 'react-router-dom'

import './app.scss'

function App () {
  const Home = lazy(() => import('./App'))
  const Issues = lazy(() => import('./component/issue/Issues'))
  const CreateIssue = lazy(() => import('./component/issue/CreateIssue'))
  const IssueDetail = lazy(() => import('./component/issue/IssueDetail'))
  //const Login = lazy(() => import('./component/login/Login'))

  const getRoutes = () => {
    let token = localStorage.getItem('item')

    const routes = []

    // const login = [
    //   <Route path='/login' component={Login}></Route>,
    //   <Route path='/' exact component={Home}>
    //     <Redirect to='/login'></Redirect>
    //   </Route>,
    //   <Route path='/issues' exact component={Home}>
    //     <Redirect to='/login'></Redirect>
    //   </Route>,
    //   <Route path='/issues/new' exact component={Home}>
    //     <Redirect to='/login'></Redirect>
    //   </Route>,
    //   <Route path='/issues/detail/:id' exact component={Home}>
    //     <Redirect to='/login'></Redirect>
    //   </Route>
    // ]

    // if (token === '' || token === null) {
    //   return login
    // } else {
    //   return routes
    // }
    return routes
  }

  return (
    <Router>
      <Suspense fallback={<div> Loading... </div>}>
        <Switch>
          <Route path='/' exact component={Home} />,
          <Route path='/issues' exact component={Issues} />,
          <Route path='/issues/new' exact component={CreateIssue} />,
          <Route path='/issues/detail/:id' exact component={IssueDetail} />
        </Switch>
      </Suspense>
    </Router>
  )
}

export default App
