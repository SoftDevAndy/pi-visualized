# pi-visualized

<img src="https://puu.sh/yf2Fv/c12c542815.png" width="300px" height="300px">

Small mini project inspired from Reddits /r/dataisbeautiful sub-reddit. 

The digit range 0-9 are each assigned an individual color. The first 10k digits of PI are read from a file and each digit is represented as a circle in the image. 

## Changing the colors of the digits

***colors.csv*** hold the color code. There is hardcoded colors in the code aswell if the file reading fails.

Below is a copy of the colors.csv which is the same as the hardcoded values

```
125,105,98
249,174,116
211,206,216
103,83,122
239,247,224
150,212,191
41,69,67
61,48,32
56,46,27
```


## Command Line Arguments Usage

-help Outputs: "e.g -dimension 500 -filein pi.txt -fileout myfilename"
```
-help
```

-dimension Accepts a modulus of 100 as the length and width of the output image.
```
-dimension 1000
```

-filein Accepts a directory and file location string
```
-filein C:\....\myfilename.txt
```

-fileout Accepts a string to tag as your outputted file name. e.g Testing will output Testing.png as the image name
```
-fileout Testing
```
