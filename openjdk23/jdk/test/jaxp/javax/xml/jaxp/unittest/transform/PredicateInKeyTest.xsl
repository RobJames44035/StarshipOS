<?xml version="1.0"?>
<!--
  ~ StarshipOS Copyright (c) 2014-2025. R.A. James
  -->
<xsl:stylesheet
   version="1.0"
   xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
>

   <xsl:output method="text"/>
   
   <xsl:key name="key" match="t[1]" use="0"/>
   <xsl:key name="key" match="t[2]" use="1"/>
   <xsl:key name="key" match="t[following-sibling::t[1] = 3]" use="2"/>
   <xsl:key name="key" match="t[preceding-sibling::t[. = 2]]" use="3"/>
   
   <xsl:template match="/">
      <xsl:copy-of select="key('key', 0)/text()"/> <!-- 0 -->
      <xsl:text>|</xsl:text>
      <xsl:copy-of select="key('key', 1)/text()"/> <!-- 1 -->
      <xsl:text>|</xsl:text>
      <xsl:copy-of select="key('key', 2)/text()"/> <!-- 2 -->
      <xsl:text>|</xsl:text>
      <xsl:copy-of select="key('key', 3)/text()"/> <!-- 3 -->
   </xsl:template>

</xsl:stylesheet>

