<!--
  - StarshipOS Copyright (c) 2025. R.A. James
  -->

<!-- ...................................................................... -->
<!-- XHTML BDO Element Module ............................................. -->
<!-- Bidirectional Override (bdo) Element

     This modules declares the element 'bdo', used to override the
     Unicode bidirectional algorithm for selected fragments of text.

     DEPENDENCIES:
     Relies on the conditional section keyword %XHTML.bidi; declared
     as "INCLUDE". Bidirectional text support includes both the bdo
     element and the 'dir' attribute.
-->

<!ENTITY % bdo.element  "INCLUDE" >
<![%bdo.element;[
<!ENTITY % bdo.content
     "( #PCDATA | %Inline.mix; )*"
>
<!ENTITY % bdo.qname  "bdo" >
<!ELEMENT %bdo.qname;  %bdo.content; >
<!-- end of bdo.element -->]]>

<!ENTITY % bdo.attlist  "INCLUDE" >
<![%bdo.attlist;[
<!ATTLIST %bdo.qname;
      %Core.attrib;
	  %lang.attrib;
      dir          ( ltr | rtl )            #REQUIRED
>
]]>

<!-- end of xhtml-bdo-1.mod -->
