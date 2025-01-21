<?xml version="1.0" encoding="UTF-8"?>
  <!--
  ~ StarshipOS Copyright (c) 2025. R.A. James
  -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="xml" version="1.0" omit-xml-declaration="no" encoding="UTF-8" indent="yes" xml:space="preserve" />
  <!-- <xsl:output method="html" doctype-system="http://www.w3.org/TR/html4/strict.dtd" doctype-public="-//W3C//DTD HTML 
    4.01//EN" version="4.0" encoding="UTF-8" indent="yes" xml:lang="$lang" omit-xml-declaration="no"/> -->
  <xsl:param name="lang" />
  <xsl:template match="alphabet">
    <root>
      <p>lang: <xsl:value-of select="$lang" /></p>
      <ul>
        <xsl:apply-templates select="character">
          <xsl:sort select="." lang="{$lang}" order="ascending" />
        </xsl:apply-templates>
      </ul>
    </root>
  </xsl:template>
  <xsl:template match="character">
    <li>
      <xsl:value-of select="text()" />
    </li>
  </xsl:template>
</xsl:stylesheet>