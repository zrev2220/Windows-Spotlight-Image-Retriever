rem get paths for assets directory and possible images directory
set assets="%userprofile%/AppData/Local/Packages/Microsoft.Windows.ContentDeliveryManager_cw5n1h2txyewy/LocalState/Assets"
set imageFolder=".\Possible Images"

rem if image folder does not exist, make it
if not exist %imageFolder% mkdir %imageFolder%

rem clear all files from image folder
del /s /q %imageFolder%

rem copy all files from assets folder to image folder and add .jpg extension
xcopy %assets% %imageFolder%
cd %imageFolder%
ren * *.jpg
cd ..

rem open file explorer to image folder 
start explorer %imageFolder%

rem exit
