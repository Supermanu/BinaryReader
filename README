The first goal of BinaryReader is to simply describe and extract data (in json) from binary file.
Currently, it only works with NWN models.

## Compilation ##
You need sbt in order to compile BinaryReader. Go in the root directory and launch:
```
sbt assembly
```
After downloading all the dependencies, it will compile and build a jar in `root_directory/target/scala-2.11`

## Usage ##

You can run the jar with:
```
java -jar path_to_jar/BinaryReader-assembly-0.1.0.jar -j path_to_your_mdl
```
It will write the json in the standard output, so if you want it directly in a file:

```
java -jar path_to_jar/BinaryReader-assembly-0.1.0.jar -j path_to_your_mdl > output.json
```

Any comment are welcome!