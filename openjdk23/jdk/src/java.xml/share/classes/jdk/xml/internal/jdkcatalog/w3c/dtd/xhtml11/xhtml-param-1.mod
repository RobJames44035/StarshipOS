<!--
  - StarshipOS Copyright (c) 2025. R.A. James
  -->

<!-- ...................................................................... -->
<!-- XHTML Param Element Module  ..................................... -->
<!-- Parameters for Java Applets and Embedded Objects

        param

     This module provides declarations for the param element,
     used to provide named property values for the applet
     and object elements.
-->

<!-- param: Named Property Value ....................... -->

<!ENTITY % param.element  "INCLUDE" >
<![%param.element;[
<!ENTITY % param.content  "EMPTY" >
<!ENTITY % param.qname  "param" >
<!ELEMENT %param.qname;  %param.content; >
<!-- end of param.element -->]]>

<!ENTITY % param.attlist  "INCLUDE" >
<![%param.attlist;[
<!ATTLIST %param.qname;
      %XHTML.xmlns.attrib;
      %id.attrib;
      name         CDATA                    #REQUIRED
      value        CDATA                    #IMPLIED
      valuetype    ( data | ref | object )  'data'
      type         %ContentType.datatype;   #IMPLIED
>
<!-- end of param.attlist -->]]>

<!-- end of xhtml-param-1.mod -->
