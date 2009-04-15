<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template mode="setArcs" name="setArcs" match="pathway">
        <xsl:for-each select="reaction">
            <xsl:call-template name="setArcGroups"/>
        </xsl:for-each>
    </xsl:template>


    <xsl:template mode="setArcGroups" name="setArcGroups" match="reaction">
        <xsl:for-each select="substrate">
            <xsl:call-template name="setSingleArc">
                <xsl:with-param name="arc_id"><xsl:value-of select="@name"/> to <xsl:value-of select="../@name"/></xsl:with-param>
                <xsl:with-param name="arc_source" select="@name"/>
                <xsl:with-param name="arc_target" select="../@name"/>
            </xsl:call-template>
        </xsl:for-each>

        <xsl:for-each select="product">
            <xsl:call-template name="setSingleArc">
                <xsl:with-param name="arc_id"><xsl:value-of select="../@name"/> to <xsl:value-of select="@name"/></xsl:with-param>
                <xsl:with-param name="arc_source" select="../@name"/>
                <xsl:with-param name="arc_target" select="@name"/>
            </xsl:call-template>
        </xsl:for-each>

    </xsl:template>

    <xsl:template mode="setSingleArc" name="setSingleArc" match="reaction">

        <xsl:param name="arc_id"/>
        <xsl:param name="arc_source"/>
        <xsl:param name="arc_target"/>
        <xsl:param name="arc_type" select="'normal'"/>

        <arc xmlns="http://www.pangea.net/type">
            <xsl:attribute name="id"><xsl:value-of select="$arc_id"/></xsl:attribute>
            <xsl:attribute name="source"><xsl:value-of select="$arc_source"/></xsl:attribute>
            <xsl:attribute name="target"><xsl:value-of select="$arc_target"/></xsl:attribute>
			<inscription>
                <value>1</value>
                <graphics/>
            </inscription>
            <type>
                <xsl:attribute name="value"><xsl:value-of select="$arc_type"/></xsl:attribute>
            </type>
        </arc>
    </xsl:template>

</xsl:stylesheet>