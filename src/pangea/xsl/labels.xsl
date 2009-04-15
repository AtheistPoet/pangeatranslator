<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template mode="setLabels" name="setLabels" match="pathway">
        <xsl:call-template name="setNameLable"/>
    </xsl:template>

    <xsl:template mode="setNameLable" name="setNameLable" match="pathway">
        <labels xmlns="http://www.pangea.net/type" x="20" y="20" width="105" height="25" border="true">
			<text><xsl:value-of select="@title"/></text>
		</labels>
    </xsl:template>

</xsl:stylesheet>