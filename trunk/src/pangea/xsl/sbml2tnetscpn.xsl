<!--
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://pdv.cs.tu-berlin.de/TimeNET/schema/SCPN"  xmlns:m="http://www.w3.org/1998/Math/MathML">
-->

<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns="http://pdv.cs.tu-berlin.de/TimeNET/schema/SCPN"  xmlns:m="http://www.w3.org/1998/Math/MathML">

<xsl:param name="scaleval"/>
<xsl:param name="dist"/>

<xsl:output indent="yes" method="xml" encoding="UTF-8" media-type="text/xml" version="1.0"/>

<xsl:template match="/">

	<net id="0" netclass="SCPN" xmlns="http://pdv.cs.tu-berlin.de/TimeNET/schema/SCPN" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://pdv.cs.tu-berlin.de/TimeNET/schema/SCPN etc/schemas/SCPN.xsd">
	
	<xsl:apply-templates mode="piazze" select="/sbml/model/listOfSpecies"/>
	<xsl:apply-templates mode="transizioni" select="/sbml/model/listOfReactions"/>
	<xsl:apply-templates mode="archi" select="/sbml/model/listOfReactions"/>
	<xsl:apply-templates mode="definizioni" select="/sbml/model/listOfParameters"/>
	<xsl:apply-templates mode="compartments" select="/sbml/model/listOfCompartments"/>
	
	</net>
</xsl:template>

<xsl:template name="piazze" mode="piazze" match="listOfSpecies">
	<xsl:for-each select="species">
        <!--<xsl:variable name="im" select="round(number(@initialAmount)*1000)"/>-->
        <xsl:variable name="im"><xsl:apply-templates mode="initialMarking" select="."/></xsl:variable>
		<place capacity="0" queue="Random" tokentype="int" type="node" watch="false">
			<xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
			<xsl:attribute name="initialMarking" select="$im"/>
			<graphics orientation="0" x="116" y="258"/>
			<label type="text">
				<xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
				<xsl:attribute name="text"><xsl:value-of select="@id"/></xsl:attribute>
				<graphics x="-10" y="-40"/>
			</label>
		</place>
	</xsl:for-each>
</xsl:template>

<!-- BISOGNA IMPOSTARE LE REAZIONI REVERSIBILI -->
<xsl:template name="transizioni" mode="transizioni" match="listOfReactions">
	<xsl:for-each select="reaction">
		<timedTransition serverType="ExclusiveServer" specType="Automatic" takeFirst="false" type="node" watch="false">
			<xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
			<xsl:attribute name="timeFunction"><xsl:apply-templates mode="kinetic_law" select="kineticLaw"/></xsl:attribute>
			<graphics orientation="0" x="374" y="238"/>
			<label type="text">
				<xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
				<xsl:attribute name="text"><xsl:value-of select="@id"/></xsl:attribute>
				<graphics x="-10" y="-40"/>
			</label>
		</timedTransition>
		
	</xsl:for-each>
</xsl:template>

<xsl:template name="archi" mode="archi" match="listOfReactions">
	<xsl:for-each select="reaction">
		
		<xsl:apply-templates mode="archiInput" select="listOfReactants">
			<xsl:with-param name="target" select="@id"/>
		</xsl:apply-templates>
		
		<xsl:apply-templates mode="archiOutput" select="listOfProducts">
			<xsl:with-param name="source" select="@id"/>
		</xsl:apply-templates>
		
	</xsl:for-each>
</xsl:template>


<xsl:template name="archiInput" mode="archiInput" match="listOfReactants">
	<xsl:param name="target"/>
	<xsl:for-each select="speciesReference">
		<arc type="connector">
			<xsl:attribute name="id"><xsl:value-of select="@species"/>2<xsl:value-of select="$target"/></xsl:attribute>
			<xsl:attribute name="fromNode"><xsl:value-of select="@species"/></xsl:attribute>
			<xsl:attribute name="toNode"><xsl:value-of select="$target"/></xsl:attribute>
			<inscription type="inscriptionText">
				<xsl:attribute name="id"><xsl:value-of select="@species"/>2<xsl:value-of select="$target"/></xsl:attribute>
				<xsl:attribute name="text"><xsl:if test="@stoichiometry"><xsl:value-of select="@stoichiometry"/>'</xsl:if><xsl:value-of select="@species"/></xsl:attribute>
				<graphics x="0" y="0"/>
			</inscription>
		</arc>
	</xsl:for-each>
</xsl:template>


<xsl:template name="archiOutput" mode="archiOutput" match="listOfProducts">
	<xsl:param name="source"/>
	<xsl:for-each select="speciesReference">
		<arc type="connector">
			<xsl:attribute name="id"><xsl:value-of select="$source"/>2<xsl:value-of select="@species"/></xsl:attribute>
			<xsl:attribute name="toNode"><xsl:value-of select="@species"/></xsl:attribute>
			<xsl:attribute name="fromNode"><xsl:value-of select="$source"/></xsl:attribute>
			<inscription type="inscriptionText">
				<xsl:attribute name="id"><xsl:value-of select="$source"/>2<xsl:value-of select="@species"/></xsl:attribute>
				<xsl:attribute name="text"><xsl:if test="@stoichiometry"><xsl:value-of select="@stoichiometry"/>'</xsl:if>new(0)</xsl:attribute>
				<graphics x="0" y="0"/>
			</inscription>
		</arc>
	</xsl:for-each>
