set hour=%time:~0,2%
for /l %%i in (0,1,9) do ( if %hour%==%%i set hour=0%%i)
echo %hour%
set zip_name=UnityForce_imbaVersion%date:~0,4%%date:~5,2%%date:~8,2%%hour%%time:~3,2%
echo %zip_name%
pause
zip src.zip -xi -r src
copy src.zip /y mods\UnityForce\jars
zip releases\%zip_name% -xi -r mods\UnityForce