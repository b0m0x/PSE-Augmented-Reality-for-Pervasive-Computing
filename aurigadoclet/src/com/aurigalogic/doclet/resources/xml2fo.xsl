<?xml version="1.0"?>
<!-- $Id: xml2fo.xsl,v 1.1.1.1 2003/12/24 12:38:12 kshaikh Exp $ -->

<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:fo="http://www.w3.org/1999/XSL/Format"
  xmlns:fox="http://xml.apache.org/fop/extensions">
	
<xsl:param name="generate-toc">true</xsl:param>
<xsl:param name="generate-navigation">true</xsl:param>
<xsl:param name="generate-links">true</xsl:param>
<xsl:param name="generate-index">true</xsl:param>
<xsl:param name="header-height">50</xsl:param>
<xsl:param name="footer-height">20</xsl:param>
<xsl:param name="css-file" />
<xsl:param name="left-margin">30</xsl:param>
<xsl:param name="right-margin">30</xsl:param>
<xsl:param name="top-margin">10</xsl:param>
<xsl:param name="bottom-margin">10</xsl:param>

<xsl:variable name="nbsp"><xsl:text> </xsl:text></xsl:variable>
<xsl:variable name="tab-size">4</xsl:variable>

<!-- key for the class elements -->
<xsl:key name="class-key" match="class" use="@qualified-name" />

<!-- key for the sub class elements -->
<xsl:key name="sub-class-key" match="class" use="@super-class" />

<!-- key for the index -->
<xsl:key name="index-key" match="class|method|field|constructor" use="substring(@name, 1, 1)" />

