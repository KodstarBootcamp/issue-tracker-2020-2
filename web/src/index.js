import React from 'react'
import ReactDOM from 'react-dom'
import './index.scss'
import reportWebVitals from './reportWebVitals'
import 'bootstrap/dist/css/bootstrap.min.css'
import { Navigation } from './component/nav/Navigation'
import axios from 'axios'
import App from './App'

// axios.defaults.baseURL =
//   'IssueTracker2020-env.eba-2q9injz6.us-east-2.elasticbeanstalk.com/'

axios.defaults.baseURL = 'http://issuetracker2020-env.eba-2q9injz6.us-east-2.elasticbeanstalk.com/'

ReactDOM.render(
  <React.StrictMode>
    <Navigation />
    <App />
  </React.StrictMode>,
  document.getElementById('root')
)

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals()
