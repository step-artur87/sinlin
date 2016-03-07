Sinlin is a simple svg (xml) preprocessor, than also can take data from .ods files (Open Ofiice Calc). Its functionality similar to Emmet (http://emmet.io/) -- you write little text and get big.
It has only command line interface.

Program takes svg  or any other xml file, searchs tags with special text in attributes (separated by "$") and generate new svg (xml) file in which each that tag copied (with its nodes) once per value extracted from its special attributes text.

If text is "$1;1;10" then extracted values is [1, 2, 3, 4, 5].

If text is "$1, 2, 3" then extracted values is [1, 2, 3].

If text is "$log10([1, 2, 3])" then extracted values is [0, 0.3010299956639812, 0.47712125471966244
].

If text is "$prices" then values get from cells of cellrange "prices" in defind .ods file.

If tag is:
```
<circle x="$1;10;50" y ="0" r="5">
```
then out file will consist tags:
```
<circle x="10" y ="0" r="5">
<circle x="20" y ="0" r="5">
<circle x="30" y ="0" r="5">
<circle x="40" y ="0" r="5">
```
Example:

java -jar sinlin.jar -i sine.svg

Input file

```
<svg width="200" height="100">
	<rect x="0" y="0" width="200" height="100"/>
	<g transform="translate($0;10;100$,0)" style="fill:rgb($255-[0;1;10]*25$,255,$[0;1;10]*25$)">
		<circle cx="$0;10;210" cy="$100-100*sin([0;1;21]/20*3.14)" r="$0;1;21"/>
	</g>	
</svg>
```

Output file (![svg](../master/example/sine.svg__out.svg))

![svg](https://pbs.twimg.com/media/CVD4H9XWoAELO6x.png)

See also:

Twitter https://twitter.com/sinlinSvg

Sourceforge https://sourceforge.net/projects/sinlin/

Tutorial https://sourceforge.net/projects/sinlin/files/sinlin_tutorial.pdf/download
