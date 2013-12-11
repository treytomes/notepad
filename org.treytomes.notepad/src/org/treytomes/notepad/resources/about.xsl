<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
	<html>
		<xsl:apply-templates select="about" />
	</html>
</xsl:template>

<xsl:template match="about">
	<head>
		<title>About <xsl:value-of select="application" /></title>
	</head>
	
	<body>
		<div><xsl:value-of select="author" /></div>
		<div>Version <xsl:value-of select="version" /></div>
		<div>
			Copyright &#169;
			<script>document.write(new Date().getFullYear())</script>
			<xsl:value-of select="author" />.  All rights reserved.
		</div>
	</body>
</xsl:template>

</xsl:stylesheet>