import React, { Suspense, lazy } from 'react'
import ReactDOM from 'react-dom'
import './index.scss'
import reportWebVitals from './reportWebVitals'
import 'bootstrap/dist/css/bootstrap.min.css'
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import { Navigation } from './component/nav/Navigation'

const Issues = lazy(() => import('./component/issue/Issues'))
const CreateIssue = lazy(() => import('./component/issue/CreateIssue'))
const IssueDetail = lazy(() => import('./component/issue/IssueDetail'))

ReactDOM.render(
  <React.StrictMode>
    <Navigation />
    <Router>
      <Suspense fallback={<div> Loading... </div>}>
        {' '}
        <Switch>
          <Route path='/issues' exact component={Issues} />{' '}
          <Route path='/issues/new' exact component={CreateIssue} />{' '}
          <Route path='/issues/detail/:id' exact component={IssueDetail} />{' '}
        </Switch>{' '}
      </Suspense>{' '}
    </Router>{' '}
  </React.StrictMode>,
  document.getElementById('root')
)

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals()
