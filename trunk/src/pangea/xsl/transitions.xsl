<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template mode="setTransitions" name="setTransitions" match="pathway">
        <xsl:for-each select="reaction">
            <xsl:call-template name="setSingleTransition"/>
        </xsl:for-each>
    </xsl:template>


    <xsl:template mode="setSingleTransition" name="setSingleTransition" match="reaction">
        <transition xmlns="http://www.pangea.net/type">
            <xsl:attribute name="id"><xsl:value-of select="@name"/></xsl:attribute>
			<name>
				<value><xsl:value-of select="@name"/></value>
			</name>
			<rate>
				<value>1.0</value>
			</rate>
			<timed>
				<value>false</value>
			</timed>
		</transition>
    </xsl:template>

</xsl:stylesheet>