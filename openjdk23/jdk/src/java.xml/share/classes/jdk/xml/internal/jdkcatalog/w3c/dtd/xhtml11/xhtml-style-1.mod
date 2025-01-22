<!--
  - StarshipOS Copyright (c) 2025. R.A. James
  -->

<!-- ...................................................................... -->
<!-- XHTML Document Style Sheet Module  ................................... -->
<!-- Style Sheets

        style

     This module declares the style element type and its attributes,
     used to embed style sheet information in the document head element.
-->

<!-- style: Style Sheet Information .................... -->

<!ENTITY % style.element  "INCLUDE" >
<![%style.element;[
<!ENTITY % style.content  "( #PCDATA )" >
<!ENTITY % style.qname  "style" >
<!ELEMENT %style.qname;  %style.content; >
<!-- end of style.element -->]]>

<!ENTITY % style.attlist  "INCLUDE" >
<![%style.attlist;[
<!ATTLIST %style.qname;
      %XHTML.xmlns.attrib;
      %id.attrib;
      %title.attrib;
      %I18n.attrib;
      xml:space    ( preserve )             #FIXED 'preserve'
      type         %ContentType.datatype;   #REQUIRED
      media        %MediaDesc.datatype;     #IMPLIED
>
<!-- end of style.attlist -->]]>

<!-- end of xhtml-style-1.mod -->
