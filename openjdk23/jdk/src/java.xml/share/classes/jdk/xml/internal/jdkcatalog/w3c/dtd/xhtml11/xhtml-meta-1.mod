<!--
  - StarshipOS Copyright (c) 2025. R.A. James
  -->

<!-- ...................................................................... -->
<!-- XHTML Document Metainformation Module  ............................... -->
<!-- Meta Information

        meta

     This module declares the meta element type and its attributes,
     used to provide declarative document metainformation.
-->

<!-- meta: Generic Metainformation ..................... -->

<!ENTITY % meta.element  "INCLUDE" >
<![%meta.element;[
<!ENTITY % meta.content  "EMPTY" >
<!ENTITY % meta.qname  "meta" >
<!ELEMENT %meta.qname;  %meta.content; >
<!-- end of meta.element -->]]>

<!ENTITY % meta.attlist  "INCLUDE" >
<![%meta.attlist;[
<!ATTLIST %meta.qname;
      %XHTML.xmlns.attrib;
      %I18n.attrib;
      http-equiv   NMTOKEN                  #IMPLIED
      name         NMTOKEN                  #IMPLIED
      content      CDATA                    #REQUIRED
      scheme       CDATA                    #IMPLIED
>
<!-- end of meta.attlist -->]]>

<!-- end of xhtml-meta-1.mod -->