<xsl:variable name="method-pattern">.abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ(0123456789_[]</xsl:variable>

<xsl:variable name="method-replacement"><xsl:text>                                                                   </xsl:text></xsl:variable>

<xsl:variable name="lower-alphas">abcdefghijklmnopqrstuvwxyz</xsl:variable>

<xsl:variable name="upper-alphas">ABCDEFGHIJKLMNOPQRSTUVWXYZ</xsl:variable>

<!-- 
*************************************************************************
Template for the document element. This template matches other templates 
from here
*************************************************************************
-->
<xsl:template match="javadoc">
<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
      
	<!-- layout -->
	<fo:layout-master-set>
      
		<!-- check if toc page is reqd -->
		<xsl:if test="$generate-toc = 'true'">
			<!-- page master for the TOC page -->
			<fo:simple-page-master 
				master-name="toc"
				margin-left="{$left-margin}pt"
				margin-right="{$right-margin}pt"
				margin-top="{$top-margin}pt"
				margin-bottom="{$bottom-margin}pt"
			>
				<!-- layout for the body part -->
				<fo:region-body margin-top="{$header-height}pt" 
					margin-bottom="{$footer-height}pt" />

				<!-- layout for the header part -->
				<fo:region-before extent="{$header-height}pt" />
			
				<!-- layout for the footer part -->
				<fo:region-after extent="{$footer-height}pt" />
			</fo:simple-page-master>
		</xsl:if>
	
		<!-- check if cover page is non blank -->
		<xsl:if test="not(cover = '')">
			<!-- page master for the cover page -->
			<fo:simple-page-master master-name="cover" margin-top="0pt" margin-bottom="0pt">
				<!-- layout for the body part -->
				<fo:region-body margin-left="0pt" margin-right="0pt" margin-top="0pt" 
					margin-bottom="0pt" />
				<fo:region-after extent="0pt" />
				<fo:region-before extent="0pt" />
			</fo:simple-page-master>
		</xsl:if>
	
		<!-- page master for the main part -->
		<fo:simple-page-master 
				master-name="body"
				margin-left="{$left-margin}pt" 
				margin-right="{$right-margin}pt" 
				margin-top="{$top-margin}pt" 
				margin-bottom="{$bottom-margin}pt"
				>
			<!-- layout for the body part -->
			<fo:region-body margin-top="{$header-height}pt" margin-bottom="{$footer-height}pt" />

			<!-- layout for the header part -->
			<fo:region-before extent="{$header-height}pt" margin-top="0pt" margin-bottom="0pt" />

			<!-- layout for the footer part -->
			<fo:region-after extent="{$footer-height}pt" margin-top="0pt" margin-bottom="0pt" />
		</fo:simple-page-master>

		<xsl:if test="$generate-index = 'true'">
			<!-- page master for the index region -->
			<fo:simple-page-master 
				master-name="index"
				margin-left="{$left-margin}pt"
				margin-right="{$right-margin}pt"
				margin-top="{$top-margin}pt"
				margin-bottom="{$bottom-margin}pt"
				>
				<!-- layout for the body part -->
				<fo:region-body column-count="2" />
			</fo:simple-page-master>
		</xsl:if>
	</fo:layout-master-set>

	<!-- call the cover page -->
	<xsl:if test="not(cover = '')">
		<xsl:apply-templates select="cover" />
	</xsl:if>

	<!-- generate toc, if reqd -->
	<xsl:if test="$generate-toc = 'true'">
		<xsl:apply-templates select="." mode="toc" />
	</xsl:if>

	<xsl:if test="$generate-navigation = 'true'">
		<xsl:apply-templates select="package|class" mode="outline" />
		<xsl:if test="$generate-index = 'true'">
			<fox:outline internal-destination="index">
				<fox:label>Index</fox:label>
			</fox:outline>
		</xsl:if>
	</xsl:if>

	<fo:page-sequence master-reference="body">

		<!-- apply the header -->
		<xsl:apply-templates select="header" />
	
		<!-- apply the footer -->
		<xsl:apply-templates select="footer" />
	 
		<!-- apply the body -->
		<fo:flow flow-name="xsl-region-body">
			<fo:block font-family="Helvetica" font-size="11pt">
				<xsl:call-template name="get-css-for-element">
					<xsl:with-param name="element">body</xsl:with-param>
				</xsl:call-template>
				<xsl:apply-templates select="package|class" />
			</fo:block>
   		</fo:flow>
     
	</fo:page-sequence>

	<xsl:if test="$generate-index = 'true'">
		<xsl:apply-templates select="." mode="index" />
	</xsl:if>

</fo:root>
</xsl:template>

<!-- 
*************************************************************************
This template generates the package outline
*************************************************************************
-->
<xsl:template match="package" mode="outline">
<fox:outline internal-destination="{@name}">
	<fox:label><xsl:value-of select="@name" /></fox:label>
	<xsl:apply-templates select="class" mode="outline" />
</fox:outline>
</xsl:template>

<!-- 
*************************************************************************
This template generates the class outline
*************************************************************************
-->
<xsl:template match="class" mode="outline">
<fox:outline internal-destination="{@qualified-name}">
	<fox:label><xsl:value-of select="@name" /></fox:label>
	<xsl:if test="field">
		<fox:outline internal-destination="{concat(@qualified-name, '_fields')}">
		<fox:label>Fields</fox:label>
		<xsl:apply-templates select="field" mode="outline" />
		</fox:outline>
	</xsl:if>
	<xsl:if test="constructor">
		<fox:outline internal-destination="{concat(@qualified-name, '_constructors')}">
		<fox:label>Constructors</fox:label>
		<xsl:apply-templates select="constructor" mode="outline" />
		</fox:outline>
	</xsl:if>
	<xsl:if test="method">
		<fox:outline internal-destination="{concat(@qualified-name, '_methods')}">
		<fox:label>Methods</fox:label>
		<xsl:apply-templates select="method" mode="outline" />
		</fox:outline>
	</xsl:if>
</fox:outline>
</xsl:template>

<!-- 
*************************************************************************
This template generates the method outline
*************************************************************************
-->
<xsl:template match="method|constructor" mode="outline">
<fox:outline internal-destination="{concat(parent::class/@qualified-name, @name, @signature)}">
	<fox:label><xsl:value-of select="concat(@name, @signature)" /></fox:label>
</fox:outline>
</xsl:template>

<!-- 
*************************************************************************
This template generates the field outline
*************************************************************************
-->
<xsl:template match="field" mode="outline">
<fox:outline internal-destination="{concat(parent::class/@qualified-name, '_field_', @name)}">
	<fox:label><xsl:value-of select="@name" /></fox:label>
</fox:outline>
</xsl:template>

<!-- 
*************************************************************************
This template generates the method outline
*************************************************************************
-->
<xsl:template match="method" mode="outline">
<fox:outline internal-destination="{concat(parent::class/@qualified-name, @name, @signature)}">
	<fox:label><xsl:value-of select="concat(@name, @signature)" /></fox:label>
</fox:outline>
</xsl:template>


<!-- 
*************************************************************************
Template for the cover page
*************************************************************************
-->
<xsl:template match="cover">
<fo:page-sequence master-reference="cover">
<fo:flow flow-name="xsl-region-body"><fo:block>
<xsl:apply-templates />
</fo:block></fo:flow>
</fo:page-sequence>
</xsl:template>

<!-- 
*************************************************************************
Template for the toc page
*************************************************************************
-->
<xsl:template match="javadoc" mode="toc">
<fo:page-sequence master-reference="toc">
<fo:flow flow-name="xsl-region-body">
<fo:block text-align="start" font-weight="bold" font-size="16pt">Table Of Content</fo:block>
<xsl:call-template name="horizontal-rule" />
<fo:block>
<xsl:apply-templates select="package|class" mode="toc-link" />
<xsl:if test="$generate-index = 'true'">
<fo:block space-before="2pt" space-after="2pt" text-align-last="justify">
<xsl:choose>
<xsl:when test="$generate-links = 'true'">
	<xsl:call-template name="internal-link">
		<xsl:with-param name="label">Index</xsl:with-param>
		<xsl:with-param name="destination">index</xsl:with-param>
	</xsl:call-template>
</xsl:when>
<xsl:otherwise>
<fo:inline keep-with-next.within-line="always">Index</fo:inline>
</xsl:otherwise>
</xsl:choose>
<fo:inline keep-together.within-line="always"><fo:leader leader-pattern="dots" leader-pattern-width="3pt" leader-alignment="reference-area" keep-with-next.within-line="always" /><fo:page-number-citation ref-id="index" /></fo:inline>
</fo:block>
</xsl:if>
</fo:block>
</fo:flow>
</fo:page-sequence>
</xsl:template>


<!-- 
*************************************************************************
This template renders the toc link
*************************************************************************
-->
<xsl:template match="package|class" mode="toc-link">
<xsl:variable name="section-level">
<xsl:choose>
<xsl:when test="parent::package">2</xsl:when>
<xsl:otherwise>1</xsl:otherwise>
</xsl:choose>
</xsl:variable>

<xsl:variable name="target">
<xsl:choose>
<xsl:when test="local-name() = 'class'"><xsl:value-of select="@qualified-name" /></xsl:when>
<xsl:otherwise><xsl:value-of select="@name" /></xsl:otherwise>
</xsl:choose>
</xsl:variable>

<fo:block space-before="2pt" space-after="2pt" text-align-last="justify">
<xsl:attribute name="margin-left"><xsl:value-of select="($section-level - 1) div 2"/>in</xsl:attribute>
<xsl:choose>
<xsl:when test="$generate-links = 'true'">
	<xsl:call-template name="internal-link">
		<xsl:with-param name="label" select="@name" />
		<xsl:with-param name="destination" select="$target" />
	</xsl:call-template>
</xsl:when>
<xsl:otherwise>
<fo:inline keep-with-next.within-line="always"><xsl:value-of select="@name" /></fo:inline>
</xsl:otherwise>
</xsl:choose>

<fo:inline keep-with-previous.within-line="always">
	<fo:leader leader-pattern="dots" leader-pattern-width="3pt" 
		leader-alignment="reference-area" keep-together.within-line="always"/>
	<fo:page-number-citation ref-id="{$target}" />
</fo:inline>
</fo:block>
<xsl:apply-templates select="class" mode="toc-link" />
</xsl:template>

<!-- 
*************************************************************************
This template renders the page header
*************************************************************************
-->
<xsl:template match="header">
<fo:static-content flow-name="xsl-region-before">
<fo:block>
	<xsl:call-template name="get-css-for-class">
		<xsl:with-param name="class">page-header</xsl:with-param>
	</xsl:call-template>
	<xsl:apply-templates />
</fo:block>
</fo:static-content>
</xsl:template>

<!-- 
*************************************************************************
This template renders the page footer
*************************************************************************
-->
<xsl:template match="footer">
<fo:static-content flow-name="xsl-region-after">
<fo:block>
	<xsl:call-template name="get-css-for-class">
		<xsl:with-param name="class">page-footer</xsl:with-param>
	</xsl:call-template>
	<xsl:apply-templates />
</fo:block>
</fo:static-content>
</xsl:template>

<!-- 
*************************************************************************
This template renders the package info
*************************************************************************
-->
<xsl:template match="package">
<fo:block id="{@name}" font-weight="bold" font-size="24pt" space-after="10pt">
	<xsl:call-template name="get-css-for-class">
		<xsl:with-param name="class">package-title</xsl:with-param>
	</xsl:call-template>
Package <xsl:value-of select="@name" />
</fo:block>

<!-- interfaces if any -->
<xsl:if test="class[@interface = 'true']">
<fo:block width="100%" background-color="silver" font-weight="bold" font-size="24pt" space-before="5pt" space-after="5pt">
<xsl:call-template name="get-css-for-class">
	<xsl:with-param name="class">header</xsl:with-param>
</xsl:call-template>
Interface Summary
</fo:block>
<xsl:for-each select="class[@interface = 'true']">
	<xsl:apply-templates select="." mode="package-summary" />
</xsl:for-each>
</xsl:if>

<!-- normal classes if any -->
<xsl:if test="class[@interface = 'false']">
<fo:block width="100%" background-color="silver" font-weight="bold" font-size="24pt" space-before="5pt" space-after="5pt">
<xsl:call-template name="get-css-for-class">
	<xsl:with-param name="class">header</xsl:with-param>
</xsl:call-template>
Class Summary</fo:block>
<xsl:for-each select="class[@interface = 'false']">
	<xsl:apply-templates select="." mode="package-summary" />
</xsl:for-each>
</xsl:if>

<!-- render the classes/interfaces -->
<xsl:apply-templates select="class" />
<xsl:if test="position() != last()">
	<fo:block break-before="page" keep-with-previous="auto" />	
</xsl:if>
</xsl:template>

<!-- 
*************************************************************************
This template renders the class info
*************************************************************************
-->
<xsl:template match="class">
<xsl:call-template name="horizontal-rule">
	<xsl:with-param name="size">5</xsl:with-param>
	<xsl:with-param name="color">gray</xsl:with-param>
	<xsl:with-param name="css-class">class-separator</xsl:with-param>
</xsl:call-template>
<!-- package name -->
<fo:table table-layout="fixed" width="100%">
<fo:table-column column-width="proportional-column-width(1)"/>
<fo:table-body>
<fo:table-row>
<fo:table-cell>
<fo:block id="{@qualified-name}" space-before="10pt" font-weight="bold">
	<xsl:value-of select="parent::package/@name" />
</fo:block>
</fo:table-cell>
</fo:table-row>
<fo:table-row keep-with-previous="always">
<fo:table-cell>
<!-- class/interface header -->
<fo:block font-weight="bold" font-size="24pt" space-before="10pt" space-after="10pt">
<xsl:call-template name="get-css-for-class">
	<xsl:with-param name="class">class-title</xsl:with-param>
</xsl:call-template>
<xsl:if test="@interface = 'false'">Class </xsl:if>
<xsl:if test="@interface = 'true'">Interface </xsl:if>
<xsl:value-of select="@name" />
</fo:block>
</fo:table-cell>
</fo:table-row>
<fo:table-row keep-with-previous="always">
<fo:table-cell>
<xsl:apply-templates select="." mode="class-hierarchy" />
<!-- All Implemented interfaces -->
<xsl:if test="interface">
<fo:block font-weight="bold">All Implemented Interfaces:</fo:block>
<fo:block start-indent="0.5in">
<xsl:for-each select="interface">
	<xsl:call-template name="class-link">
		<xsl:with-param name="name" select="@qualified-name" />
	</xsl:call-template>
	<xsl:if test="position() != last()"><xsl:value-of select="concat(',', ' ')" /></xsl:if>
</xsl:for-each>
</fo:block>
</xsl:if>
<!-- All known subclasses -->
<xsl:variable name="sub-classes" select="key('sub-class-key', @qualified-name)" />
<xsl:if test="$sub-classes">
<fo:block space-before="5pt" font-weight="bold">Direct Known Subclasses:</fo:block>
<fo:block start-indent="0.5in">
<xsl:for-each select="$sub-classes">
	<xsl:call-template name="class-link">
		<xsl:with-param name="name" select="@qualified-name" />
	</xsl:call-template>
	<xsl:if test="position() != last()"><xsl:value-of select="concat(',', ' ')" /></xsl:if>
</xsl:for-each>
</fo:block>
</xsl:if>
<xsl:call-template name="horizontal-rule">
	<xsl:with-param name="color">silver</xsl:with-param>
</xsl:call-template>
</fo:table-cell>
</fo:table-row>
</fo:table-body>
</fo:table>
<xsl:if test="$generate-links = 'true'">
<fo:block>
<!-- link to fields -->
<xsl:if test="field">
&lt;
<xsl:call-template name="internal-link">
	<xsl:with-param name="destination" select="concat(@qualified-name, '_fields')" />
	<xsl:with-param name="label">Fields</xsl:with-param>
</xsl:call-template>
&gt;
</xsl:if>
<!-- link to constructors -->
<xsl:if test="constructor">
&lt;
<xsl:call-template name="internal-link">
	<xsl:with-param name="destination" select="concat(@qualified-name, '_constructors')" />
	<xsl:with-param name="label">Constructors</xsl:with-param>
</xsl:call-template>
&gt;
</xsl:if>
<!-- link to methods -->
<xsl:if test="method">
&lt;
<xsl:call-template name="internal-link">
	<xsl:with-param name="destination" select="concat(@qualified-name, '_methods')" />
	<xsl:with-param name="label">Methods</xsl:with-param>
</xsl:call-template>
&gt;
</xsl:if>
</fo:block>
<xsl:call-template name="horizontal-rule">
	<xsl:with-param name="color">silver</xsl:with-param>
</xsl:call-template>
</xsl:if>
<xsl:variable name="type">
<xsl:choose>
	<xsl:when test="@interface = 'true'"></xsl:when>
	<xsl:otherwise>class</xsl:otherwise>
</xsl:choose>
</xsl:variable>
<xsl:value-of select="concat(@modifiers, ' ', $type, ' ')" />
<fo:inline font-weight="bold"><xsl:value-of select="@name" /></fo:inline>
<!-- super class if any -->
<xsl:if test="@super-class">
	<fo:block>extends 
	<xsl:call-template name="class-link">
		<xsl:with-param name="name" select="@super-class" />
	</xsl:call-template>
	</fo:block>
</xsl:if>
<!-- interfaces if any -->
<xsl:if test="interface[@direct = 'true']">
	<fo:block>implements 
	<xsl:for-each select="interface[@direct = 'true']">
		<xsl:call-template name="class-link">
			<xsl:with-param name="name" select="@qualified-name" />
		</xsl:call-template>
		<xsl:if test="position() != last()"><xsl:value-of select="concat(',', ' ')" /></xsl:if>
	</xsl:for-each>
	</fo:block>
</xsl:if>
<!-- class/interface comments -->
<fo:block space-before="10pt" space-after="10pt">
	<xsl:apply-templates select="comment" />
</fo:block>
<!-- tags -->
<xsl:apply-templates select="tag" mode="class" />
<!-- constructors, fields, methods -->
<xsl:apply-templates select="." mode="fields" />
<xsl:apply-templates select="." mode="constructors" />
<xsl:apply-templates select="." mode="methods" />
</xsl:template>

<!--
*************************************************************************
This template renders the clas hierarchy
*************************************************************************
-->
<xsl:template match="class" mode="class-hierarchy">
<xsl:if test="super-class">
<fo:block space-after="10pt" wrap-option="no-wrap" white-space-collapse="false" font-family="monospace">
<xsl:for-each select="super-class">
<xsl:call-template name="display-class-node">
	<xsl:with-param name="class" select="@name" />
	<xsl:with-param name="level" select="position() - 1" />
</xsl:call-template><xsl:text>
</xsl:text>
</xsl:for-each>
<xsl:variable name="spaces">
<xsl:call-template name="display-n-spaces">
	<xsl:with-param name="n" select="count(super-class) * $tab-size" />
</xsl:call-template>
</xsl:variable>
<xsl:value-of select="concat($spaces,'|')" /><xsl:text>
</xsl:text><xsl:value-of select="concat($spaces,'+--', @qualified-name)" />
</fo:block>
</xsl:if>
</xsl:template>

<!-- 
*************************************************************************
This template renders the constructor details
*************************************************************************
-->
<xsl:template match="class" mode="constructors">
<xsl:if test="constructor">
<fo:block background-color="silver" font-weight="bold" font-size="24pt" space-before="10pt" 
	space-after="10pt" id="{concat(@qualified-name, '_constructors')}">
<xsl:call-template name="get-css-for-class">
	<xsl:with-param name="class">header</xsl:with-param>
</xsl:call-template>
Constructors</fo:block>
<!-- iterate -->
<xsl:for-each select="constructor">
	<xsl:apply-templates select="." mode="details" />
	<!-- hr -->
	<xsl:if test="not(position() = last())">
		<xsl:call-template name="horizontal-rule">
			<xsl:with-param name="css-class">class-item-separator</xsl:with-param>
		</xsl:call-template>
	</xsl:if>
</xsl:for-each>
</xsl:if>
</xsl:template>

<!-- 
*************************************************************************
This template renders the field detail
*************************************************************************
-->
<xsl:template match="class" mode="fields">
<xsl:if test="field">
<fo:block background-color="silver" font-weight="bold" font-size="24pt" space-before="10pt" 
	space-after="10pt" id="{concat(@qualified-name, '_fields')}">
<xsl:call-template name="get-css-for-class">
	<xsl:with-param name="class">header</xsl:with-param>
</xsl:call-template>
Fields</fo:block>
<!-- iterate -->
<xsl:for-each select="field">
<!-- field title -->
<fo:block id="{concat(parent::class/@qualified-name, '_field_', @name)}" font-weight="bold" font-size="20pt" space-before="10pt" space-after="10pt">
<xsl:call-template name="get-css-for-class">
	<xsl:with-param name="class">field-title</xsl:with-param>
</xsl:call-template>
<xsl:value-of select="@name" /></fo:block>
<!-- field name, type -->
<fo:block font-family="monospace"><xsl:value-of select="concat(@modifiers, ' ')" />
<xsl:call-template name="class-link"><xsl:with-param name="name" select="@type" /></xsl:call-template>
<fo:inline font-weight="bold"><xsl:value-of select="concat(' ', @name)" /></fo:inline>
</fo:block>
<!-- field comments -->
<fo:block start-indent="0.5in" space-after="10pt">
	<xsl:apply-templates select="comment" />
</fo:block>
<!-- hr -->
<xsl:if test="not(position() = last())">
	<xsl:call-template name="horizontal-rule">
		<xsl:with-param name="css-class">class-item-separator</xsl:with-param>
	</xsl:call-template>
</xsl:if>
</xsl:for-each>
</xsl:if>
</xsl:template>

<!-- 
*************************************************************************
This template renders the methods
*************************************************************************
-->
<xsl:template match="class" mode="methods">
<xsl:if test="method">
<fo:block background-color="silver" font-weight="bold" font-size="24pt" space-before="10pt" 
	space-after="10pt" id="{concat(@qualified-name, '_methods')}">
<xsl:call-template name="get-css-for-class">
	<xsl:with-param name="class">header</xsl:with-param>
</xsl:call-template>
Methods</fo:block>
<!-- iterate -->
<xsl:for-each select="method">
	<xsl:apply-templates select="." mode="details" />
	<!-- hr -->
	<xsl:if test="not(position() = last())">
		<xsl:call-template name="horizontal-rule">
			<xsl:with-param name="css-class">class-item-separator</xsl:with-param>
		</xsl:call-template>
	</xsl:if>
</xsl:for-each>
</xsl:if>
</xsl:template>

<!-- 
*************************************************************************
This template renders a method or constructor
*************************************************************************
-->
<xsl:template match="method|constructor" mode="details">
<!-- method title -->
<fo:table table-layout="fixed" width="100%">
<fo:table-column column-width="proportional-column-width(1)"/>
<fo:table-body>
<fo:table-row>
<fo:table-cell>
<fo:block id="{concat(parent::class/@qualified-name, @name, @signature)}" font-weight="bold" 
	font-size="20pt" space-before="10pt" space-after="10pt">
<xsl:call-template name="get-css-for-class">
	<xsl:with-param name="class">
	<xsl:choose>
	<xsl:when test="local-name() ='constructor'">constructor-title</xsl:when>
	<xsl:otherwise>method-title</xsl:otherwise>
	</xsl:choose>
	</xsl:with-param>
</xsl:call-template>
<xsl:value-of select="@name" /></fo:block>
</fo:table-cell>
</fo:table-row>
<!-- method signature -->
<xsl:variable name="return-type-class" select="key('class-key', @return-type)" />
<xsl:variable name="signature">
<xsl:choose>
<xsl:when test="$return-type-class and $generate-links = 'true'">
	<xsl:value-of select="concat(@modifiers, ' ', $return-type-class/@name, ' ', @name, '(')" />
</xsl:when>
<xsl:otherwise>
	<xsl:value-of select="concat(@modifiers, ' ', @return-type, ' ', @name, '(')" />
</xsl:otherwise>
</xsl:choose>
</xsl:variable>

<xsl:variable name="spaces" 
	select="translate($signature, $method-pattern, $method-replacement)" />
<fo:table-row keep-with-previous="always">
<fo:table-cell>
<fo:block wrap-option="wrap" white-space-collapse="false" font-family="monospace">
<xsl:value-of select="concat(@modifiers, ' ')" /><xsl:call-template name="class-link"><xsl:with-param name="name" select="@return-type" /></xsl:call-template><fo:inline font-weight="bold"><xsl:value-of select="concat(' ', @name)" /></fo:inline><xsl:choose>
<xsl:when test="@signature = '()'"><xsl:value-of select="@signature" /><xsl:if test="throws">
<xsl:text>
</xsl:text></xsl:if></xsl:when>
<xsl:otherwise>(<xsl:for-each select="parameter">
<xsl:if test="position() &gt; 1">
<xsl:text>
</xsl:text><xsl:value-of select="$spaces" />
</xsl:if>
<xsl:call-template name="class-link"><xsl:with-param name="name" select="@type" /></xsl:call-template><xsl:value-of select="concat(' ', @name)" /><xsl:if test="not(position() = last())">,</xsl:if>
</xsl:for-each>)
</xsl:otherwise>
</xsl:choose>
<xsl:variable name="spaces2" select="translate(concat(@modifiers, ' ', @return-type, ' '), $method-pattern, $method-replacement)" />
<xsl:if test="throws">
<xsl:value-of select="$spaces2" />throws <xsl:for-each select="throws">
<xsl:if test="position() &gt; 1"><xsl:text>
</xsl:text><xsl:value-of select="$spaces" /></xsl:if>
<xsl:call-template name="class-link">
<xsl:with-param name="name" select="@class" />
</xsl:call-template>
<xsl:if test="not(position() = last())">,</xsl:if>
</xsl:for-each>
</xsl:if>
</fo:block>
<!-- space -->
<fo:block start-indent="0.5in" space-before="10pt" space-after="10pt">
<!-- comments -->
<fo:block space-after="5pt"><xsl:apply-templates select="comment" /></fo:block>
<!-- Parameters, if any -->
<xsl:if test="param">
<fo:block font-weight="bold">Parameters:</fo:block>
<fo:block start-indent="1.0in" space-before="5pt" space-after="5pt">
<xsl:for-each select="param">
	<fo:block>
		<xsl:value-of select="@name" /> - <xsl:apply-templates select="comment" />
	</fo:block>
</xsl:for-each>
</fo:block>
</xsl:if>

<!-- Returns, if not void -->
<xsl:if test="tag[@kind  = '@return']">
<fo:block font-weight="bold">Returns:</fo:block>
<fo:block start-indent="1.0in" space-before="5pt" space-after="5pt">
<xsl:apply-templates select="tag[@kind = '@return']/text" />
</fo:block>
</xsl:if>

<!-- Overrides: if overridden -->
<xsl:if test="@overridden-class">
<xsl:variable name="overridden-class" select="@overridden-class" />
<fo:block font-weight="bold">Overrides:</fo:block>
<fo:block start-indent="1.0in" space-before="5pt" space-after="5pt">
<xsl:call-template name="method-link">
	<xsl:with-param name="class-name" select="$overridden-class" />
	<xsl:with-param name="method" select="." />
</xsl:call-template>
 in 
<xsl:variable name="base-class" select="key('class-key', $overridden-class)" /> 
<xsl:choose>
	<xsl:when test="$base-class[@interface = 'true']">interface </xsl:when>
	<xsl:otherwise>class </xsl:otherwise>
</xsl:choose>
<xsl:call-template name="class-link">
	<xsl:with-param name="name" select="$overridden-class" />
</xsl:call-template>
</fo:block>
</xsl:if>

<!-- Throws, exceptions if any -->
<xsl:if test="throws">
<fo:block font-weight="bold">Throws:</fo:block>
<fo:block start-indent="1.0in" space-before="5pt" space-after="5pt">
<xsl:for-each select="throws">
<fo:block><xsl:value-of select="@class" /> - <xsl:apply-templates select="comment" /></fo:block>
</xsl:for-each>
</fo:block>
</xsl:if>
</fo:block>
</fo:table-cell>
</fo:table-row>
</fo:table-body>
</fo:table>
</xsl:template>

<!-- 
*************************************************************************
Template for the index page
*************************************************************************
-->
<xsl:template match="javadoc" mode="index">
<fo:page-sequence master-reference="index">
<fo:flow flow-name="xsl-region-body">
<fo:block space-before="10pt" space-after="10pt" span="all" font-weight="bold" font-size="24pt">INDEX</fo:block>
<fo:block id="index" font-family="Helvetica" font-size="10pt">
<xsl:call-template name="get-css-for-element">
	<xsl:with-param name="element">body</xsl:with-param>
</xsl:call-template>
<xsl:call-template name="generate-index" />
</fo:block>
</fo:flow>
</fo:page-sequence>
</xsl:template>

<!-- 
*************************************************************************
This template is responsible for drawing a horizontal rule.
*************************************************************************
-->
<xsl:template match="hr">
<fo:block><fo:leader leader-pattern="rule" leader-length="100%" space-before.optimum="2pt" 
	space-after.optimum="2pt">
<xsl:if test="@color">
<xsl:attribute name="color"><xsl:value-of select="@color" /></xsl:attribute>
</xsl:if>
<xsl:if test="@size">
<xsl:attribute name="rule-thickness"><xsl:value-of select="@size" />pt</xsl:attribute>
</xsl:if>
<xsl:call-template name="apply-css">
	<xsl:with-param name="element" select="." />
</xsl:call-template>
</fo:leader>
</fo:block>
</xsl:template>

<!-- 
*************************************************************************
This template is responsible for creating an anchor/link
*************************************************************************
-->
<xsl:template match="a">
<fo:inline>
<!-- 
	if name attribute is present then possibility is that it can be 
	used as an anchor
-->
<xsl:if test="@name">
	<xsl:attribute name="id"><xsl:value-of select="@name" /></xsl:attribute>
</xsl:if>

<!-- if href is not present then it is just an anchor name and not a link -->
<xsl:if test="not(@href)">
	<xsl:apply-templates />
</xsl:if>

<!-- if href is present then is is a link -->
<xsl:choose>
<xsl:when test="@href and $generate-links='true'">
	<xsl:call-template name="apply-css">
		<xsl:with-param name="element" select="." />
	</xsl:call-template>

	<fo:basic-link>
	<xsl:call-template name="apply-css">
		<xsl:with-param name="element" select="." />
	</xsl:call-template>

	<xsl:choose>
		<!-- href starts with # that means it is an internal link -->
		<xsl:when test="starts-with(@href, '#')">
			<xsl:attribute name="internal-destination"><xsl:value-of select="substring-after(@href,'#')" /></xsl:attribute>	
		</xsl:when>
		
		<!-- else it is an external link -->
		<xsl:otherwise>
			<xsl:attribute name="external-destination"><xsl:value-of select="@href" /></xsl:attribute>	
		</xsl:otherwise>
	</xsl:choose>
	<xsl:apply-templates />
	</fo:basic-link>
</xsl:when>
<xsl:otherwise><xsl:apply-templates /></xsl:otherwise>
</xsl:choose>
</fo:inline>
</xsl:template>

<!-- 
*************************************************************************
This template renders an unordered list
*************************************************************************
-->
<xsl:template match="ul">
	<!-- get the bullet size -->
	<xsl:variable name="bullet-size">
	<xsl:choose>
	<xsl:when test="@bullet-size">
	<xsl:value-of select="@bullet-size" />
	</xsl:when>
	<xsl:otherwise>12pt</xsl:otherwise>
	</xsl:choose>
	</xsl:variable>

	<!-- list -->
	<fo:list-block >
	<xsl:for-each select="li">
	<fo:list-item>
	<fo:list-item-label end-indent="label-end()">
	<fo:block>
	<fo:inline font-family="Symbol" font-size="{$bullet-size}">&#x2022;</fo:inline>
	</fo:block>
	</fo:list-item-label>
	
	<fo:list-item-body start-indent="body-start()">
		<fo:block><xsl:apply-templates /></fo:block>
	</fo:list-item-body>
	
	</fo:list-item>
	</xsl:for-each>
	</fo:list-block>
</xsl:template>

<!-- 
*************************************************************************
This template renders an ordered list
*************************************************************************
-->
<xsl:template match="ol">
	<!-- list -->
	<fo:list-block >
	<xsl:for-each select="li">
	<fo:list-item>
	<fo:list-item-label end-indent="label-end()">
	<fo:block>
		<fo:inline>
			<xsl:choose>
			<xsl:when test="parent::ol/@type">
				<xsl:number value="position()" format="{parent::ol/@type}"/>.
			</xsl:when>
			<xsl:otherwise>
				<xsl:number value="position()" />.
			</xsl:otherwise>
			</xsl:choose>
		</fo:inline>
	</fo:block>
	</fo:list-item-label>
	
	<fo:list-item-body start-indent="body-start()">
		<fo:block><xsl:apply-templates /></fo:block>
	</fo:list-item-body>
	
	</fo:list-item>
	</xsl:for-each>
	</fo:list-block>
</xsl:template>

<!-- 
*************************************************************************
This template renders an definition list
*************************************************************************
-->
<xsl:template match="dl">
<fo:block space-after.optimum="15pt">
	<xsl:apply-templates select="dt|dd" />
</fo:block>
</xsl:template>

<!-- 
*************************************************************************
This template renders an definition term
*************************************************************************
-->
<xsl:template match="dt">
<fo:block>
	<xsl:apply-templates />
</fo:block>
</xsl:template>

<!-- 
*************************************************************************
This template renders an definition description
*************************************************************************
-->
<xsl:template match="dd">
<fo:block space-after.optimum="5pt" start-indent="0.5in">
	<xsl:apply-templates />
</fo:block>
</xsl:template>

<!-- 
*************************************************************************
This template renders a line break
*************************************************************************
-->
<xsl:template match="br">
	<fo:block  space-before.optimum="5pt"></fo:block>
	<xsl:apply-templates />
</xsl:template>

<!-- 
*************************************************************************
This template renders an image
*************************************************************************
-->
<xsl:template match="img">
<fo:external-graphic src="{@src}">
<xsl:if test="@width">
<xsl:attribute name="width"><xsl:value-of select="concat(@width, 'px')" /></xsl:attribute>
</xsl:if>
<xsl:if test="@height">
<xsl:attribute name="height"><xsl:value-of select="concat(@height, 'px')" /></xsl:attribute>
</xsl:if>
<xsl:if test="@hspace">
<xsl:attribute name="padding-left"><xsl:value-of select="@vspace" />px</xsl:attribute>
<xsl:attribute name="padding-right"><xsl:value-of select="@vspace" />px</xsl:attribute>
</xsl:if>
<xsl:if test="@vspace">
<xsl:attribute name="padding-top"><xsl:value-of select="@vspace" />px</xsl:attribute>
<xsl:attribute name="padding-bottom"><xsl:value-of select="@vspace" />px</xsl:attribute>
</xsl:if>
<xsl:call-template name="apply-css">
	<xsl:with-param name="element" select="." />
</xsl:call-template>
</fo:external-graphic>
</xsl:template>

<!-- 
*************************************************************************
This template sets the font weight as bold
*************************************************************************
-->
<xsl:template match="b|strong">
<fo:inline font-weight="bold">
<xsl:call-template name="apply-css">
	<xsl:with-param name="element" select="." />
</xsl:call-template>
<xsl:apply-templates />
</fo:inline>
</xsl:template>

<!-- 
*************************************************************************
This template sets the font style as italic
*************************************************************************
-->
<xsl:template match="i|em">
<fo:inline font-style="italic">
<xsl:call-template name="apply-css">
	<xsl:with-param name="element" select="." />
</xsl:call-template>
<xsl:apply-templates />
</fo:inline>
</xsl:template>

<!-- 
*************************************************************************
This template sets the font style as underlined
*************************************************************************
-->
<xsl:template match="u">
<fo:inline text-decoration="underline">
<xsl:call-template name="apply-css">
	<xsl:with-param name="element" select="." />
</xsl:call-template>
<xsl:apply-templates />
</fo:inline>
</xsl:template>

<!-- 
*************************************************************************
This template sets the font style as striked-through
*************************************************************************
-->
<xsl:template match="s">
<fo:inline text-decoration="line-through">
<xsl:call-template name="apply-css">
	<xsl:with-param name="element" select="." />
</xsl:call-template>
<xsl:apply-templates />
</fo:inline>
</xsl:template>

<!-- 
*************************************************************************
This template handles the p and div tags
*************************************************************************
-->
<xsl:template match="p|div">
<fo:block space-before.optimum="10pt" space-after.optimum="10pt">
<xsl:call-template name="apply-css">
	<xsl:with-param name="element" select="." />
</xsl:call-template>
<xsl:call-template name="get-alignment">
	<xsl:with-param name="align" select="@align" />
</xsl:call-template>
<xsl:apply-templates />
</fo:block>
</xsl:template>

<!-- 
*************************************************************************
This template handles the center tags
*************************************************************************
-->
<xsl:template match="center">
<fo:block text-align="center">
	<xsl:apply-templates />
</fo:block>
</xsl:template>

<!-- 
*************************************************************************
This template handles the h1 heading tag
*************************************************************************
-->
<xsl:template match="h1">
<fo:block space-before.optimum="10pt" space-after.optimum="10pt" font-size="28pt">
	<xsl:call-template name="apply-css">
		<xsl:with-param name="element" select="." />
	</xsl:call-template>
	<xsl:call-template name="get-alignment">
		<xsl:with-param name="align" select="@align" />
	</xsl:call-template>
	<xsl:apply-templates />
</fo:block>
</xsl:template>

<!-- 
*************************************************************************
This template handles the h2 heading tag
*************************************************************************
-->
<xsl:template match="h2">
<fo:block space-before.optimum="10pt" space-after.optimum="10pt" font-size="24pt">
	<xsl:call-template name="apply-css">
		<xsl:with-param name="element" select="." />
	</xsl:call-template>
	<xsl:call-template name="get-alignment">
		<xsl:with-param name="align" select="@align" />
	</xsl:call-template>
	<xsl:apply-templates />
</fo:block>
</xsl:template>

<!-- 
*************************************************************************
This template handles the h3 heading tag
*************************************************************************
-->
<xsl:template match="h3">
<fo:block space-before.optimum="10pt" space-after.optimum="10pt" font-size="20pt">
	<xsl:call-template name="apply-css">
		<xsl:with-param name="element" select="." />
	</xsl:call-template>
	<xsl:call-template name="get-alignment">
		<xsl:with-param name="align" select="@align" />
	</xsl:call-template>
	<xsl:apply-templates />
</fo:block>
</xsl:template>

<!-- 
*************************************************************************
This template handles the h4 heading tag
*************************************************************************
-->
<xsl:template match="h4">
<fo:block space-before.optimum="10pt" space-after.optimum="10pt" font-size="16pt">
	<xsl:call-template name="apply-css">
		<xsl:with-param name="element" select="." />
	</xsl:call-template>
	<xsl:call-template name="get-alignment">
		<xsl:with-param name="align" select="@align" />
	</xsl:call-template>
	<xsl:apply-templates />
</fo:block>
</xsl:template>

<!-- 
*************************************************************************
This template handles the h5 heading tag
*************************************************************************
-->
<xsl:template match="h5">
<fo:block space-before.optimum="10pt" space-after.optimum="10pt" font-size="12pt">
	<xsl:call-template name="apply-css">
		<xsl:with-param name="element" select="." />
	</xsl:call-template>
	<xsl:call-template name="get-alignment">
		<xsl:with-param name="align" select="@align" />
	</xsl:call-template>
	<xsl:apply-templates />
</fo:block>
</xsl:template>

<!-- 
*************************************************************************
This template handles the h6 heading tag
*************************************************************************
-->
<xsl:template match="h6">
<fo:block space-before.optimum="10pt" space-after.optimum="10pt" font-size="8pt">
	<xsl:call-template name="apply-css">
		<xsl:with-param name="element" select="." />
	</xsl:call-template>
	<xsl:call-template name="get-alignment">
		<xsl:with-param name="align" select="@align" />
	</xsl:call-template>
	<xsl:apply-templates />
</fo:block>
</xsl:template>

<!-- 
*************************************************************************
This template handles the address tag
*************************************************************************
-->
<xsl:template match="address">
<fo:block font-style="italic">
<xsl:call-template name="apply-css">
	<xsl:with-param name="element" select="." />
</xsl:call-template>
<xsl:apply-templates />
</fo:block>
</xsl:template>

<!-- 
*************************************************************************
This template renders the page number
*************************************************************************
-->
<xsl:template match="page-number">
	<fo:page-number/>
</xsl:template>

<!-- 
*************************************************************************
This template renders the cover page
*************************************************************************
-->
<xsl:template name="cover-page">
	<xsl:if test="/document/document-meta-info">
	<fo:page-sequence master-reference="cover">
	<!-- apply the header -->
	<xsl:apply-templates select="/document/document-header" />

	<!-- apply the footer -->
	<xsl:apply-templates select="/document/document-footer" />

	<fo:flow flow-name="xsl-region-body">

	<!-- title -->
	<fo:block space-before.optimum="16pt" font-weight="bold">
	<xsl:call-template name="get-css-for-class">
		<xsl:with-param name="class">document-title</xsl:with-param>
	</xsl:call-template>

	<xsl:value-of select="/document/document-meta-info/title" />
	</fo:block>
		
	<!-- all attributes -->
	<fo:block space-before.optimum="16pt">
	<xsl:call-template name="get-css-for-class">
		<xsl:with-param name="class">document-attributes</xsl:with-param>
	</xsl:call-template>

	<xsl:for-each select="/document/document-meta-info/attribute">
		<xsl:value-of select="@name" />: <xsl:apply-templates select="." />
		<fo:block/>
	</xsl:for-each>
	</fo:block>
	</fo:flow>
	</fo:page-sequence>
	</xsl:if>
</xsl:template>

<!-- 
*************************************************************************
This template forces a page-break
*************************************************************************
-->
<xsl:template match="page-break">
	<fo:block break-before="page" keep-with-previous="auto" />	
</xsl:template>

<!-- 
*************************************************************************
This template reners a table
*************************************************************************
-->
<xsl:template match="table">
<fo:table table-layout="fixed">	
	<!-- bgcolor -->
	<xsl:if test="@bgcolor">
	<xsl:attribute name="background-color"><xsl:value-of select="@bgcolor" /></xsl:attribute>
	</xsl:if>
	
	<xsl:if test="@border">
	<xsl:attribute name="border-width"><xsl:value-of select="@border" />pt</xsl:attribute>
	<xsl:attribute name="border-style">solid</xsl:attribute>
	</xsl:if>
	
	<xsl:call-template name="apply-css">
		<xsl:with-param name="element" select="." />
	</xsl:call-template>

	<!-- table columns -->
	<xsl:for-each select="tr[1]/td">
	<xsl:variable name="col-width">
	<xsl:choose>
	<xsl:when test="@width">
	<xsl:choose>
	<xsl:when test="contains(@width, '%')"><xsl:value-of select="170 * substring-before(@width, '%') div 100" />mm</xsl:when>
	<xsl:otherwise><xsl:value-of select="concat(@width, 'pt')" /></xsl:otherwise>
	</xsl:choose>
	</xsl:when>
	<xsl:otherwise><xsl:value-of select="170 div count(../td)" />mm</xsl:otherwise>
	</xsl:choose>
	</xsl:variable>
	<fo:table-column column-width="{$col-width}" />
	</xsl:for-each>

	<fo:table-body>	
		<xsl:apply-templates select="tr" />
	</fo:table-body>
</fo:table>	
</xsl:template>

<!-- 
*************************************************************************
This template reners a tr - table row
*************************************************************************
-->
<xsl:template match="tr">
<fo:table-row>
	<!-- bgcolor -->
	<xsl:if test="@bgcolor">
	<xsl:attribute name="background-color"><xsl:value-of select="@bgcolor" /></xsl:attribute>
	</xsl:if>

	<xsl:call-template name="apply-css">
		<xsl:with-param name="element" select="." />
	</xsl:call-template>
	
	<xsl:apply-templates />
</fo:table-row>
</xsl:template>


<!-- 
*************************************************************************
This template reners a td - table cell
*************************************************************************
-->
<xsl:template match="td">
<fo:table-cell>
	
	<!-- bgcolor -->
	<xsl:if test="@bgcolor">
	<xsl:attribute name="background-color"><xsl:value-of select="@bgcolor" /></xsl:attribute>
	</xsl:if>

	<xsl:call-template name="apply-css">
		<xsl:with-param name="element" select="." />
	</xsl:call-template>
	
	<!-- colspan -->
	<xsl:if test="@colspan">
	<xsl:attribute name="number-columns-spanned"><xsl:value-of select="@colspan" /></xsl:attribute>
	</xsl:if>

	<!-- rowspan -->
	<xsl:if test="@rowspan">
	<xsl:attribute name="number-rows-spanned"><xsl:value-of select="@rowspan" /></xsl:attribute>
	</xsl:if>

	<xsl:if test="ancestor::table/@border">
	<xsl:attribute name="border-width"><xsl:value-of select="ancestor::table/@border" />pt</xsl:attribute>
	<xsl:attribute name="border-style">solid</xsl:attribute>
	</xsl:if>

	<xsl:if test="ancestor::table/@cellpadding">
	<xsl:attribute name="padding"><xsl:value-of select="ancestor::table/@cellpadding" />pt</xsl:attribute>
	</xsl:if>
	
	<xsl:if test="@valign">
		<xsl:attribute name="display-align">
		<xsl:choose>
			<xsl:when test="@valign='bottom'">bottom</xsl:when>
			<xsl:when test="@valign='middle'">center</xsl:when>
			<xsl:otherwise>center</xsl:otherwise>
		</xsl:choose>
		</xsl:attribute>
	</xsl:if>	

 	<fo:block> 
		<xsl:attribute name="text-align">
		<xsl:choose>
			<xsl:when test="@align='right'">end</xsl:when>
			<xsl:when test="@align='center'">center</xsl:when>
			<xsl:when test="@align='justify'">justify</xsl:when>
			<xsl:otherwise>start</xsl:otherwise>
		</xsl:choose>
		</xsl:attribute>
		<xsl:apply-templates />
 	</fo:block> 
</fo:table-cell>
</xsl:template>

<!-- 
*************************************************************************
This template renders preformatted text
*************************************************************************
-->
<xsl:template match="pre">
<fo:block space-before.optimum="10pt" space-after.optimum="10pt" wrap-option="no-wrap"  white-space-collapse="false">
<xsl:call-template name="apply-css">
	<xsl:with-param name="element" select="." />
</xsl:call-template>
<xsl:apply-templates />
</fo:block>
</xsl:template>

<!-- 
*************************************************************************
This template renders a blockquote(indented) section
*************************************************************************
-->
<xsl:template match="blockquote">
<fo:block start-indent="1.0in">
<xsl:call-template name="apply-css">
	<xsl:with-param name="element" select="." />
</xsl:call-template>
<xsl:apply-templates />
</fo:block>
</xsl:template>

<!-- 
*************************************************************************
This template renders superscript text
*************************************************************************
-->
<xsl:template match="sup">
<fo:inline vertical-align="super">
<xsl:call-template name="apply-css">
	<xsl:with-param name="element" select="." />
</xsl:call-template>
<xsl:apply-templates />
</fo:inline>
</xsl:template>

<!-- 
*************************************************************************
This template renders subscript text
*************************************************************************
-->
<xsl:template match="sub">
<fo:inline vertical-align="sub">
<xsl:call-template name="apply-css">
	<xsl:with-param name="element" select="." />
</xsl:call-template>
<xsl:apply-templates />
</fo:inline>
</xsl:template>

<!-- 
*************************************************************************
This template sets a new font for the content enclosed within it.
*************************************************************************
-->
<xsl:template match="font">
<fo:inline>
<xsl:if test="@color">
	<xsl:attribute name="color"><xsl:value-of select="@color" /></xsl:attribute>
</xsl:if>
<xsl:if test="@face">
	<xsl:attribute name="font-family"><xsl:value-of select="@face" /></xsl:attribute>
</xsl:if>
<xsl:apply-templates />
</fo:inline>
</xsl:template>

<!-- 
*************************************************************************
This template handles the code tag
*************************************************************************
-->
<xsl:template match="code">
<fo:inline font-family="monospace">
<xsl:call-template name="apply-css">
	<xsl:with-param name="element" select="." />
</xsl:call-template>
<xsl:apply-templates />
</fo:inline>
</xsl:template>

<!-- 
*************************************************************************
This template applies the css for the given element or class
*************************************************************************
-->
<xsl:template name="apply-css">
<xsl:param name="element" />
<xsl:variable name="name" select="local-name($element)" />
<!-- if style attrib is present, apply it -->
<xsl:if test="$element/@style">
	<xsl:call-template name="parse-css-attrib">
		<xsl:with-param name="style" select="normalize-space($element/@style)" />
	</xsl:call-template>
</xsl:if>

<!-- if css rule exists for this element apply it -->
<xsl:call-template name="get-css-for-element">
	<xsl:with-param name="element" select="$name" />
</xsl:call-template>

<!-- if class attribute exists this element apply it -->
<xsl:call-template name="get-css-for-class">
	<xsl:with-param name="element" select="$name" />
	<xsl:with-param name="class" select="$element/@class" />
</xsl:call-template>
</xsl:template>

<!-- 
*************************************************************************
This template applies the css rules for the given class
*************************************************************************
-->
<xsl:template name="get-css-for-class">
<xsl:param name="element" />
<xsl:param name="class" />
<xsl:if test="$class and $css-file">
<xsl:for-each 
	select="document($css-file)/css/selector[@name = concat($element, '.', $class) or @name = concat('*.', $class)]/attrib">
	<xsl:attribute name="{@name}"><xsl:value-of select="@value" /></xsl:attribute>
</xsl:for-each>
</xsl:if>
</xsl:template>

<!-- 
*************************************************************************
This template applies the css rules for the given element
*************************************************************************
-->
<xsl:template name="get-css-for-element">
<xsl:param name="element" />
<xsl:if test="$css-file">
<xsl:for-each select="document($css-file)/css/selector[@name = $element]/attrib">
	<xsl:attribute name="{@name}"><xsl:value-of select="@value" /></xsl:attribute>
</xsl:for-each>
</xsl:if>
</xsl:template>

<!-- 
*************************************************************************
This template parses the style attribute and gets the individual 
components
*************************************************************************
-->
<xsl:template name="parse-css-attrib">
<xsl:param name="style" />
<xsl:if test="$style">
<xsl:variable name="attrib" select="$style" />
<xsl:choose>
<xsl:when test="contains($attrib, ';')">
<xsl:variable name="item" select="substring-before($attrib, ';')" />
<xsl:variable name="remaining" select="substring-after($attrib, ';')" />
<xsl:call-template name="parse-css-item">
		<xsl:with-param name="item" select="$item" />
</xsl:call-template>
<xsl:if test="not($remaining = '')">
	<xsl:call-template name="parse-css-attrib">
		<xsl:with-param name="style" select="$remaining" />
	</xsl:call-template>
</xsl:if>
</xsl:when>
<xsl:otherwise>
<xsl:call-template name="parse-css-item">
		<xsl:with-param name="item" select="$attrib" />
</xsl:call-template>
</xsl:otherwise>
</xsl:choose>
</xsl:if>
</xsl:template>

<!-- 
*************************************************************************
This template process a single css attribute and generates an xsl
attribute based on that.
*************************************************************************
-->
<xsl:template name="parse-css-item">
<xsl:param name="item" />
<xsl:variable name="name">
<xsl:call-template name="to-lower-case">
	<xsl:with-param name="str" select="substring-before($item, ':')" />
</xsl:call-template>
</xsl:variable>
<xsl:variable name="value">
<xsl:call-template name="to-lower-case">
	<xsl:with-param name="str" select="substring-after($item, ':')" />
</xsl:call-template>
</xsl:variable>
<xsl:attribute name="{$name}"><xsl:value-of select="$value" /></xsl:attribute>
</xsl:template>

<!-- 
*************************************************************************
This template converts the given string value to lowecase.
*************************************************************************
-->
<xsl:template name="to-lower-case">
<xsl:param name="str" />
<xsl:value-of select="translate($str, $upper-alphas, $lower-alphas)" />
</xsl:template>

<!--
*************************************************************************
This template gets the align attribute
*************************************************************************
-->
<xsl:template name="get-alignment">
<xsl:param name="align" />
<xsl:if test="$align">
<xsl:variable name="alignment" select="translate($align, $upper-alphas, $lower-alphas)" /> 
<xsl:attribute name="text-align"><xsl:choose>
		<xsl:when test="$alignment = 'right'">end</xsl:when>
		<xsl:when test="$alignment = 'center'">center</xsl:when>
		<xsl:otherwise>start</xsl:otherwise>
</xsl:choose></xsl:attribute>
</xsl:if>
</xsl:template>

<!-- 
*************************************************************************
This template shows a horizontal rule
*************************************************************************
-->
<xsl:template name="horizontal-rule">
<xsl:param name="color">black</xsl:param>
<xsl:param name="size">1</xsl:param>
<xsl:param name="css-class" />
<fo:block text-align="center">
<fo:leader color="{$color}" leader-pattern="rule" 
	leader-length="100%" space-before.optimum="2pt" space-after.optimum="2pt" 
	rule-thickness="{$size}pt">
<xsl:if test="$css-class">
<xsl:call-template name="get-css-for-class">
	<xsl:with-param name="class" select="$css-class" />
</xsl:call-template>
</xsl:if>
</fo:leader>
</fo:block>
</xsl:template>

<!--
*************************************************************************
This template shows a horizontal rule
*************************************************************************
-->
<xsl:template match="class" mode="package-summary">
<fo:block font-weight="bold" font-size="12pt" space-after="5pt">
<xsl:call-template name="internal-link">
	<xsl:with-param name="destination" select="@qualified-name" />
	<xsl:with-param name="label" select="@name" />
</xsl:call-template>
</fo:block>
<fo:block start-indent="0.5in" space-after="10pt">
<xsl:apply-templates select="summary" />
</fo:block>
</xsl:template>

<!--
*************************************************************************
This template shows a tag
*************************************************************************
-->
<xsl:template match="tag" mode="class">
<fo:block font-weight="bold" space-after="2pt">
<xsl:choose>
<xsl:when test="@name = '@author'">Author:</xsl:when>
<xsl:when test="@name = '@version'">Version:</xsl:when>
</xsl:choose>
</fo:block>
<fo:block start-indent="0.5in" space-after="10pt">
	<xsl:apply-templates select="text" />
</fo:block>
</xsl:template>

<!-- 
*************************************************************************
This template outputs an internal link
*************************************************************************
-->
<xsl:template name="internal-link">
<xsl:param name="destination" />
<xsl:param name="label" />
<xsl:choose>
<xsl:when test="$generate-links = 'true'">
<fo:basic-link text-decoration="underline" internal-destination="{$destination}">
<xsl:call-template name="get-css-for-element">
	<xsl:with-param name="element">a</xsl:with-param>
</xsl:call-template>
<xsl:value-of select="$label" /></fo:basic-link>
</xsl:when>
<xsl:otherwise><xsl:value-of select="$label" /></xsl:otherwise>
</xsl:choose>
</xsl:template>

<!--
*************************************************************************
This template renders a class link, if the class info is present in the 
doc; if not the class name is displayed without a link. Also if the class
info is present then just the class name is displayed; else the fully 
qualified name of the class is displayed.
*************************************************************************
-->
<xsl:template name="class-link">
<xsl:param name="name" />
<xsl:variable name="class" select="key('class-key', $name)" />
<xsl:choose>
<xsl:when test="$class and $generate-links = 'true'">
<xsl:call-template name="internal-link">
	<xsl:with-param name="destination" select="$class/@qualified-name" />
	<xsl:with-param name="label" select="$class/@name" />
</xsl:call-template>
</xsl:when>
<xsl:otherwise><xsl:value-of select="$name" /></xsl:otherwise>
</xsl:choose>
</xsl:template>

<!--
*************************************************************************
This template renders a method link, if the class containing the method 
is present in the doc; if not the method name is displayed without a link. 
*************************************************************************
-->
<xsl:template name="method-link">
<xsl:param name="class-name" />
<xsl:param name="method" />
<xsl:variable name="class" select="key('class-key', $class-name)" />
<xsl:choose>
<xsl:when test="$class and $generate-links = 'true'">
<xsl:call-template name="internal-link">
	<xsl:with-param name="destination" 
		select="concat($class/@qualified-name, $method/@name, $method/@signature)" />
	<xsl:with-param name="label" select="$method/@name" />
</xsl:call-template>
</xsl:when>
<xsl:otherwise><xsl:value-of select="$method/@name" /></xsl:otherwise>
</xsl:choose>
</xsl:template>

<!--
*************************************************************************
This template renders a class node in the class hierarchy
*************************************************************************
-->
<xsl:template name="display-class-node">
<xsl:param name="level" />
<xsl:param name="class" />
<xsl:variable name="spaces">
<xsl:call-template name="display-n-spaces">
	<xsl:with-param name="n" select="$level * $tab-size" />
</xsl:call-template>
</xsl:variable>
<xsl:if test="not($level = 0)">
<xsl:value-of select="concat($spaces, '|')" />
<xsl:text>
</xsl:text>
<xsl:value-of select="concat($spaces, '+--')" />
</xsl:if>
<xsl:call-template name="class-link">
	<xsl:with-param name="name" select="$class" />
</xsl:call-template>
</xsl:template>

<!--
*************************************************************************
This template renders n spaces
*************************************************************************
-->
<xsl:template name="display-n-spaces">
<xsl:param name="n" />
<xsl:if test="$n &gt; 0">
<xsl:text> </xsl:text>
<xsl:call-template name="display-n-spaces">
<xsl:with-param name="n" select="$n - 1" />
</xsl:call-template>
</xsl:if>
</xsl:template>

<!--
*************************************************************************
This template generates the index
*************************************************************************
-->
<xsl:template name="generate-index">
<xsl:param name="alphas" select="$upper-alphas" />
<xsl:if test="not($alphas = '')">
<xsl:call-template name="display-index-items">
	<xsl:with-param name="alphabet" select="substring($alphas, 1, 1)" />
</xsl:call-template>
<xsl:call-template name="generate-index">
	<xsl:with-param name="alphas" select="substring($alphas, 2)" />
</xsl:call-template>
</xsl:if>
</xsl:template>

<!--
*************************************************************************
This template generates the index
*************************************************************************
-->
<xsl:template name="display-index-items">
<xsl:param name="alphabet" />
<xsl:variable name="lower-alphabet">
<xsl:call-template name="to-lower-case">
	<xsl:with-param name="str" select="$alphabet" />
</xsl:call-template>
</xsl:variable>
<xsl:if test="key('index-key', $alphabet) or key('index-key', $lower-alphabet)">
<fo:table table-layout="fixed" width="100%">
<fo:table-column column-width="proportional-column-width(1)"/>
<fo:table-body>
<fo:table-row>
<fo:table-cell>
<fo:block start-indent="0.25in" space-before="10pt" space-after="5pt" font-size="14pt" font-weight="bold">
	<xsl:value-of select="$alphabet" />
</fo:block>
</fo:table-cell>
</fo:table-row>
<fo:table-row keep-with-previous="always">
<fo:table-cell>
<!-- starting with lower alphabet -->
<xsl:for-each select="key('index-key', $lower-alphabet)">
<xsl:sort select="@name" />
<xsl:call-template name="display-index-item">
	<xsl:with-param name="item" select="." />
</xsl:call-template>
</xsl:for-each>
<!-- starting with upper alphabet -->
<xsl:for-each select="key('index-key', $alphabet)">
<xsl:sort select="@name" />
<xsl:call-template name="display-index-item">
	<xsl:with-param name="item" select="." />
</xsl:call-template>
</xsl:for-each>
</fo:table-cell>
</fo:table-row>
</fo:table-body>
</fo:table>
</xsl:if>
</xsl:template>

<!--
*************************************************************************
This template renders a single index item.
*************************************************************************
-->
<xsl:template name="display-index-item">
<xsl:param name="item" />
<xsl:variable name="local-name" select="local-name($item)" />
<xsl:variable name="target">
<xsl:choose>
<xsl:when test="$local-name = 'class'">
	<xsl:value-of select="$item/@qualified-name" />
</xsl:when>
<xsl:when test="$local-name = 'method' or $local-name = 'constructor'">
	<xsl:value-of 
		select="concat($item/parent::class/@qualified-name, $item/@name, $item/@signature)" />
</xsl:when>
<xsl:when test="$local-name = 'field'">
	<xsl:value-of 
		select="concat($item/parent::class/@qualified-name, '_field_', $item/@name)" />
</xsl:when>
</xsl:choose>
</xsl:variable>
<fo:block start-indent="0.5in">
	<xsl:choose>
	<xsl:when test="$generate-links = 'true'">
		<fo:basic-link internal-destination="{$target}">
			<xsl:call-template name="get-css-for-element">
				<xsl:with-param name="element">a</xsl:with-param>
			</xsl:call-template>
			<xsl:value-of select="$item/@name" />
		</fo:basic-link>
		<xsl:text> ... </xsl:text>
		<fo:inline font-style="italic">
			<fo:page-number-citation ref-id="{$target}" />
		</fo:inline>
	</xsl:when>
	<xsl:otherwise>
			<xsl:value-of select="$item/@name" /><xsl:text> ... </xsl:text>
			<fo:inline font-style="italic">
				<fo:page-number-citation ref-id="{$target}" />
			</fo:inline>
	</xsl:otherwise>
	</xsl:choose>
</fo:block>
</xsl:template>

</xsl:stylesheet>
