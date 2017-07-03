If you are running in sbt eclipse, Please make sure 

1. sbt is installed 
2. Sbt Plugin is added in plugins.sbt. To do so follow below steps

```

$cd
$ cd .sbt
$ ls
0.13  boot
$ cd 0.13/
$ mkdir plugins
$ cd plugins/
$ vi plugins.sbt
#Add below line in plugins.sbt and save
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "4.0.0")

```


In project directory, run below command

sbt eclipse

Then run,
sbt clean package

