# WeatherChallenge
Code Challenge from Zhi Cui

# Core Require
1. Home screen:
- Showing a list of locations that the user has bookmarked previously.
- Show a way to remove locations from the list.
- Add locations by placing a pin on map.
2. City screen: 
- nce the user clicks on a bookmarked city this screen will appear. On this
screen the user should be able to see:
- Today’s forecast, including: temperature, humidity, rain chances and wind
information.

# Test Info
Tested on:
Real devices:  LGE Nexus 5X API 27
Vurtual device:  Nexus 5X API 27/ Nexus 5 API 25/ Nexus 5 API 27

# Introduction
1. Home Page
Display a list of saved city from local private file
Able to delete city click on the right end of the cell
Click cell download JSON data then go to Detail Page
2. Setting Page
Able to change the metric/imperial by update url
Clear cities: 
Check number of city saved, if there is no cities saved pop a toast alert user, if with data, clear them.
Override back button, to refresh home page.
3. Add pin on map
Click the '+' on right cornor to open the mapview
Auto focus on the your device last position
Click on map, will add a pin also show info-window (Unable to add Unknown city)
Click on infowindow save city to list then back to home page
4. Detail page
Display total 5 day weather forecast(Date, temp, condicition, max-temp, min-temp, etc)
Achieve the  5day/3h API(JSON) is a bit complex than I thought

Thank you for your time viewing this project.
Let me know if you need more information from me, and look forward to hearing from you.


