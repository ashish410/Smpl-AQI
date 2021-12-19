<h1 align="center">Simple AQI</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=24"><img alt="API" src="https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://ashish1.medium.com/"><img alt="Medium" src="https://skydoves.github.io/badges/Story-Medium.svg"/></a>
</p>

<p align="center">  
Simple AQI is a an android project based on modern Android application tech-stacks and MVVM architecture.<br>This project is for focusing especially on the new jetpack libraries.<br>
Also fetching data from the network and integrating persisted data in the database via repository pattern. The app's purpose is to show live AQI for popular places in India
</p>
<br>

<p align="center">
  <img alt="home" src="https://github.com/ashish410/Smpl-AQI/blob/master/screenshots/home_light.png" width=200/>
  <img alt="article_and_save" src="https://github.com/ashish410/Smpl-AQI/blob/master/screenshots/graph_light.png" width=200/>
  <img alt="search" src="https://github.com/ashish410/Smpl-AQI/blob/master/screenshots/home_dark.png" width=200/>
  <img alt="save_and_delete" src="https://github.com/ashish410/Smpl-AQI/blob/master/screenshots/graph_dark.png" width=200/>
</p><br>

## MAD Scorecard
<p align="center"><img alt="mad_scorecard" src="https://github.com/ashish410/Smpl-AQI/blob/master/screenshots/summary.png" /></p>
<p align="center"><img alt="mad_scorecard" src="https://github.com/ashish410/Smpl-AQI/blob/master/screenshots/kotlin.png" /></p>
<p align="center"><img alt="mad_scorecard" src="https://github.com/ashish410/Smpl-AQI/blob/master/screenshots/studio.png" /></p>
<p align="center"><img alt="mad_scorecard" src="https://github.com/ashish410/Smpl-AQI/blob/master/screenshots/jetpack.png" /></p>
<br>

## Time Taken
- Time Taken: 2 Days
- Features built: City AQI list Screen(1.5 days), Graph Screen(0.5 day) 
<br>

## Libraries and tech used
- Minimum SDK level 24
- Hilt for dependency injection.
- JetPack
  - Flow - notify domain layer data to views.
  - Lifecycle - observing data when lifecycle state changes.
  - ViewModel - lifecycle aware UI related data holder.
  - Room Persistence - construct a database to store city aqi data.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
- [OkHttp3](https://github.com/square/okhttp) - for REST APIs and network data.
- [Gson](https://github.com/google/gson/) - A JSON library for parsing network response.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components for CardView.
<br>

## API
The project uses a websocket, hosted at wss://city-ws.herokuapp.com/
<br>
