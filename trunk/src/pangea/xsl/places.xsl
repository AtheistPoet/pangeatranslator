<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template mode="setPlaces" name="setPlaces" match="pathway">
        <xsl:for-each select="entry[@type='compound' or (@type='enzyme' and not(@map))]">
            <xsl:call-template name="setSinglePlace"/>
        </xsl:for-each>
    </xsl:template>


    <xsl:template mode="setSinglePlace" name="setSinglePlace" match="entry">
        <!--<place xmlns="http://www.pangea.net/type">-->
        <place>
            <xsl:attribute name="id"><xsl:value-of select="@name"/></xsl:attribute>
            <graphics>
                <position>
                    <xsl:attribute name="x"><xsl:value-of select="graphics/@x"/></xsl:attribute>
                    <xsl:attribute name="y"><xsl:value-of select="graphics/@y"/></xsl:attribute>
                </position>
            </graphics>
			<name>
				<value><xsl:value-of select="@name"/></value>
			</name>
		</place>
    </xsl:template>

</xsl:stylesheet>