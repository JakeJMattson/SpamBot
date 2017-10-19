@echo off
if not DEFINED IS_MINIMIZED set IS_MINIMIZED=1 && start "" /min "%~dpnx0" %* && exit
Taskkill /IM javaw.exe /F
Taskkill /IM chrome.exe /F
Taskkill /IM chromedriver.exe /F
exit