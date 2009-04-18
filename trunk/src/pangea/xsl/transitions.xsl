<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template mode="setTransitions" name="setTransitions" match="pathway">
        <xsl:for-each select="reaction">
            <xsl:call-template name="setSingleTransition"/>
            <xsl:if test="@type='reversible'">
                <xsl:call-template name="setReverseTransition"/>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>


    <xsl:template mode="setSingleTransition" name="setSingleTransition" match="reaction">
        <xsl:call-template name="setupTransition">
            <xsl:with-param name="name" select="@name"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template mode="setReverseTransition" name="setReverseTransition" match="reaction">
        <xsl:call-template name="setupTransition">
            <xsl:with-param name="name"><xsl:value-of select="@name"/>#rev</xsl:with-param>
        </xsl:call-template>
    </xsl:template>


    <xsl:template mode="setupTransition" name="setupTransition" match="reaction">
        <xsl:param name="name"/>
        <!--<transition xmlns="http://www.pangea.net/type">-->
        <transition>
            <xsl:attribute name="id"><xsl:value-of select="$name"/></xsl:attribute>
			<name>
				<value><xsl:value-of select="$name"/></value>
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