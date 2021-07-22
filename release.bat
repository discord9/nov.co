set zip_name = UnityForce_imbaVersion%date:~0,4%%date:~5,2%%date:~8,2%%time:~0,2%%time:~3,2%
zip src.zip -xi -r src
zip %zip_name% -xi -r mods\UnityForce