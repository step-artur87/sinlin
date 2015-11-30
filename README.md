This command-line program takes svg (xml) file with special attributes (text) in tags (optional also takes ods file with data) and generate new svg (xml) file in which each tag copied once per value extracted from its special attributes (text).

Example:

Input file

```
<svg
	xmlns="http://www.w3.org/2000/svg"
	xmlns:xlink="http://www.w3.org/1999/xlink" 
	version="1.1" 
	width="200"
	height="100">
	<rect x="0" y="0" width="200" height="100"/>
	<g transform="translate($0;10;100$,0)"
		style="fill:rgb($255-[0;1;10]*25$,255,$[0;1;10]*25$)">		
		<circle cx="$0;10;210"
		  cy="$100-100*sin([0;1;21]/20*3.14)"
		  r="$0;1;21"/>
	</g>	
</svg>
```

Output file (![svg](../master/example/sine.svg__out.svg))

![svg](https://pbs.twimg.com/media/CVD4H9XWoAELO6x.png)

See more at ![](https://github.com/step-artur87/sinlin/wiki).
