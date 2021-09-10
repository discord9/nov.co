::复制编译好的.jar文件并打开游戏
copy NeoUSSR.jar /y mods\UnityForce\jars\Unity_Force.jar
copy src\data\scripts\UnityForceModPlugin.java /y mods\UnityForce\data\scripts\UnityForceModPlugin.java
xcopy mods\UnityForce /E /Y D:\StarSector\ss0.9.1\Starsector\mods\UnityForce
cd D:\StarSector\ss0.9.1\Starsector\
starsector.exe
cd D:\ModdingSS\NeoUSSR