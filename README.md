# offset-to-function

Simple ghidra script to assign function symbols to offsets provided in a properly formatted text file. Parameters are not supported

Proper format is as follows:
```
<function name>:<offset>
functionName:12345678AB
```

The offset needs to be the proper text address, so offset `426180` would be need to be written as `7100426180` for aarch64, as an example.