</xsl:template>



<xsl:template name="definizioni" mode="definizioni" match="listOfParameters">
	<xsl:for-each select="parameter">
		<definition type="text">
			<xsl:attribute name="expression"><xsl:value-of select="@value"/></xsl:attribute>
			<xsl:attribute name="result"><xsl:value-of select="@id"/></xsl:attribute>
			<xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
			<graphics x="0" y="0"/>
		</definition>
	</xsl:for-each>
</xsl:template>


<xsl:template name="compartments" mode="compartments" match="listOfCompartments">
	<xsl:for-each select="compartment">
		<definition type="text">
			<xsl:attribute name="expression"><xsl:value-of select="@size"/></xsl:attribute>
			<xsl:attribute name="result"><xsl:value-of select="@id"/></xsl:attribute>
			<xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
			<graphics x="0" y="0"/>
		</definition>
	</xsl:for-each>
</xsl:template>


<xsl:template name="kinetic_law" mode="kinetic_law" match="kineticLaw">
	<xsl:value-of select="$dist"/><xsl:apply-templates mode="kinetic_law_passo" select="m:math/m:apply"/>
</xsl:template>

<xsl:template name="kinetic_law_passo" mode="kinetic_law_passo" match="m:*">
	<xsl:choose>
		<!-- OPERAZIONI -->
		<xsl:when test="name()='times'"><xsl:apply-templates mode="kinetic_law_operazione" select=".."><xsl:with-param name="operatore">*</xsl:with-param></xsl:apply-templates></xsl:when>
		<xsl:when test="name()='minus'"><xsl:apply-templates mode="kinetic_law_operazione" select=".."><xsl:with-param name="operatore">-</xsl:with-param></xsl:apply-templates></xsl:when>
		<xsl:when test="name()='plus'"><xsl:apply-templates mode="kinetic_law_operazione" select=".."><xsl:with-param name="operatore">+</xsl:with-param></xsl:apply-templates></xsl:when>
		<xsl:when test="name()='divide'"><xsl:apply-templates mode="kinetic_law_operazione" select=".."><xsl:with-param name="operatore">/</xsl:with-param></xsl:apply-templates></xsl:when>
		<xsl:when test="name()='power'"><xsl:apply-templates mode="kinetic_law_operazione" select=".."><xsl:with-param name="operatore">^</xsl:with-param></xsl:apply-templates></xsl:when>
		
		<!-- OPERANDI -->
		<xsl:when test="name()='cn'"><xsl:value-of select="text()"/></xsl:when>
		<xsl:when test="name()='ci'"><xsl:apply-templates mode="kinetic_law_species" select="."/></xsl:when>
		
		<xsl:when test="name()='apply'">(<xsl:apply-templates mode="kinetic_law_passo" select="*[1]"/>)</xsl:when>
		
		<xsl:otherwise>PROBLEMA</xsl:otherwise>
	</xsl:choose>
</xsl:template>


<xsl:template name="kinetic_law_operazione" mode="kinetic_law_operazione" match="m:*">
	<xsl:param name="operatore"/>
	<xsl:for-each select="*">
		<xsl:choose>
			<xsl:when test="position()=1"></xsl:when>
			<xsl:when test="position()=last()"><xsl:apply-templates mode="kinetic_law_passo" select="."/></xsl:when>
			<xsl:otherwise><xsl:apply-templates mode="kinetic_law_passo" select="."/><xsl:value-of select="$operatore"/></xsl:otherwise>
		</xsl:choose>
	</xsl:for-each>
</xsl:template>

<xsl:template name="kinetic_law_species" mode="kinetic_law_species" match="m:ci">
	<xsl:variable name="text"><xsl:apply-templates mode="trim" select="."/></xsl:variable>
	<xsl:choose>
		<xsl:when test="count(/sbml/model/listOfSpecies/species[@id = $text])>0"><xsl:value-of select="concat('#',$text)"/></xsl:when>
		<xsl:otherwise><xsl:value-of select="$text"/></xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template name="trim" mode="trim" match="*">
	<xsl:variable name="pattern" select="'^[ ]+|[ ]+$'"/>
	<xsl:variable name="output" select="''"/>
	<xsl:value-of select="replace(text(),$pattern,$output)"/>
</xsl:template>

<xsl:template name="initialMarking" mode="initialMarking" match="species">
	<xsl:variable name="scaledim" select="round(number(@initialAmount)*number($scaleval))"/>
	<xsl:apply-templates mode="initialMarking_passo" select="."><xsl:with-param name="iterator" select="$scaledim"/></xsl:apply-templates>
</xsl:template>

<xsl:template name="initialMarking_passo" mode="initialMarking_passo" match="*">
	<xsl:param name="iterator"/>
	<xsl:choose>
		<xsl:when test="$iterator>1">0, <xsl:apply-templates mode="initialMarking_passo" select="."><xsl:with-param name="iterator" select="$iterator - 1"/></xsl:apply-templates></xsl:when>
		<xsl:when test="$iterator=1">0</xsl:when>
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>
