<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output indent="yes" method="xml" encoding="UTF-8"/>

    <xsl:include href="arcs.xsl"/>
    <xsl:include href="labels.xsl"/>
    <xsl:include href="places.xsl"/>
    <xsl:include href="transitions.xsl"/>

    <xsl:template match="/">
        <!--<pnml xmlns="http://www.pangea.net/type">-->
        <pnml>
            <net type="P/T net">

                <!--SETUP OF ID ATTRIBUTE-->
                <xsl:attribute name="id">
                    <xsl:choose>
                        <xsl:when test="contains(pathway/@name,':')">
                            <xsl:value-of select="substring-after(pathway/@name,':')"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="pathway/@name"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:attribute>
                <!--<pangea.xsl:attribute name="id"><pangea.xsl:value-of select="pathway/org"/><pangea.xsl:value-of select="pathway/number"/></pangea.xsl:attribute>-->

                <xsl:apply-templates mode="setLabels" select="pathway"/>
                <xsl:apply-templates mode="setPlaces" select="pathway"/>
                <xsl:apply-templates mode="setTransitions" select="pathway"/>
                <xsl:apply-templates mode="setArcs" select="pathway"/>

            </net>
        </pnml>
    </xsl:template>

</xsl:stylesheet>